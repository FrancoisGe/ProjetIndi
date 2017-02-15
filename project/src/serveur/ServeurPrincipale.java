package serveur;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
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
        boolean verbose=false;

        if ((args.length>0) && (args[0].equals("-v"))){
            verbose =true;
            System.out.println("Le mode verbose est activé");
        }

        try{
            //Thread qui va gérer la communication de l IP aux boites
            EnvoieIP envIP=new EnvoieIP(verbose);
            Thread envoieIP = new Thread(envIP);
            envoieIP.start();


            //Il faut que le fichier Config.txt soit configuré et soit dans le même dossier que le jar
            //On récupère les données du fichier de config sous format JSON
            JsonObject json = ServeurPrincipale.créerJsonAvecFile("Config.txt");
            //Récupère le nom de la bd
            String bd = json.get("bd").getAsString();

            Class.forName("org.sqlite.JDBC");
            if (verbose){ System.out.println("Driver O.K.");}


            //Connection à la bd
            connection = DriverManager.getConnection("jdbc:sqlite:"+bd);
            if (verbose){System.out.println("Opened database successfully");}


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
                    serveur[i] = new Thread(new Serveur(socket,connection,pageWebActive,verbose));//Thread qui va gérer l interaction entre le serveur et la boite
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
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static JsonObject créerJsonAvecFile(String nf) throws IOException {

        FileReader fr = new FileReader(nf);
        System.out.println(new File(nf).getAbsolutePath());
        char[] buff =new char[1000];
        fr.read(buff);
        String s =new String(buff);

        int i=0;
        while (buff[i]!='}'){
            i++;
        }
        i++;
        s=s.substring(0,i);
        JsonParser parser =new JsonParser();
        JsonObject json = parser.parse(s).getAsJsonObject();

        return json;
    }

}
