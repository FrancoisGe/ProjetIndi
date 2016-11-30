package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Created by François on 29-10-16.
 */
public class ServeurPrincipale {
    public static void main(String[] zero){



        Connection connection;




        try{



            Class.forName("org.sqlite.JDBC");
            System.out.println("Driver O.K.");
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\ProjetIndividuel\\project\\BaseDeDonnees.db");
            System.out.println("Opened database successfully");



            Thread serveur1 = new Thread(new Serveur(1,connection));
           // Thread serveur2 = new Thread(new Serveur(2,statement));
            serveur1.start();
         //   serveur2.start();

            serveur1.wait();
          //  serveur2.wait();
            //screen.wait();
            System.out.println("Problemme !!!!!!!!!!!!!!!!!!!!!!!!!");



            connection.close();



        }/* catch (IOException e) {
            e.printStackTrace();
        }*/ catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } /*catch (InterruptedException e) {
            e.printStackTrace();
        }catch ( Exception e )*/ catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
