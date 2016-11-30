package Client.Listener;

import Client.Client;
import com.google.gson.JsonObject;
import com.phidgets.InterfaceKitPhidget;
import com.phidgets.PhidgetException;
import com.phidgets.event.SensorChangeEvent;
import com.phidgets.event.SensorChangeListener;

import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by François on 05-11-16.
 */
public class SensorChangeListenerOp implements SensorChangeListener {

    private PrintWriter out ;

    private int[] i;

    public SensorChangeListenerOp(PrintWriter out,int[] i){

        this.out = out;

        this.i=i;
    }
    @Override
    public void sensorChanged(SensorChangeEvent sensorChangeEvent) {




        int valeur = sensorChangeEvent.getValue();

        Date date = new Date();
        long heure =   date.getHours();
        int jour = date.getDay();



        if (valeur >=990) {
            int index = sensorChangeEvent.getIndex();
            System.out.print("valeur event:" + index);

            JsonObject json = new JsonObject();
            json.addProperty("Heure", heure);
            json.addProperty("Valeur", valeur);
            json.addProperty("Index", index);
            json.addProperty("Jour",jour);

            System.out.println(json);

            i[0] = i[0] + 1;
            System.out.println("envoie :" + i[0]);

            out.println(json);
            out.flush();


        }
    }
}
