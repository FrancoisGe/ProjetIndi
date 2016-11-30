package serveur;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.ResourceBundle;

/**
 * Created by User on 15-11-16.
 */
public class UptateDataScreen implements Runnable {
    private Statement state;
    private File f;
    private File f2;
    private Connection connection;




    public UptateDataScreen (Connection c,int numBoite){
        this.state =state;
        ResourceBundle rb = ResourceBundle.getBundle("serveur.domaine.properties.config");
        //String nf1 = rb.getString("nf1");
        String nf2 = rb.getString("nf2");
       // f = new File(nf1) ;



        f2 = new File(nf2) ;//fichier data pour mapage3
        this.connection=c;
        try {
            state= connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    @Override
    public void run() {





        try {
            Process proc = Runtime.getRuntime().exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe http://localhost/mapage3.html");

            while (true) {


              /*  ResultSet rs = state.executeQuery("SELECT * FROM Box1;");

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


                rs.close();
                fw.close();*/

                //Structure données mapage3
                ResultSet rs3 = state.executeQuery("SELECT  Ind FROM Boite1 GROUP BY Ind ORDER BY Ind ASC;");
                int tabBoutton[]=new int[8];
                int lgTab=0;
                FileWriter fw2 = new FileWriter(f2);
                fw2.write("State");
                while (rs3.next()){
                    tabBoutton[lgTab]=rs3.getInt("Ind");
                    fw2.write(",bouton "+tabBoutton[lgTab]);
                    lgTab++;
                }


                rs3.close();

                int i=0;
                int derniereHeure = -1;
                int heure;
                int id=-1;
                int jour;
                int derniereJour=-1;

                ResultSet rs2 = state.executeQuery("SELECT Ind,Jour,Heure,COUNT(Valeur) AS nb  FROM Boite1 GROUP BY Ind,Jour,Heure ORDER BY  Jour ASC,Heure ASC;");
                while (rs2.next()) {
                    heure = rs2.getInt("Heure");
                    jour = rs2.getInt("Jour");

                    if ((heure == derniereHeure)&&(jour==derniereJour)) {
                        id = rs2.getInt("Ind");
                        while ((i<=lgTab)&&(id !=tabBoutton[i])){
                            fw2.write(",0");
                            i++;
                        }

                        fw2.write("," + rs2.getInt("nb"));
                        i++;



                    }
                    else{
                        int tabOccurence[]={0,0,0,0,0,0,0,0};//max 8 ports dans le phidget

                        fw2.write("\n");
                        i=0;
                        fw2.write(ConvertirIntJour(jour)+" : "+heure + "h");

                        id = rs2.getInt("Ind");
                        while ((i<lgTab)&&(id !=tabBoutton[i])){
                            fw2.write(",0");
                            i++;
                        }

                        fw2.write("," + rs2.getInt("nb"));
                        i++;
                    }

                    derniereHeure=heure;
                    derniereJour=jour;

                }
                System.out.println("je viens d ecrire");
                fw2.close();
                rs2.close();

                //Structure données mapage3 fin





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


    public String ConvertirIntJour(int jour){
        switch (jour) {
            case 1:
                return "Lundi";
            case 2:
                return "Mardi";
            case 3:
                return "Mercredi";
            case 4:
                return "Jeudi";
            case 5:
                return "Vendredi";
            case 6:
                return "Samedi";
            default : return "Dimanche";

        }
    }
}
