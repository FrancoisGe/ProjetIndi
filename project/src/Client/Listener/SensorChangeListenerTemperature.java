package Client.Listener;

import com.google.gson.JsonObject;
import com.phidgets.InterfaceKitPhidget;
import com.phidgets.PhidgetException;
import com.phidgets.event.SensorChangeEvent;
import com.phidgets.event.SensorChangeListener;

import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by User on 02-12-16.
 */
public class SensorChangeListenerTemperature implements SensorChangeListener{


    private PrintWriter out ;

    private int[] i;



    public SensorChangeListenerTemperature(PrintWriter out, int[] i){

        this.out = out;

        this.i=i;

    }
    @Override
    public void sensorChanged(SensorChangeEvent sensorChangeEvent) {

        Date date = new Date();
        int seconde = date.getSeconds();
        int minute = date.getMinutes();
        int heure = date.getHours();
        int jour = date.getDate();
        int mois =date.getMonth();








        double valeur = (sensorChangeEvent.getValue()*0.2222)-61.111;
        if (valeur>-5) {
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


    }
}
