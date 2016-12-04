package Client;
import Client.Listener.SensorChangeListenerButton;
import Client.Listener.SensorChangeListenerTemperature;
import com.phidgets.*;
import com.phidgets.event.*;

import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by François on 30-10-16.
 */
public class OpenNewPhidget {






    public static InterfaceKitPhidget initIK( PrintWriter out,int[] i,SensorChangeListener s) throws Exception {
        InterfaceKitPhidget ik = new InterfaceKitPhidget();

        System.out.println(Phidget.getLibraryVersion());


        ik.addDetachListener(new DetachListener() {
            public void detached(DetachEvent ae) {
                System.out.println("detachment of " + ae);
            }
        });
        ik.addErrorListener(new ErrorListener() {
            public void error(ErrorEvent ee) {
                System.out.println("error event for " + ee);
            }
        });


       ik.addSensorChangeListener(s);
        ik.openAny();
        Date date =new Date();
        System.out.println(date);
        System.out.println("waiting for InterfaceKit attachment...");
        ik.waitForAttachment();
        System.out.println(ik.getDeviceName());
        ik.setSensorChangeTrigger(0,2);
        System.out.print("fini");





       return ik;

    }

}

