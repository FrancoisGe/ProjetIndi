package serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
            EnvoieIP envIP=new EnvoieIP();
            Thread envoieIP = new Thread(envIP);
            envoieIP.start();

            ResourceBundle rb = ResourceBundle.getBundle("serveur.domaine.properties.config");
            String bd = rb.getString("bd");

            Class.forName("org.sqlite.JDBC");
            System.out.println("Driver O.K.");

            connection = DriverManager.getConnection("jdbc:sqlite:"+bd);
            System.out.println("Opened database successfully");




            Thread serveur[]=new Thread[100];

            for (int i = 0; i <100 ; i++) {
                try {


                    ServerSocket socketserver = new ServerSocket(2000);
                    Socket socket = socketserver.accept();
                    socketserver.close();
                    serveur[i] = new Thread(new Serveur(socket,connection));
                    serveur[i].start();
                } catch (IOException e) {
                    e.printStackTrace();
                }



            }





            for (int i = 0; i < serveur.length; i++) {
                while (serveur[i].isAlive()){
                    Thread.sleep(50);

                }
            }


            connection.close();

            envIP.stopRun();


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

}
