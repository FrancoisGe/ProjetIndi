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
            //Thread qui va gérer la communication de l IP aux boites
            EnvoieIP envIP=new EnvoieIP();
            Thread envoieIP = new Thread(envIP);
            envoieIP.start();

            //Connection au fichier de propriété pour avoir le nom de la bd
            ResourceBundle rb = ResourceBundle.getBundle("serveur.domaine.properties.config");
            String bd = rb.getString("bd");

            Class.forName("org.sqlite.JDBC");
            System.out.println("Driver O.K.");

            //Connection à la bd
            connection = DriverManager.getConnection("jdbc:sqlite:"+bd);
            System.out.println("Opened database successfully");


            Thread serveur[]=new Thread[1000];

            /*Tableau content la liste de toutes les boites qui se sont déjà connectée
             * la valeur pageWebActive[0][0] contient le nombre de pair ajouté dans le tableau
             *Ex:
             * On connecte une première boite : boiteTemp2 sera pageWebActive[1][0]=1 et pageWebActive[1][0] = 2
             * Première valeur le type et la deuxième le num de la boite.
             */
            int[][] pageWebActive= new int[100][2];
            pageWebActive[0][0]=0;

            for (int i = 0; i <1000 ; i++) {
                try {

                    //Création de la socket serveur pour communiquer avec une boite
                    ServerSocket socketserveur = new ServerSocket(2000);
                    Socket socket = socketserveur.accept();
                    socketserveur.close();
                    serveur[i] = new Thread(new Serveur(socket,connection,pageWebActive));//Thread qui va gérer l interaction entre le serveur et la boite
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
