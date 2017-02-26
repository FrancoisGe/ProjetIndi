package serveur.Force;

import com.google.gson.JsonObject;
import serveur.ServeurPrincipal;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    /**
     * Quand il est actif, ce Thread gère l'ouverture de la pages Web renseignée dans le fichier de Config(pageForce1), la mise à jour des données pour la page Web(nff1 dans le fichier de Config).
     *
     * @param c : Connection à la base de données
     * @param numBoite : numéro de la boite avec laquel on communique et à encoder dans la BD
     * @param pageOuverte : True si la page de la boite connectée a déjà été ouverte sur le navigateur
     *                      False si pas encore ouverte sur le navigateur
     */

    public UpdateDataScreenForce(Connection c, int numBoite, boolean pageOuverte) {


        JsonObject  json = ServeurPrincipal.creerJsonAvecFile("Config.txt");

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
            System.out.println ("\n\nLe chemin d'accès du fichier : "+e.fillInStackTrace().getMessage());
            System.out.println("Veillez vérifier si vous n'avez commis une erreur dans le fichier de Config.txt au niveau du chemin d'accès\n\n");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param j jour dans le mois
     * @param m mois (janvier =0)
     * @return  le jour de l'année (1 à 366) selon le jour j du mois m
     */

    public static int jourAnnee(int j,int m) {


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

    /**
     * Arrête le Thread si il est actif
     */
    public void stopRun(){
        isRun=false;
    }

}