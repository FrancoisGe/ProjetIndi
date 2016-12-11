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
    JsonParser parser;

    int[] i;
    Statement state;
    String sql;
    int numBoite;
    boolean t;

    public ReceptionTemp(BufferedReader in, int[] i, Statement state, int numBoite){
        parser = new JsonParser();
        this.in = in;
        this.i = i;
        this.state=state;
        this.numBoite=numBoite;
        this.t=true;
    }



    public void run(){

        while(true && t){
            try {
                message = in.readLine();
                if (message.equals("erreur "+numBoite)){
                    System.out.println("il y a un problè=e !!!!!!!!!!!!!!!");
                    File f = new File("C:\\ProjetIndividuel\\project\\src\\serveur\\erreur.html") ;
                    FileWriter fw = new FileWriter(f);
                    fw.write("Il y a un problème a la boite "+numBoite);
                    fw.close();
                    Process proc = Runtime.getRuntime().exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe C:\\ProjetIndividuel\\project\\src\\serveur\\erreur.html");

                }
                else {
                    JsonObject json = parser.parse(message).getAsJsonObject();


                    System.out.println(message);


                    i[0] = i[0] + 1;
                    System.out.println(i[0]);// !!!!!!!


                    sql = "INSERT INTO BoiteTemp"+numBoite+ "(Valeur,Mois,Jour,Heure,Minute,Seconde) " +
                            "VALUES (" + json.get("Valeur") + "," + json.get("Mois") +"," + json.get("Jour") + "," + json.get("Heure") + ","+ json.get("Minute") + ","+json.get("Seconde")+");";
                    System.out.println(sql);
                    state.executeUpdate(sql);
                }

            } catch (IOException e) {
                t=false;
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
