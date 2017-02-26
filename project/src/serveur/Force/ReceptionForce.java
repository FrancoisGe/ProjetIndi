package serveur.Force;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by François on 11-12-16.
 */
public class ReceptionForce implements Runnable{
    private String message;//Contient le dernier message recu
    private BufferedReader in;
    private JsonParser parser;

    private int[] i;
    private Statement state;
    private String sql;
    private int numBoite;
    private boolean isRun =true;
    private boolean verbose;

    /**
     * Quand ce Thread est actif il gère la réception des données émise par la boite connectée et leur insertion dans la BD
     *
     * @param in : buffer dans lequel on va communiquer avec la boite connectée
     * @param i : à l'indice 0, on a le nombre de packets de données reçus que l'on n'a pas encore averti la boite de leur réception
     * @param state : statement dans lequel on va insérer les données reçues
     * @param numBoite : numéro de la boite avec laquel on communique et à encoder dans la BD
     * @param verbose : Permet de savoir si le mode verbose est actif
     *                  True = actif
     *                  False = desactivé
     */
    public ReceptionForce (BufferedReader in, int[] i, Statement state, int numBoite,boolean verbose){
        parser = new JsonParser();
        this.in = in;
        this.i = i;
        this.state=state;
        this.numBoite=numBoite;
        this.verbose=verbose;
    }



    public void run(){

        while(isRun){
            try {
                message = in.readLine();

                //On  parse le packet de données recu
                JsonObject json = parser.parse(message).getAsJsonObject();

                i[0] = i[0] + 1;//On incrémente le compteur car on a un packet de données en plus

                //On ajoute les données dans la BD
                sql = "INSERT INTO BoiteForce"+numBoite+" (Valeur,Mois,Jour,Heure,Minute,Seconde) " +
                        "VALUES (" + json.get("Valeur") + "," + json.get("Mois") +"," + json.get("Jour") + "," + json.get("Heure") + ","+ json.get("Minute") + ","+json.get("Seconde")+");";
                if(verbose) {System.out.println(sql);}
                state.executeUpdate(sql);


            } catch (IOException e) {
                isRun=false;
                if(verbose){System.out.println("La BoiteForce "+numBoite+" a été déconnectée");}
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Arrête le Thread si il est actif
     */
    public void stopRun(){
        isRun=false;
    }
}
