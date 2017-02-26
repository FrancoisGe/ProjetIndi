package Client.Temperature;

import Client.LedAffichage;
import Client.Listener.SensorChangeListenerTemperature;
import Client.OpenNewPhidget;
import com.google.gson.JsonObject;
import com.phidgets.InterfaceKitPhidget;
import com.phidgets.PhidgetException;

import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by User on 02-12-16.
 */
public class EnvoieTemp implements Runnable{

    private PrintWriter out;
    private InterfaceKitPhidget ik;
    private SensorChangeListenerTemperature s;
    private boolean isRun = true;

    private int[] i;

    /**
     * Ce Thread quand il est actif gère l'envoie des données au Serveur par out.
     *
     * @param out Buffer dans lequel on met les messages à envoyer (le maximum enregistré toutes les secondes + la date)
     * @param i tableau contenant une seul valeur à l'indice 0 qui est le nombre de paquets de données envoyées moins le nombre de paquets que le serveur à dit qu'il avait reçu
     * @param s SensorChangeListener utiliser pour configurer l'interfaceKitPhidget
     */

    public EnvoieTemp(PrintWriter out,int[] i,SensorChangeListenerTemperature s){
        this.out = out;
        this.i =i;
        this.s=s;

    }

    @Override
    public void run() {

            try {
                ik = OpenNewPhidget.initIK(s);
                ik.setSensorChangeTrigger(0,2);

                //Thread qui va gérer l'allumage des leds
                LedAffichage l =new LedAffichage(ik);
                Thread led = new Thread(l);
                led.start();

                while (isRun) {

                    Date date = new Date();
                    int seconde = date.getSeconds();
                    int minute = date.getMinutes();
                    int heure = date.getHours();
                    int jour = date.getDate();
                    int mois = date.getMonth();
                    s.setValMax(ik.getSensorValue(0));
                    double valeur = (s.getMax() * 0.2222) - 61.111;

                    //envoie un packet de données toutes les 1 sec (Date +température)
                    JsonObject json = new JsonObject();
                    json.addProperty("Heure", heure);
                    json.addProperty("Minute", minute);
                    json.addProperty("Seconde", seconde);
                    json.addProperty("Valeur", valeur);
                    json.addProperty("Jour", jour);
                    json.addProperty("Mois", mois);

                    System.out.println(json);

                    i[0] = i[0] + 1;
                    System.out.println("envoie :" + i[0]);

                    out.println(json);
                    out.flush();

                    s.resetMax();
                    Thread.sleep(1000);
                }
                l.stopRun();
                ik.close();

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (PhidgetException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }


    }
    /**
     * Arrête le Thread si il est actif
     */
    public void stopRun(){
        isRun=false;
    }
}
