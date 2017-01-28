package serveur;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import serveur.Bouton.ServeurBouton;
import serveur.Force.ServeurForce;
import serveur.Temperature.ServeurTemp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;

/**
 * Created by User on 15-12-16.
 */
public class Serveur implements Runnable {


    Socket socket = new Socket();

    PrintWriter out = null;
    BufferedReader in = null;



    Connection connection;



    public Serveur(Socket socket, Connection c) {

        this.socket=socket;
        this.connection = c;
        try {
            socket.setSoTimeout(2147483647);//Permet d'éviter un TimeOut pendant l'utilisation du dispositif (24 j fonctionnel)
        } catch (SocketException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void run() {
        try {



            System.out.println(socket);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            String  message = in.readLine();
            System.out.println("test"+message);

            //le premier message envoyé après une connection entre une boite et le serveur est le numérau de la boite et le numéraux de son type
            //Boite Bouton=0;
            //Boite Température=1;
            //Boite Force=2;
            JsonParser parser =new JsonParser();
            JsonObject json = parser.parse(message).getAsJsonObject();
            JsonElement typeBoite =json.get("numType");
            JsonElement numBoite =json.get("numBoite");

            try{
                //Création du Thread selon le type de boite
                switch (typeBoite.getAsInt()){
                    case 0: Thread t0=new Thread(new ServeurBouton(numBoite.getAsInt(),connection,in,out));
                            t0.start();
                            while(t0.isAlive()){Thread.sleep(50);}
                        socket.close();
                            break;
                    case 1: Thread t1=new Thread(new ServeurTemp(numBoite.getAsInt(),connection,in,out));
                            t1.start();
                            while(t1.isAlive()){Thread.sleep(50);}
                            socket.close();
                            break;
                    case 2: Thread t2=new Thread(new ServeurForce(numBoite.getAsInt(),connection,in,out));
                            t2.start();
                            while(t2.isAlive()){Thread.sleep(50);}
                            socket.close();
                            break;
                    default:break;

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
