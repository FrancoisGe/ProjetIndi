package Client.Listener;


import com.phidgets.InterfaceKitPhidget;
import com.phidgets.PhidgetException;
import com.phidgets.event.SensorChangeEvent;
import com.phidgets.event.SensorChangeListener;
import java.io.PrintWriter;


/**
 * Created by François on 11-12-16.
 */
public class SensorChangeListenerForce implements SensorChangeListener {

    private int[] max;


    public SensorChangeListenerForce(PrintWriter out, int[] max){


        this.max=max;
    }
    @Override
    public void sensorChanged(SensorChangeEvent sensorChangeEvent) {



        try {
            //Modifie la valeur maximum si la valeur détectée est supérieur au max précédent.

            int valeur ;
            if (sensorChangeEvent.getIndex() == 0) {

                InterfaceKitPhidget ik = (InterfaceKitPhidget) sensorChangeEvent.getSource();

                valeur = ik.getSensorValue(0) + ik.getSensorValue(2)+  ik.getSensorValue(4) +ik.getSensorValue(6);
                System.out.println(valeur);
                if (valeur>max[0]){max[0]=valeur;}



            }
        } catch (PhidgetException e) {
            e.printStackTrace();
        }




   }
}
