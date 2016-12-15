package serveur;

import serveur.Bouton.ServeurBouton;
import serveur.Force.ServeurForce;
import serveur.Temperature.ServeurTemp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by François on 29-10-16.
 */
public class ServeurPrincipale {
    public static void main(String[] args){



        Connection connection;



        try{



            Class.forName("org.sqlite.JDBC");
            System.out.println("Driver O.K.");
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\ProjetIndividuel2\\INFOB318-16-17-pds\\project\\BaseDeDonnees.db");
            System.out.println("Opened database successfully");

//Il faut ajouter un thread par boite et ajouter les while pour chaque thread

            int nbBoite[] ={0,0,0};
            //nbBoite[0]=nombre de boites Bouttons actives
            //nbBoite[1]=nombre de boites Temperatures actives
            //nbBoite[2]=nombre de boites Forces actives
            Thread serveur[]=new Thread[10];
        /*   Thread serveur1 = new Thread(new ServeurTemp(1,connection,nbBoite));nbBoite[1]++;
            Thread serveur2 = new Thread(new ServeurTemp(2,connection,nbBoite));nbBoite[1]++;
           serveur1.start();
           serveur2.start();*/
            for (int i = 0; i <10 ; i++) {
                serveur[i] = new Thread(new Serveur(i,connection,nbBoite));nbBoite[1]++;
                serveur[i].start();
            }




            for (int i = 0; i < serveur.length; i++) {
                while (serveur[0].isAlive()){
                    Thread.sleep(50);

                }
            }


            connection.close();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
