package serveur.Bouton;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

/**
 * Created by User on 15-11-16.
 */
public class UptateDataScreenButton implements Runnable {
    private Statement state;
    private File f;
    private File f2;
    private Connection connection;
    private String page;
    private  int numBoite;
    private String page2;
    private boolean isRun;

    private boolean pageOuverte;




    public UptateDataScreenButton(Connection c, int numBoite,boolean pageOuverte){
        this.isRun =true;
        ResourceBundle rb = ResourceBundle.getBundle("serveur.domaine.properties.config");
        String nf1 = rb.getString("nf1");
        nf1 = nf1+numBoite+".csv";
        String nf2 = rb.getString("nf2");
        nf2 = nf2+numBoite+".csv";
        f = new File(nf1) ;
        page=rb.getString("page1");
        page2=rb.getString("page2");
        this.numBoite=numBoite;
        this.pageOuverte=pageOuverte;



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
        //Utilisation des noms assignés aux différents boutons
        ResourceBundle rb = ResourceBundle.getBundle("serveur.domaine.properties.configName");
        String[] bouton=new String[8];//tableau qui contient les noms affichés pour les boutons
        for (int i = 0; i <8 ; i++) {
            bouton[i]=rb.getString("b"+i);
        }

        try {
            //ouverture des pages Web si elles ne l'ont pas déjà étée
            if (!pageOuverte) {
                Process proc = Runtime.getRuntime().exec(page);
                Process proc2 = Runtime.getRuntime().exec(page2);
            }



            while (isRun) {
                //Mise à jour des fichiers de données utilisés par les pages webs
                ResultSet rs = state.executeQuery("SELECT Ind ,COUNT(Valeur) AS nb FROM BoiteBouton"+numBoite+" GROUP BY Ind;");

                FileWriter fw = new FileWriter(f);

                fw.write("bouton,click\n");


                while (rs.next()) {
                    fw.write(bouton[rs.getInt("Ind")] + "," + rs.getInt("nb") + "\r\n");
                }

                rs.close();
                fw.close();

                //Mise à jour des données de mapage3
                ResultSet rs3 = state.executeQuery("SELECT  Ind FROM BoiteBouton"+numBoite+" GROUP BY Ind ORDER BY Ind ASC;");
                int tabBouton[]=new int[8];
                int lgTab=0;
                FileWriter fw2 = new FileWriter(f2);
                fw2.write("State");
                while (rs3.next()){
                    tabBouton[lgTab]=rs3.getInt("Ind");
                    fw2.write(","+ bouton[tabBouton[lgTab]]);
                    lgTab++;
                }
                rs3.close();

                int i=0;
                int derniereHeure = -1;
                int heure;
                int id=-1;
                int jour;
                int derniereJour=-1;

                ResultSet rs2 = state.executeQuery("SELECT Ind,Jour,Heure,COUNT(Valeur) AS nb  FROM BoiteBouton"+numBoite+" GROUP BY Ind,Jour,Heure ORDER BY  Jour ASC,Heure ASC;");
                while (rs2.next()) {
                    heure = rs2.getInt("Heure");
                    jour = rs2.getInt("Jour");

                    if ((heure == derniereHeure)&&(jour==derniereJour)) {
                        id = rs2.getInt("Ind");
                        while ((i<=lgTab)&&(id != tabBouton[i])){
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
                        while ((i<lgTab)&&(id != tabBouton[i])){
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


    public static String ConvertirIntJour(int jour){
        //Post : renvoit selon la valeur du jour le jour correspondant en String
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
    public void stopRun(){
        isRun=false;
    }
}
