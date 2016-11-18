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
public class Serveur {
    public static void main(String[] zero){

        ServerSocket socketserver;
        Socket socket = new Socket();

        PrintWriter out = null;
        BufferedReader in = null;

        int[] i = {0};

        Connection conn;




        try{
            socketserver = new ServerSocket(2000);
            socket= socketserver.accept();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());


            Class.forName("org.sqlite.JDBC");
            System.out.println("Driver O.K.");
            conn = DriverManager.getConnection("jdbc:sqlite:BaseDeDonnees.db");
            System.out.println("Opened database successfully");


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

            socket.close();
            statement.close();
            conn.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }catch ( Exception e ) {
        }

    }
}
