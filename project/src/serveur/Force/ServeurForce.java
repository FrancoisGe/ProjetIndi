package serveur.Force;

import Client.Force.EnvoieForce;
import serveur.Bouton.ReceptionBouton;
import serveur.Bouton.UptateDataScreenButton;
import serveur.Envoie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by François on 11-12-16.
 */
public class ServeurForce implements Runnable {


    Socket socket = new Socket();

    PrintWriter out = null;
    BufferedReader in = null;

    int numBoite;

    int[] checkReception = {0};//Est utilisé pour vérifie le nombre de message envoyé au serveur moins le nombres de message que le serveur a recu

    Connection connection;
    Statement statement;


    public ServeurForce(int numBoite, Connection c,BufferedReader in,PrintWriter out) {

        this.numBoite = numBoite;
        this.connection = c;
        this.in=in;
        this.out=out;



    }

    @Override
    public void run() {

            try {
                statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                String sql = "CREATE TABLE BoiteForce"+numBoite+"("+
                "Valeur	INTEGER NOT NULL,"+
                "Jour	INTEGER NOT NULL,"+
                "Heure	INTEGER NOT NULL,"+
                "Minute	INTEGER NOT NULL,"+
                "Mois	INTEGER NOT NULL,"+
                "Seconde	INTEGER NOT NULL);";

                System.out.println(sql);

                statement.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }








        ReceptionForce rec=new ReceptionForce(in,checkReception,statement,numBoite);
        Thread reception = new Thread(rec);
        Envoie env=new Envoie(out,checkReception);
        Thread envoie = new Thread(env);
        UptateDataScreenForce screenUp =new UptateDataScreenForce(connection,numBoite);
        Thread screen = new Thread(screenUp);

        screen.start();
        reception.start();
        envoie.start();


        try {
            while (reception.isAlive()) {
                Thread.sleep(50);

            }
            env.stopRun();
            screenUp.stopRun();


            socket.close();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}