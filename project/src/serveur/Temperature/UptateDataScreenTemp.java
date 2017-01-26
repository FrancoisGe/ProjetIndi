package serveur.Temperature;


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
public class UptateDataScreenTemp implements Runnable {
    private Statement state;
    private File f;

    private Connection connection;
    private String page;
    private int numBoite;

    private boolean isRun=true;


    public UptateDataScreenTemp(Connection c, int numBoite) {
        ResourceBundle rb = ResourceBundle.getBundle("serveur.domaine.properties.config");

       // String nf1 = "C:\\wamp\\www\\data1.tsv";
        String nf1 = rb.getString("nft1");
        f = new File(nf1);
       page = rb.getString("pageTemp1");


        this.numBoite = numBoite;


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
            Process proc = Runtime.getRuntime().exec(page);
            while (isRun) {


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