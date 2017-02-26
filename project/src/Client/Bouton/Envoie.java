package Client.Bouton;


import Client.OpenNewPhidget;
import com.phidgets.InterfaceKitPhidget;
import com.phidgets.event.SensorChangeListener;



/**
 * Created by François on 29-10-16.
 */
public class Envoie implements Runnable{

    private InterfaceKitPhidget ik;
    private SensorChangeListener s;

    /**
     * Ce Thread quand il est actif, il configure et active l'interfaceKitPhidget pour l'envoie des données récoltées.
     *
     * @param s SensorChangeListener utiliser pour configurer l'interfaceKitPhidget
     */
    public Envoie(SensorChangeListener s){

        this.s=s;
    }

    @Override
    public void run() {
        try {
            ik= OpenNewPhidget.initIK(s);//création de la connection avec les phidgets
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public InterfaceKitPhidget getIk(){
        return this.ik;
    }

}
