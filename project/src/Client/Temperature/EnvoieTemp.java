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

    public EnvoieTemp(PrintWriter out,int[] i,SensorChangeListenerTemperature s){
        this.out = out;
        this.i =i;
        this.s=s;


    }

    @Override
    public void run() {





            try {
                ik = OpenNewPhidget.initIK(out, i, s);
                ik.setSensorChangeTrigger(0,2);

                Thread led = new Thread(new LedAffichage(ik,5));
                led.start();

                while (isRun) {

                    Date date = new Date();
                    int seconde = date.getSeconds();
                    int minute = date.getMinutes();
                    int heure = date.getHours();
                    int jour = date.getDate();
                    int mois = date.getMonth();
                    double valeur = (ik.getSensorValue(0) * 0.2222) - 61.111;


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


                    Thread.sleep(10000);


                }
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
