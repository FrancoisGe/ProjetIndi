package serveur;

import serveur.Bouton.ServeurBouton;
import serveur.Force.ServeurForce;
import serveur.Temperature.ServeurTemp;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by François on 29-10-16.
 */
public class ServeurPrincipale {
    public static void main(String[] args){



        Connection connection;



        try{
            ResourceBundle rb = ResourceBundle.getBundle("serveur.domaine.properties.config");


            String bd = rb.getString("bd");




            Class.forName("org.sqlite.JDBC");
            System.out.println("Driver O.K.");

            connection = DriverManager.getConnection("jdbc:sqlite:"+bd);
            System.out.println("Opened database successfully");




            Thread serveur[]=new Thread[100];

            for (int i = 0; i <100 ; i++) {
                serveur[i] = new Thread(new Serveur(i,connection));
                serveur[i].start();
            }





            for (int i = 0; i < serveur.length; i++) {
                while (serveur[i].isAlive()){
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
