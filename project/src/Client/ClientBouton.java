package Client;

import Client.Listener.SensorChangeListenerButton;
import com.phidgets.PhidgetException;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;

import java.net.Socket;

/**
 * Created by François on 29-10-16.
 */
public class ClientBouton {

    public static void main(String[] zero) throws IOException {


        Socket socket = new Socket();

        PrintWriter out = null;
        BufferedReader in = null;

        int i[] = {0};



        try{
            socket = new Socket(InetAddress.getLocalHost(),2001);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            SensorChangeListenerButton s =new SensorChangeListenerButton(out,i);


            Thread envoie = new Thread(new Envoie(out,i,s));
            Thread reception =new Thread(new Reception(in,i,out));
            envoie.start();
            reception.start();

            envoie.wait();
            reception.wait();

            socket.close();



        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
