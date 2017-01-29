package serveur.Force;

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
public class UptateDataScreenForce implements Runnable {
    private Statement state;
    private File f;

    private Connection connection;
    private String page;
    private int numBoite;

    private boolean isRun=true;

    private boolean pageOuverte;


    public UptateDataScreenForce(Connection c, int numBoite,boolean pageOuverte) {
        ResourceBundle rb = ResourceBundle.getBundle("serveur.domaine.properties.config");


        String nff1 = rb.getString("nff1");
        f = new File(nff1);

        page = rb.getString("pageForce1");


        this.numBoite = numBoite;

        this.pageOuverte=pageOuverte;


        ;//fichier data pour mapage3
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


                ResultSet rs = state.executeQuery("SELECT Valeur ,Mois,Jour,Heure,Minute,Seconde FROM BoiteForce"+numBoite+";");
                // ResultSet rs = state.executeQuery("SELECT Valeur ,Heure,Minute,Seconde FROM BoiteTemp1;");

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