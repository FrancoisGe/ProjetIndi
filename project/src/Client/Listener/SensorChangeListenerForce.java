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

    private int[] max;


    public SensorChangeListenerForce(PrintWriter out, int[] max){

        this.out = out;


        this.max=max;
    }
    @Override
    public void sensorChanged(SensorChangeEvent sensorChangeEvent) {



        try {

            int valeur = sensorChangeEvent.getValue();
            if (sensorChangeEvent.getIndex() == 0) {

                InterfaceKitPhidget ik = (InterfaceKitPhidget) sensorChangeEvent.getSource();

                valeur = valeur + ik.getSensorValue(2)+  ik.getSensorValue(4) +ik.getSensorValue(6);
                System.out.println(valeur);
                if (valeur>max[0]){max[0]=valeur;}



            }
        } catch (PhidgetException e) {
            e.printStackTrace();
        }




   }
}
