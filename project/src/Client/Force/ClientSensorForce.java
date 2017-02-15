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

        //trouver IP Serveur et créé la socket
        DemandeIPServeur demIP=new DemandeIPServeur();
        Thread demIpServeur = new Thread(demIP);
        demIpServeur.start();

        activationBoiteTemp(demIP);


    }

    private static void activationBoiteTemp(DemandeIPServeur demIP){
        Socket socket = new Socket();

        PrintWriter out = null;
        BufferedReader in = null;

        int numType=2;//type de boite (2 pour la boiteSensorForce)
        int numBoite=1;

        int i[] = {0};//permet de verifier l etat des données envoyées.
        int max[]={0};//Valeur maximum enregistrée par la somme des capteurs de forces





        try{
            socket = demIP.socketIpServeur();

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            //Envoie du premier message pour donner le num de la boite et son type
            JsonObject json = new JsonObject();
            json.addProperty("numType", numType);
            json.addProperty("numBoite", numBoite);

            out.println(json);
            out.flush();

            SensorChangeListenerForce s =new SensorChangeListenerForce(out,max);


            EnvoieForce env = new EnvoieForce(out,i,s);//Thread qui envoie les données au serveur
            Thread envoie = new Thread(env);

            Reception rec = new Reception(in,i,out,numBoite);
            Thread reception =new Thread(rec);// Thread qui va recevoir la validation de la reception des données par le serveur
            envoie.start();
            reception.start();

            //Vérifie que Reception et Envoie sont encore en vie et qu'il n'y a pas trop de perte de packets
            while(reception.isAlive() && (i[0]<50) && envoie.isAlive()){
                Thread.sleep(50);
            }


            if (reception.isAlive()) {rec.stopRun();}
            if (envoie.isAlive()) {env.stopRun();}

            socket.close();

            Thread.sleep(1000);

            activationBoiteTemp(demIP);

        } catch (IOException e) {
            e.printStackTrace();

        }  catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


}