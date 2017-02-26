package Client.Bouton;

import Client.DemandeIPServeur;
import Client.Listener.SensorChangeListenerButton;
import Client.Reception;
import com.google.gson.JsonObject;
import com.phidgets.PhidgetException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by François on 29-10-16.
 */


public class ClientBouton {

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
        Socket socket = new Socket();

        PrintWriter out = null;
        BufferedReader in = null;

        int numType=0;//type de boite (0 pour la boiteBouton)
        int numBoite=1;


        int i[] = {0};//permet de verifier l etat des données envoyées.

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

            SensorChangeListenerButton s =new SensorChangeListenerButton(out,i);

            Envoie env = new Envoie(s);//Thread qui va envoyer les données au serveur
            Thread envoie = new Thread(env);

            Reception rec = new Reception(in,i);
            Thread reception =new Thread(rec);// Thread qui va recevoir la validation de la reception des données par le serveur
            envoie.start();
            reception.start();

            //Vérifie que Reception est encore en vie et qu'il n'y a pas trop de perte de packets
            while(reception.isAlive() && (i[0]<100)){
                Thread.sleep(50);
            }
            Thread.sleep(5000);

            if(reception.isAlive()) {rec.stopRun();}

            if (env.getIk()==(null)){
            }
            else {
                env.getIk().close();
            }

            socket.close();

            Thread.sleep(100);

            activationBoiteTemp(demIP);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (PhidgetException e) {
            e.printStackTrace();
        }

    }
}
