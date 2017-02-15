package Client.Force;

import Client.LedAffichage;
import Client.Listener.SensorChangeListenerForce;
import Client.OpenNewPhidget;
import com.google.gson.JsonObject;
import com.phidgets.InterfaceKitPhidget;
import com.phidgets.PhidgetException;

import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by François on 11-12-16.
 */
public class EnvoieForce implements Runnable{

    private PrintWriter out;
    private InterfaceKitPhidget ik;
    private SensorChangeListenerForce s;
    private boolean isRun=true;




    private int[] i;

    public EnvoieForce(PrintWriter out, int[] i, SensorChangeListenerForce s){
        this.out = out;
        this.i =i;
        this.s=s;
    }

    @Override
    public void run() {





        try {
            ik = OpenNewPhidget.initIK(out, i, s);//Création de l objet gérant les phidget

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
                int valeur =s.getMax();//On récupère la somme des valeurs des sensors de forces


                //Création du packet de données (date + la somme des valeurs des sensor de force)
                //On envoie un packet toutes les secondes avec la valeur maximum pendant cette seconde
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

                if (i[0]>50){
                    isRun=false;
                }

                Thread.sleep(1000);
            }
            ik.close();
            l.stopRun();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (PhidgetException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public InterfaceKitPhidget getIk(){
        return ik;
    }

    public void stopRun(){
        isRun=false;
    }
}
