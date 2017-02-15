package serveur.Bouton;

import serveur.Envoie;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by François on 27-11-16.
 */
public class ServeurBouton implements Runnable{

    private PrintWriter out = null;
    private BufferedReader in = null;

    private int numBoite;

    private int[] checkReception = {0};//Est utilisé pour vérifie le nombre de message envoyé au serveur moins le nombres de message que le serveur a recu

    private Connection connection;
    private Statement statement;

    private boolean pageOuverte;
    private boolean verbose;


    public ServeurBouton(int numBoite, Connection c,BufferedReader in,PrintWriter out,boolean pageOuverte,boolean verbose){

        this.numBoite=numBoite;
        this.connection= c;
        this.in=in;
        this.out=out;
        this.pageOuverte=pageOuverte;
        this.verbose=verbose;

    }

    @Override
    public void run() {

        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        String nom = "BoiteBouton"+numBoite;
        try {
            //On créer la table si elle n'existe pas
            String sql = "CREATE TABLE "+nom+" ("+
            "Jour	INTEGER NOT NULL,"+
            "Heure	INTEGER NOT NULL,"+
            "Ind	INTEGER NOT NULL,"+
            "Valeur	INTEGER NOT NULL);";

            if (verbose){System.out.println(sql);}

            statement.executeUpdate(sql);

        } catch (SQLException e) {
            if (verbose) {System.out.println("La table de la "+ nom+" existe déjà dans la BD");}
        }

        //Thread qui recoit et traite les packets de données
        ReceptionBouton rec=new ReceptionBouton(in,checkReception,statement,numBoite,verbose);
        Thread reception = new Thread(rec);


        //Thread qui envoie la confirmation de réception des données
        Envoie env=new Envoie(out,checkReception,verbose,nom);
        Thread envoie = new Thread(env);

        //Thread qui ouvre la page Web qui affiche les données et qui créer/met à jour les fichiers de données utilisé par les page web
        UpdateDataScreenBouton screenUp =new UpdateDataScreenBouton(connection,numBoite,pageOuverte);
        Thread screen = new Thread(screenUp);
        screen.start();

        reception.start();
        envoie.start();


        try {
            while (reception.isAlive()){
                Thread.sleep(50);

            }

            screenUp.stopRun();
            env.stopRun();

        }   catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
