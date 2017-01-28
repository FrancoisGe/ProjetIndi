package serveur.Temperature;

import jdk.nashorn.internal.ir.WhileNode;
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
 * Created by François on 03-12-16.
 */
public class ServeurTemp implements Runnable{

    ServerSocket socketserver;
    Socket socket = new Socket();

    PrintWriter out = null;
    BufferedReader in = null;

    int numBoite;

    int[] checkReception = {0};//Est utilisé pour vérifie le nombre de message envoyé au serveur moins le nombres de message que le serveur a recu

    Connection connection;
    Statement statement;


    public ServeurTemp(int numBoite, Connection c,BufferedReader in,PrintWriter out){


        this.numBoite=numBoite;
        this.connection= c;
        this.in =in;
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
            //Création de la table si elle n'existe pas
        String sql = "CREATE TABLE BoiteTemp"+numBoite+" ("+
            "Valeur	REAL NOT NULL,"+
            "Jour	INTEGER NOT NULL,"+
            "Heure	INTEGER NOT NULL,"+
            "Minute	INTEGER NOT NULL,"+
            "Seconde	INTEGER NOT NULL,"+
            "Mois	INTEGER NOT NULL);";

            System.out.println(sql);

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }



        //Thread qui recoit et traite les packets de données
        ReceptionTemp rt =new ReceptionTemp(in,checkReception,statement,numBoite);
        Thread reception = new Thread(rt);

        //Thread qui envoie la confirmation de réception des données
        Envoie ev=new Envoie(out,checkReception);
        Thread envoie = new Thread(ev);

        //Thread qui ouvre la page Web qui affiche les données et qui créer/met à jour les fichiers de données utilisé par les page web
        UptateDataScreenTemp us =new UptateDataScreenTemp(connection,numBoite);
        Thread screen = new Thread(us);

        screen.start();
        reception.start();
        envoie.start();


        try {
            while (reception.isAlive()) {
                Thread.sleep(50);
            }

             rt.stopRun();
             ev.stopRun();
             us.stopRun();

            socket.close();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}