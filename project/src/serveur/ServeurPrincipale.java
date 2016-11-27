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


        Statement statement;
        Connection conn;




        try{
           /* socketserver = new ServerSocket(2000);
            socket= socketserver.accept();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());*/


            Class.forName("org.sqlite.JDBC");
            System.out.println("Driver O.K.");
            conn = DriverManager.getConnection("jdbc:sqlite:C:\\ProjetIndividuel\\project\\BaseDeDonnees.db");
            System.out.println("Opened database successfully");

/*
            Statement statement = conn.createStatement();


            Thread reception = new Thread(new Reception(in,i,statement));
            Thread envoie = new Thread(new Envoie(out,i));
            Thread screen = new Thread(new UptateDataScreen(statement));
            reception.start();
            envoie.start();
            screen.start();


            reception.wait();
            envoie.wait();
            screen.wait();

            socket.close();*/

            statement = conn.createStatement();
            Thread serveur1 = new Thread(new Serveur(1,statement));
           // Thread serveur2 = new Thread(new Serveur(2,statement));
            serveur1.start();
         //   serveur2.start();
            Thread screen = new Thread(new UptateDataScreen(statement));
            screen.start();
            serveur1.wait();
          //  serveur2.wait();
            screen.wait();


            statement.close();
            conn.close();



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
