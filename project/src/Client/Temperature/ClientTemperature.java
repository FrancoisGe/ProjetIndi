package Client.Temperature;

import Client.DemandeIPServeur;
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

        int numType=1;//type de boite (1 pour la boiteTemperature)
        int numBoite=1;


        int i[] = {0};//permet de verifier l etat des données envoyées.

        //trouver IP Serveur et créé la socket
        DemandeIPServeur demIP=new DemandeIPServeur();
        Thread demIpServeur = new Thread(demIP);
        demIpServeur.start();

        socket =demIP.socketIpServeur();


        try{


            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            //Envoie du premier message pour donner le num de la boite et son type
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
