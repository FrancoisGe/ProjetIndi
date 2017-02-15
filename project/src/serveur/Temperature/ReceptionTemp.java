package serveur.Temperature;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by François on 02-12-16.
 */
public class ReceptionTemp implements Runnable{
    private String message;//Contient le dernier message recu
    private BufferedReader in;
    private JsonParser parser;

    private int[] i;
    private Statement state;
    private String sql;
    private int numBoite;
    private boolean t;

    private boolean isRun=true;
    private boolean verbose;

    public ReceptionTemp(BufferedReader in, int[] i, Statement state, int numBoite,boolean verbose){
        parser = new JsonParser();
        this.in = in;
        this.i = i;
        this.state=state;
        this.numBoite=numBoite;
        this.t=true;
        this.verbose =verbose;

    }



    public void run(){
        boolean pasErreur=true;

        while(isRun){
            try {
                message = in.readLine();
                if ((message.equals("erreur "+numBoite))&&t){
                    if (pasErreur) {
                        System.out.println("Il y a des données perdues pour la boiteTemp "+numBoite);
                        File f = new File("C:\\ProjetIndividuel\\project\\src\\serveur\\erreur.html");
                        FileWriter fw = new FileWriter(f);
                        fw.write("Il y a des données perdues pour la boiteTemp "+numBoite);
                        fw.close();
                        Process proc = Runtime.getRuntime().exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe C:\\ProjetIndividuel\\project\\src\\serveur\\erreur.html");
                        pasErreur = false;
                        Thread.sleep(10000);
                    }
                }
                else {
                    pasErreur=true;

                    JsonObject json = parser.parse(message).getAsJsonObject();

                    i[0] = i[0] + 1;//On incrémente le compteur car on a un packet de données en plus

                    sql = "INSERT INTO BoiteTemp"+numBoite+ "(Valeur,Mois,Jour,Heure,Minute,Seconde) " +
                            "VALUES (" + json.get("Valeur") + "," + json.get("Mois") +"," + json.get("Jour") + "," + json.get("Heure") + ","+ json.get("Minute") + ","+json.get("Seconde")+");";
                    if (verbose){ System.out.println(sql);}
                    state.executeUpdate(sql);
                }

            } catch (IOException e) {
                isRun=false;
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void stopRun(){
        isRun=false;
    }

}
