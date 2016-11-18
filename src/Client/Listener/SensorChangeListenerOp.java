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
    private InterfaceKitPhidget ik;
    private int[] i;

    public SensorChangeListenerOp(PrintWriter out, InterfaceKitPhidget ik,int[] i){

        this.out = out;
        this.ik=ik;
        this.i=i;
    }
    @Override
    public void sensorChanged(SensorChangeEvent sensorChangeEvent) {

        int x= 0;
        try {
            x = ik.getSensorValue(0);
        } catch (PhidgetException e) {
            e.printStackTrace();
        }
        Date date = new Date();
        long hour =   date.getTime();


        JsonObject json = new JsonObject();
        json.addProperty("Hour",hour);
        json.addProperty("Value",x);
        System.out.println(json);
        // String message = gson.toJson(json);
        i[0]=i[0]+1;
        System.out.println("envoie :"+i[0]);

        out.println(json);
        out.flush();
    }
}
