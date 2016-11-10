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

    public SensorChangeListenerOp(PrintWriter out, InterfaceKitPhidget ik){

        this.out = out;
        this.ik=ik;
    }
    @Override
    public void sensorChanged(SensorChangeEvent sensorChangeEvent) {
        System.out.println("coucou");
        int x= 0;
        try {
            x = ik.getSensorValue(0);
        } catch (PhidgetException e) {
            e.printStackTrace();
        }
        Date date = new Date();
        long hour =   date.getTime();

        JsonObject json = new JsonObject();
        json.addProperty("hour",hour);
        json.addProperty("Value",x);
        System.out.print(json);
        // String message = gson.toJson(json);
        out.println(json);
        out.flush();
    }
}
