package Client.Bouton;

import Client.Listener.SensorChangeListenerButton;
import Client.OpenNewPhidget;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.phidgets.InterfaceKitPhidget;
import com.phidgets.PhidgetException;
import com.phidgets.event.SensorChangeListener;

import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by François on 29-10-16.
 */
public class Envoie implements Runnable{

    private  PrintWriter out;
    private InterfaceKitPhidget ik;
    private SensorChangeListener s;

    private int[] i;

    public Envoie(PrintWriter out,int[] i,SensorChangeListener s){
        this.out = out;
        this.i =i;
        this.s=s;
    }

    @Override
    public void run() {
        try {
            ik= OpenNewPhidget.initIK(out,i,s);


        } catch (Exception e) {
            e.printStackTrace();
        }
        int x=0;



    }
    public InterfaceKitPhidget getIk(){
        return this.ik;
    }




}
