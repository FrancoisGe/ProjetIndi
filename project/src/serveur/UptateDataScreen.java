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
    private File f2;
    private Date date;



    public UptateDataScreen (Statement state){
        this.state =state;
        f = new File("C:\\ProjetIndividuel\\project\\src\\serveur\\mydate.tsv") ;
        f2 = new File("C:\\wamp64\\www\\data.csv") ;
    }
    @Override
    public void run() {





        try {
            Process proc = Runtime.getRuntime().exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe http://localhost/mapage2");
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

                ResultSet rs2 = state.executeQuery("SELECT Ind,COUNT(Valeur) AS nb  FROM Box2 GROUP BY Ind;");

                FileWriter fw2 = new FileWriter(f2);
                fw2.write("age,population\n");
                while (rs2.next()) {
                    fw2.write(rs2.getInt("Ind")+","+rs2.getInt("nb")+"\n");
                }
                System.out.println("je viens d ecrire");
                fw2.close();

                Thread.sleep(10000);





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
