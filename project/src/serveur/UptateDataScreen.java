package serveur;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * Created by User on 15-11-16.
 */
public class UptateDataScreen implements Runnable {
    private Statement state;
    private File f;
    private Date date;



    public UptateDataScreen (Statement state){
        this.state =state;
        f = new File("C:\\ProjetIndividuel\\project\\src\\serveur\\mydate.tsv") ;
    }
    @Override
    public void run() {





        try {
            Process proc = Runtime.getRuntime().exec("C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe  C:\\ProjetIndividuel\\project\\src\\serveur\\mapage.html");
            // Attention seul firefox permet l'utilisation du fichier data.tsv
            while (true) {

                ResultSet rs = state.executeQuery("SELECT * FROM Box1;");

                FileWriter fw = new FileWriter(f);
                int d;
                int valeur;
                fw.write("date\tvaleur\n");




                while (rs.next()) {
                    d = rs.getInt("Date");
                    valeur = rs.getInt("Valeur");
                    date = new Date(d);


                    fw.write(date.getHours() + "\t" + valeur + "\r\n");
                }



                fw.close();
                Thread.sleep(500);
            }




        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
