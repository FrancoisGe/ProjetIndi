package serveur.Temperature;


import com.google.gson.JsonObject;
import serveur.Serveur;
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
 * Created by François on 02-12-16.
 */
public class UpdateDataScreenTemp implements Runnable {
    private Statement state;
    private File f;

    private Connection connection;
    private String page;
    private int numBoite;

    private boolean isRun=true;

    private boolean pageOuverte;


    public UpdateDataScreenTemp(Connection c, int numBoite, boolean pageOuverte){
        JsonObject json = null;
        try {
            json = ServeurPrincipale.créerJsonAvecFile("Config.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //nf1 contient l'adress à la quel on doit créer le fichier de données
        String nf1 = json.get("nft1").getAsString();
        f = new File(nf1);

        //page contient le navigateur utilisé + la page qu'il faut ouvrir
        page = json.get("pageTemp1").getAsString();

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
            //Ouvre la page internet sur Chrome si elle n'a pas deja été ouverte
            if(!pageOuverte) {Process proc = Runtime.getRuntime().exec(page);}
            while (isRun) {

                /*Récupère les données dans la BD pour les mettre dans un fichier de data qui sera utiliser par la page web
                    Expemple :
                    date	close
                    18-10-19-29	19.7698
                    18-10-19-29	19.7698
                    28-10-19-29	19.992
                 */

                ResultSet rs = state.executeQuery("SELECT Valeur ,Mois,Jour,Heure,Minute,Seconde FROM BoiteTemp"+numBoite+";");

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

    public static int jourAnnee(int j,int m){
        //Pre: j = jour et m = mois
        //Post : renvoie le jour entre 1 et 366
        if(m==0){return j;}
        if(m==1){return j+31;}
        if (m==2){return 31+29+j;}
        if (m % 2 ==0){return jourAnnee(j,m-1)+30;}
        else{return jourAnnee(j,m-1)+31;}
    }

    public void stopRun(){
        isRun=false;
    }
}