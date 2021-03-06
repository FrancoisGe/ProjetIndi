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

        //trouver IP Serveur et créé la socket
        DemandeIPServeur demIP=new DemandeIPServeur();
        Thread demIpServeur = new Thread(demIP);
        demIpServeur.start();
        activationBoiteTemp(demIP);


    }
    /**
     *
     * @param demIP Objet qui nous permet de récuperer l'ip du serveur. Il doit déjà avoir été créé et activé.
     */

    private static void activationBoiteTemp(DemandeIPServeur demIP){
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        int numType=1;//type de boite (1 pour la boiteTemperature)
        int numBoite=1;

        int i[] = {0};//permet de verifier l etat des données envoyées.

        try{
            socket = demIP.socketIpServeur();
            System.out.println(socket);


            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            //Envoie du premier message pour donner le num de la boite et son type
            JsonObject json = new JsonObject();
            json.addProperty("numType", numType);
            json.addProperty("numBoite", numBoite);

            out.println(json);
            out.flush();

            SensorChangeListenerTemperature s =new SensorChangeListenerTemperature();

            EnvoieTemp env = new EnvoieTemp(out,i,s);
            Thread envoieTemp = new Thread(env);
            Reception rec =new Reception(in,i);
            Thread reception =new Thread(rec);

            reception.start();
            envoieTemp.start();

            //Vérifie que Reception et Envoie sont encore en vie et qu'il n'y a pas trop de perte de packets
            while(reception.isAlive() && (i[0]<50) && envoieTemp.isAlive()){
                Thread.sleep(50);
            }

            Thread.sleep(1000);



            if (envoieTemp.isAlive()) {env.stopRun();}
            if(reception.isAlive()) {rec.stopRun();}

            socket.close();

            Thread.sleep(1000);

            activationBoiteTemp(demIP);//Permet de relancer les Threads quand il y a une exception dans les sockets


        } catch (IOException e) {
            e.printStackTrace();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            activationBoiteTemp(demIP);

        }   catch (InterruptedException e) {
            e.printStackTrace();

        }


    }
}
