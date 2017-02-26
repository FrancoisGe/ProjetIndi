package Client;

import com.phidgets.*;
import com.phidgets.event.*;


import java.util.Date;

/**
 * Created by François on 30-10-16.
 */
public class OpenNewPhidget {


    /**
     * Création de l'interfaceKitPhidget adapté au type de boite et configuration de celui-ci(les differents Listeners)
     *
     * @param s SensorChangeListener qui sera utilisé par l'interfaceKitPhidget créé (Dépend du type de boite)
     * @return L'InterfaceKitPhidget configuré et adapté à la boite.
     * @throws Exception
     */

    public static InterfaceKitPhidget initIK(SensorChangeListener s) throws Exception {

        //Post : création de l'InterfaceKitPhidget (en utilisant le SensorChangeListener mis en param)

        InterfaceKitPhidget ik = new InterfaceKitPhidget();

        System.out.println(Phidget.getLibraryVersion());

        ik.addAttachListener(new AttachListener() {
            @Override
            public void attached(AttachEvent attachEvent) {

            }
        });
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


        ik.addSensorChangeListener(s);//On connect le tpe de listener entré en param
        ik.openAny();
        Date date =new Date();
        System.out.println(date);
        System.out.println("waiting for InterfaceKit attachment...");
        ik.waitForAttachment();
        System.out.println(ik.getDeviceName());
        return ik;

    }

}

