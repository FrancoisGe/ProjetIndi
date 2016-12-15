package Client.Bouton;

import Client.Listener.SensorChangeListenerButton;
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
 * Created by François on 29-10-16.
 */
public class ClientBouton {

    public static void main(String[] args) throws IOException {


        Socket socket = new Socket();

        PrintWriter out = null;
        BufferedReader in = null;

        int numType=0;
        int numBoite=1;//utiliser un fichier de propriété


        int i[] = {0};

        int j =-1;
        boolean noSocket=true;

        while ((j<10)&&noSocket){
            try {


                j++;
                socket = new Socket("192.168.0.6", 2000 + j);//utiliser un fichier de propriété pour l IP

                noSocket = false;

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

            SensorChangeListenerButton s =new SensorChangeListenerButton(out,i);

            Envoie env = new Envoie(out,i,s);
            Thread envoie = new Thread(env);
            Thread reception =new Thread(new Reception(in,i,out));
            envoie.start();
            reception.start();


            while(reception.isAlive()){
                Thread.sleep(50);

            }

            env.getIk().close();



            socket.close();







        }

        catch (IOException e) {
            e.printStackTrace();

        } catch (PhidgetException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
