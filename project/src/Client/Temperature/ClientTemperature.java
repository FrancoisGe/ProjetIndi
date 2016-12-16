package Client.Temperature;

import Client.Listener.SensorChangeListenerTemperature;
import Client.Reception;
import com.google.gson.JsonObject;
import com.phidgets.PhidgetException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by User on 02-12-16.
 */
public class ClientTemperature {
    public static void main(String[] args) throws IOException {


        Socket socket = null;

        PrintWriter out = null;
        BufferedReader in = null;

        int numType=1;
        int numBoite=1;//utiliser un fichier de propriété



        int i[] = {0};

        int j =-1;
        boolean noSocket=true;

        while ((j<100)&&noSocket){
           try {


               j++;
               socket = new Socket("192.168.0.6", 2000 + j);//utiliser un fichier de propriété pour l IP

               noSocket = false;
               System.out.println(socket);

           }catch (IOException e){

           }
        }


        try{


            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            JsonObject json = new JsonObject();
            json.addProperty("numType", numType);
            json.addProperty("numBoite", numBoite);

            out.println(json);
            out.flush();



            SensorChangeListenerTemperature s =new SensorChangeListenerTemperature(out,i);

            EnvoieTemp env = new EnvoieTemp(out,i,s);
           // Thread envoie = new Thread(new Envoie(out,i,s));
            Thread envoieTemp = new Thread(env);
            Thread reception =new Thread(new Reception(in,i,out));

            reception.start();
            envoieTemp.start();



            while(reception.isAlive()){
                Thread.sleep(50);

            }


            try {
                env.getIk().close();
            } catch (PhidgetException e) {
                e.printStackTrace();
            }


            env.stopRun();
            socket.close();



        } catch (IOException e) {
            e.printStackTrace();
        }   catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
