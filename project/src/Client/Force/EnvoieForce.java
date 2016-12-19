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
    private int[]max;



    private int[] i;

    public EnvoieForce(PrintWriter out,int[] i,SensorChangeListenerForce s,int[] max){
        this.out = out;
        this.i =i;
        this.s=s;
        this.max=max;


    }

    @Override
    public void run() {





        try {
            ik = OpenNewPhidget.initIK(out, i, s);

            Thread led = new Thread(new LedAffichage(ik,100));
            led.start();

            while (isRun) {


                Date date = new Date();
                int seconde = date.getSeconds();
                int minute = date.getMinutes();
                int heure = date.getHours();
                int jour = date.getDate();
                int mois = date.getMonth();
                int valeur =max[0];


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

                max[0]=0;
                Thread.sleep(1000);


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
