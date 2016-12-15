package Client.Listener;

import com.google.gson.JsonObject;
import com.phidgets.InterfaceKitPhidget;
import com.phidgets.PhidgetException;
import com.phidgets.event.SensorChangeEvent;
import com.phidgets.event.SensorChangeListener;

import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by François on 11-12-16.
 */
public class SensorChangeListenerForce implements SensorChangeListener {

    private PrintWriter out ;

    private int[] i;

    public SensorChangeListenerForce(PrintWriter out, int[] i){

        this.out = out;

        this.i=i;
    }
    @Override
    public void sensorChanged(SensorChangeEvent sensorChangeEvent) {



        try {

            int valeur = sensorChangeEvent.getValue();
            if (sensorChangeEvent.getIndex() == 0) {

                InterfaceKitPhidget ik = (InterfaceKitPhidget) sensorChangeEvent.getSource();
                ik.setSensorChangeTrigger(0,100);
                valeur = valeur + ik.getSensorValue(2)+  ik.getSensorValue(4) +ik.getSensorValue(6);


                i[0] = i[0] + 1;
                System.out.println("envoie :" + i[0]);
                Date date = new Date();
                int seconde = date.getSeconds();
                int minute = date.getMinutes();
                int heure = date.getHours();
                int jour = date.getDate();
                int mois =date.getMonth();










                JsonObject json = new JsonObject();
                json.addProperty("Heure", heure);
                json.addProperty("Minute", minute);
                json.addProperty("Seconde", seconde);
                json.addProperty("Valeur", valeur);
                json.addProperty("Jour", jour);
                json.addProperty("Mois",mois);

                System.out.println(json);

                i[0] = i[0] + 1;
                System.out.println("envoie :" + i[0]);

                out.println(json);
                out.flush();




            }
        } catch (PhidgetException e) {
            e.printStackTrace();
        }




   }
}
