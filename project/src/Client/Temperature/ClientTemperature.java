package Client.Temperature;

import Client.EnvoieTemp;
import Client.Listener.SensorChangeListenerButton;
import Client.Listener.SensorChangeListenerTemperature;
import Client.Reception;
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


        Socket socket = new Socket();

        PrintWriter out = null;
        BufferedReader in = null;

        int i[] = {0};



        try{
            socket = new Socket(InetAddress.getLocalHost(),2001);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());


            SensorChangeListenerTemperature s =new SensorChangeListenerTemperature(out,i);

            EnvoieTemp env = new EnvoieTemp(out,i,s);
           // Thread envoie = new Thread(new Envoie(out,i,s));
            Thread envoieTemp = new Thread(env);
            Thread reception =new Thread(new Reception(in,i,out));
           // envoie.start();
            reception.start();
            envoieTemp.start();

          //  envoie.wait();
            reception.wait();

            while(reception.isAlive()){

            }

            env.getIk().close();
            socket.close();



        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (PhidgetException e) {
            e.printStackTrace();
        }


    }
}
