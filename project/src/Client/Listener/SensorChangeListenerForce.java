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


            InterfaceKitPhidget ik = (InterfaceKitPhidget) sensorChangeEvent.getSource();

            valeur = ik.getSensorValue(0) + ik.getSensorValue(2)+  ik.getSensorValue(4) +ik.getSensorValue(6);
            if (valeur>max[0]){max[0]=valeur;}


        } catch (PhidgetException e) {
            e.printStackTrace();
        }
   }

    public void resetMax(){
        //Post : la valeur de Maximum enregistée est mise à 0.
        max[0]=0;
    }

    public int getMax(){
        //Post : return la valeur de Maximum enregistrée.
        return max[0];
    }
}
