package serveur.Force;

import com.google.gson.JsonObject;
import serveur.ServeurPrincipale;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * Created by François on 11-12-16.
 */
public class UpdateDataScreenForce implements Runnable {
    private Statement state;
    private File f;

    private Connection connection;
    private String page;
    private int numBoite;

    private boolean isRun=true;

    private boolean pageOuverte;


    public UpdateDataScreenForce(Connection c, int numBoite, boolean pageOuverte) {
        JsonObject json = null;
        try {
            json = ServeurPrincipale.créerJsonAvecFile("Config.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }


        String nff1 = json.get("nff1").getAsString();
        f = new File(nff1);


        page = json.get("pageForce1").getAsString();

        this.numBoite = numBoite;
        this.pageOuverte=pageOuverte;



        this.connection = c;
        try {
            state = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void run() {


        try {

            if(!pageOuverte) {
                Process proc = Runtime.getRuntime().exec(page);
            }
            while (isRun) {

                /*Récupération des données et mise à jour du fichier de data pour mapageForce
                    Exemple
                    date	close
                    11-5-20-29	0.0
                    12-5-20-29	19.0
                    13-5-20-29	8.0
                 */

                ResultSet rs = state.executeQuery("SELECT Valeur ,Mois,Jour,Heure,Minute,Seconde FROM BoiteForce"+numBoite+";");

                FileWriter fw = new FileWriter(f);

                fw.write("date\tclose\n");


                while (rs.next()) {

                    int jour =jourAnnee(rs.getInt("Jour"),rs.getInt("Mois"));
                    fw.write( rs.getInt("Seconde") + "-" + rs.getInt("Minute") + "-" +rs.getInt("Heure") +"-"+jour+ "\t"+rs.getFloat("Valeur")+ "\r\n");
                }


                rs.close();
                fw.close();

                Thread.sleep(1000);


            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int jourAnnee(int j,int m) {

        //Post : renvoie le jour de l'année (1 à 366) selon le jour j du mois m
        if (m == 0) {
            return j;
        }
        if (m == 1) {
            return j + 31;
        }
        if (m == 2) {
            return 31 + 29 + j;
        }
        if (m % 2 == 0) {
            return jourAnnee(j, m - 1) + 30;
        } else {
            return jourAnnee(j, m - 1) + 31;
        }


    }
    public void stopRun(){
        isRun=false;
    }

}