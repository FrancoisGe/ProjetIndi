package Client.Listener;


import com.phidgets.event.SensorChangeEvent;
import com.phidgets.event.SensorChangeListener;


/**
 * Created by User on 02-12-16.
 */
public class SensorChangeListenerTemperature implements SensorChangeListener{


    private int max=0;

    public SensorChangeListenerTemperature(){

    }
    @Override
    public void sensorChanged(SensorChangeEvent sensorChangeEvent) {


        if (max<sensorChangeEvent.getValue()){
            max=sensorChangeEvent.getValue();
        }

    }

    /**
     * la valeur maximum est remise à 0
     */
    public void resetMax(){
        //Post : la valeur de Maximum enregistée est mise à 0.
        max=0;
    }

    /**
     *
     * @return la valeur maximum
     */
    public int getMax(){
        //Post : return la valeur de Maximum enregistrée.
        return max;
    }

    /**
     *
     * @param m devient la nouvel valeur maximum
     */
    public void setValMax(int m){
        if (max<m){
            max=m;
        }
    }
}
