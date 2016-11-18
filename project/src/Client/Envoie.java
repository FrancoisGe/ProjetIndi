package Client;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.phidgets.InterfaceKitPhidget;
import com.phidgets.PhidgetException;

import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by François on 29-10-16.
 */
public class Envoie implements Runnable{

    private  PrintWriter out;
    private InterfaceKitPhidget ik;

    private int[] i;

    public Envoie(PrintWriter out,int[] i){
        this.out = out;
        this.i =i;
    }

    @Override
    public void run() {
        try {
           ik= OpenNewPhidget.initIK(317446,out,i);


        } catch (Exception e) {
            e.printStackTrace();
        }
        int x=0;
       // while (true){




       // }
    }



}
