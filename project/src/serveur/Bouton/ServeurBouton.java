package serveur.Bouton;

import serveur.Envoie;
import serveur.Temperature.UptateDataScreenTemp;

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
 * Created by François on 27-11-16.
 */
public class ServeurBouton implements Runnable{




    PrintWriter out = null;
    BufferedReader in = null;

    int numBoite;

    int[] checkReception = {0};//Est utilisé pour vérifie le nombre de message envoyé au serveur moins le nombres de message que le serveur a recu

    Connection connection;
    Statement statement;


    public ServeurBouton(int numBoite, Connection c,BufferedReader in,PrintWriter out){

        this.numBoite=numBoite;
        this.connection= c;
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
            //On créer la table si elle n'existe pas
            String sql = "CREATE TABLE BoiteBouton"+numBoite+" ("+
            "Jour	INTEGER NOT NULL,"+
            "Heure	INTEGER NOT NULL,"+
            "Ind	INTEGER NOT NULL,"+
            "Valeur	INTEGER NOT NULL);";

            System.out.println(sql);

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Thread qui recoit et traite les packets de données
        ReceptionBouton rec=new ReceptionBouton(in,checkReception,statement,numBoite);
        Thread reception = new Thread(rec);

        //Thread qui envoie la confirmation de réception des données
        Envoie env=new Envoie(out,checkReception);
        Thread envoie = new Thread(env);

        //Thread qui ouvre la page Web qui affiche les données et qui créer/met à jour les fichiers de données utilisé par les page web
        UptateDataScreenButton screenUp =new UptateDataScreenButton(connection,numBoite);
        Thread screen = new Thread(screenUp);
        screen.start();

        reception.start();
        envoie.start();


        try {
            while (reception.isAlive()){
                Thread.sleep(50);

            }

            rec.stopRun();
            screenUp.stopRun();
            env.stopRun();

        }   catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
