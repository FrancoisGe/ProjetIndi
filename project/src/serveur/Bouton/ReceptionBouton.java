package serveur.Bouton;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by François on 29-10-16.
 */
public class ReceptionBouton implements Runnable{
    private String message;//Contient le dernier message recu
    private BufferedReader in;
    private JsonParser parser;

    private int[] i;//Compteur du nombre de packet de données recu
    private Statement state;
    private String sql;
    private int numBoite;
    private boolean isRun;
    private boolean verbose;

    public ReceptionBouton(BufferedReader in, int[] i, Statement state, int numBoite,boolean verbose){
        parser = new JsonParser();
        this.in = in;
        this.i = i;
        this.state=state;
        this.numBoite=numBoite;
        this.isRun =true;
        this.verbose=verbose;
    }



    public void run(){


        while(isRun){
            try {
                message = in.readLine();

                    //On  parse le packet de données recu
                    JsonObject json = parser.parse(message).getAsJsonObject();
                    System.out.println(message);


                    i[0] = i[0] + 1;//On incrémente le compteur car on a un packet de données en plus

                    //On met dans la BD les données recues
                    sql = "INSERT INTO BoiteBouton"+numBoite+" (Valeur,Jour,Ind,Heure) " +
                            "VALUES (" + json.get("Valeur") + "," + json.get("Jour") + "," + json.get("Index") + ","+json.get("Heure")+");";
                    if(verbose){ System.out.println(sql);}
                    state.executeUpdate(sql);

            } catch (IOException e) {
                isRun=false;
                e.printStackTrace();
           } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public void stopRun(){
        isRun=false;
    }
}
