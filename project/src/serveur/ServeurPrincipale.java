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
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\ProjetIndividuel\\project\\BaseDeDonnees.db");
            System.out.println("Opened database successfully");

//Il faut ajouter un thread par boite et ajouter les while pour chaque thread

           // Thread serveur1 = new Thread(new ServeurTemp(1,connection));
            Thread serveur2 = new Thread(new ServeurForce(2,connection));
           // serveur1.start();
           serveur2.start();

         //   serveur1.wait();

         //   screen.wait();
            while (serveur2.isAlive()){

            }



            connection.close();



        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
