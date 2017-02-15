package serveur.Temperature;

import serveur.Envoie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by François on 03-12-16.
 */
public class ServeurTemp implements Runnable{

    private Socket socket = new Socket();

    private PrintWriter out = null;
    private BufferedReader in = null;

    private int numBoite;

    private int[] checkReception = {0};//Est utilisé pour vérifie le nombre de message envoyé au serveur moins le nombres de message que le serveur a recu

    private Connection connection;
    private Statement statement;

    private boolean pageOuverte;
    private boolean verbose;


    public ServeurTemp(int numBoite, Connection c,BufferedReader in,PrintWriter out,boolean pageOuverte,boolean verbose){

        this.numBoite=numBoite;
        this.connection= c;
        this.in =in;
        this.out=out;
        this.pageOuverte = pageOuverte;
        this.verbose=verbose;
    }

    @Override
    public void run() {

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String nom = "BoiteTemp"+ numBoite;
        try {
            //Création de la table si elle n'existe pas
        String sql = "CREATE TABLE "+nom+" ("+
            "Valeur	REAL NOT NULL,"+
            "Jour	INTEGER NOT NULL,"+
            "Heure	INTEGER NOT NULL,"+
            "Minute	INTEGER NOT NULL,"+
            "Seconde	INTEGER NOT NULL,"+
            "Mois	INTEGER NOT NULL);";

            if (verbose){System.out.println(sql);}

            statement.executeUpdate(sql);
        } catch (SQLException e) {
            if (verbose) {System.out.println("La table de la "+ nom+" existe déjà dans la BD");}
        }

        //Thread qui recoit et traite les packets de données
        ReceptionTemp rt =new ReceptionTemp(in,checkReception,statement,numBoite,verbose);
        Thread reception = new Thread(rt);


        //Thread qui envoie la confirmation de réception des données
        Envoie ev=new Envoie(out,checkReception,verbose,nom);
        Thread envoie = new Thread(ev);


        //Thread qui ouvre la page Web qui affiche les données et qui créer/met à jour les fichiers de données utilisé par les page web
        UpdateDataScreenTemp us = new UpdateDataScreenTemp(connection, numBoite,pageOuverte);
        Thread screen = new Thread(us);


        screen.start();
        reception.start();
        envoie.start();


        try {
            while (reception.isAlive()) {
                Thread.sleep(50);
            }

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