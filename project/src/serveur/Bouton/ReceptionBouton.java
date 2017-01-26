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
    private boolean pasErreur;//Vrai tant qu'on a pas recu de message d erreur d'une boite.
    private boolean isRun;

    public ReceptionBouton(BufferedReader in, int[] i, Statement state, int numBoite){
        parser = new JsonParser();
        this.in = in;
        this.i = i;
        this.state=state;
        this.numBoite=numBoite;
        this.pasErreur =true;
        this.isRun =true;
    }



    public void run(){


        while(isRun){
            try {
                message = in.readLine();
                //Si on recoit un message d'erreur on ouvre une fenetre pour en avertir l'utilisateur du serveur
                if (message.equals("erreur "+numBoite)) {
                    if (pasErreur){
                        System.out.println("il y a un problème !!!!!!!!!!!!!!!");
                        File f = new File("C:\\ProjetIndividuel\\project\\src\\serveur\\erreur.html");
                        FileWriter fw = new FileWriter(f);
                        fw.write("Il y a un problème a la boite " + numBoite);
                        fw.close();
                        Process proc = Runtime.getRuntime().exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe C:\\ProjetIndividuel\\project\\src\\serveur\\erreur.html");
                        pasErreur = false;
                    }
                }
                else {

                    pasErreur=true;

                    //On  parse le packet de données recu
                    JsonObject json = parser.parse(message).getAsJsonObject();
                    System.out.println(message);


                    i[0] = i[0] + 1;

                    //On met dans la BD les données recues
                    sql = "INSERT INTO BoiteBouton"+numBoite+" (Valeur,Jour,Ind,Heure) " +
                            "VALUES (" + json.get("Valeur") + "," + json.get("Jour") + "," + json.get("Index") + ","+json.get("Heure")+");";
                    System.out.println(sql);
                    state.executeUpdate(sql);
                }

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
