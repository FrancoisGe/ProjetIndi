package serveur.fonctionTest;

import com.phidgets.InterfaceKitPhidget;
import com.phidgets.Phidget;
import com.phidgets.PhidgetException;
import com.phidgets.event.*;

/**
 * Created by François on 13-11-16.
 */
public class test {
    public static void main(String[] zero) {
        java.util.Date date = new java.util.Date();
        //date.setTime(1000000);
        System.out.println("voici le test :" + date.getHours());
        System.out.println("voici le jour"+ date.getDay());
        System.out.println(date.getDate());



           /* Class.forName("org.sqlite.JDBC");
            System.out.println("Driver O.K.");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\ProjetIndividuel\\project\\BaseDeDonnees.db");
            System.out.println("Opened database successfully");

            String sql = "INSERT INTO box3 (Valeur,Date,Ind) " +
                    "VALUES (" + 1200 + "," + 12 + "," + 14 + ");";
            System.out.println(sql);
            Statement state = conn.createStatement();
            state.executeUpdate(sql);*/
            /*System.out.println(date.getDay());


            File f2 = new File("C:\\wamp\\www\\data.csv") ;//fichier data pour mapage3


            ResourceBundle rb = ResourceBundle.getBundle("serveur.domaine.properties.config");
            String driver = rb.getString("sgbd.driver");
            System.out.print(driver);



        try {
            Process proc = Runtime.getRuntime().exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe http://localhost/mapage2");
        } catch (IOException e1) {
                e1.printStackTrace();
        }
        //Runtime.getRuntime().ex*/
       /* try{
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




            ik.addSensorChangeListener(new SensorChangeListener() {
                @Override
                public void sensorChanged(SensorChangeEvent sensorChangeEvent) {
                    System.out.println(sensorChangeEvent.getValue());
                }
            });
            ik.openAny();
            java.util.Date date = new java.util.Date();
            System.out.println(date);
            System.out.println("waiting for InterfaceKit attachment...");
            ik.waitForAttachment();
            System.out.println(ik.getDeviceName());
            for (int i = 0; i < 100; i++) {
                double x = ik.getSensorValue(0)*0.2222-61.111;

                System.out.println("test : "+x);
                Thread.sleep(1000);

            }
        } catch (PhidgetException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

    }






}

