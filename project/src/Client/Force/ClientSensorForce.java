package Client.Force;

import Client.DemandeIPServeur;
import Client.Listener.SensorChangeListenerForce;
import Client.Reception;
import com.google.gson.JsonObject;
import com.phidgets.PhidgetException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by François on 11-12-16.
 */
public class ClientSensorForce {
    public static void main(String[] args) throws IOException {


        Socket socket = new Socket();

        PrintWriter out = null;
        BufferedReader in = null;

        int numType=2;//type de boite (2 pour la boiteSensorForce)
        int numBoite=1;

        int i[] = {0};//permet de verifier l etat des données envoyées.

        //trouver IP Serveur et créé la socket
        DemandeIPServeur demIP=new DemandeIPServeur();
        Thread demIpServeur = new Thread(demIP);
        demIpServeur.start();

        socket = demIP.socketIpServeur();




        try{


            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            //Envoie du premier message pour donner le num de la boite et son type
            JsonObject json = new JsonObject();
            json.addProperty("numType", numType);
            json.addProperty("numBoite", numBoite);

            out.println(json);
            out.flush();

            SensorChangeListenerForce s =new SensorChangeListenerForce(out,i);


            EnvoieForce env = new EnvoieForce(out,i,s);//Thread qui envoie les données au serveur
            Thread envoie = new Thread(env);

            Thread reception =new Thread(new Reception(in,i,out));//Thread qui recoit les confimation du serveur
            envoie.start();
            reception.start();


            while(reception.isAlive()){
                Thread.sleep(50);
            }

            try {
                env.getIk().close();
            } catch (PhidgetException e) {
                e.printStackTrace();
            }


            socket.close();


        }

        catch (IOException e) {
            e.printStackTrace();

        }  catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}