package Client.Bouton;

import Client.Listener.SensorChangeListenerButton;
import Client.Reception;
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

    public static void main(String[] args) throws IOException {


        Socket socket = new Socket();

        PrintWriter out = null;
        BufferedReader in = null;

        int i[] = {0};



        try{
            socket = new Socket(InetAddress.getLocalHost(),2002);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            SensorChangeListenerButton s =new SensorChangeListenerButton(out,i);

            Envoie env = new Envoie(out,i,s);
            Thread envoie = new Thread(env);
            Thread reception =new Thread(new Reception(in,i,out));
            envoie.start();
            reception.start();


            while(reception.isAlive()){

                }

            env.getIk().close();



            socket.close();







        }

        catch (IOException e) {
            e.printStackTrace();

        } catch (PhidgetException e) {
            e.printStackTrace();
        }


    }
}
