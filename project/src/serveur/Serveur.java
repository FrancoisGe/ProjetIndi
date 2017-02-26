package serveur;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import serveur.Bouton.ServeurBouton;
import serveur.Force.ServeurForce;
import serveur.Temperature.ServeurTemp;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Connection;

/**
 * Created by User on 15-12-16.
 */
public class Serveur implements Runnable {

    private Socket socket = new Socket();

    private PrintWriter out = null;
    private BufferedReader in = null;

    private Connection connection;

    private int[][] pageWebActive;
    private boolean verbose;


    /**
     *
     * @param socket : socket qui est utilisé pour la communication TCP entre le serveur et la boite connectée
     * @param c : Connection à la base de données
     * @param pageWebActive :   Tableau content la liste de toutes les boites qui se sont déjà connectées
     *                          la valeur pageWebActive[0][0] contient le nombre de pair ajouté dans le tableau
     *                          Ex:
     *                          On connecte une première boite : boiteTemp2 sera pageWebActive[1][0]=1 et pageWebActive[1][0] = 2
     *                          Première valeur le type et la deuxième le num de la boite.
     *
     *                          Valeur Type boite :
     *                          Boite Bouton=0;
     *                          Boite Température=1;
     *                          Boite Force=2;
     * @param verbose : Permet de savoir si le mode verbose est actif
     *                  True = actif
     *                  False = desactivé
     */
    public Serveur(Socket socket, Connection c,int[][] pageWebActive,boolean verbose) {
        this.pageWebActive = pageWebActive;
        this.socket=socket;
        this.connection = c;
        this.verbose=verbose;
        try {
            socket.setSoTimeout(2147483647);//Permet d'éviter un TimeOut pendant l'utilisation du dispositif (24 j fonctionnel)
        } catch (SocketException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void run() {
        try {

            if(verbose) {System.out.println(socket);}

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());

            String  message = in.readLine();
            if(verbose) {System.out.println(message);}

            //le premier message envoyé après une connection entre une boite et le serveur est le numérau de la boite et le numéraux de son type
            //Boite Bouton=0;
            //Boite Température=1;
            //Boite Force=2;
            JsonParser parser =new JsonParser();
            JsonObject json = parser.parse(message).getAsJsonObject();
            JsonElement typeBoite =json.get("numType");
            JsonElement numBoite =json.get("numBoite");


            boolean  pageOuverte=pageDejaOuverte(typeBoite.getAsInt(),numBoite.getAsInt());
            try{
                //Création du Thread selon le type de boite
                switch (typeBoite.getAsInt()){
                    case 0: Thread t0=new Thread(new ServeurBouton(numBoite.getAsInt(),connection,in,out,pageOuverte,verbose));
                            t0.start();
                            while(t0.isAlive()){Thread.sleep(50);}
                            socket.close();
                            break;
                    case 1: Thread t1=new Thread(new ServeurTemp(numBoite.getAsInt(),connection,in,out,pageOuverte,verbose));
                            t1.start();
                            while(t1.isAlive()){Thread.sleep(50);}
                            socket.close();
                            break;
                    case 2: Thread t2=new Thread(new ServeurForce(numBoite.getAsInt(),connection,in,out,pageOuverte,verbose));
                            t2.start();
                            while(t2.isAlive()){Thread.sleep(50);}
                            socket.close();
                            break;
                    default:break;

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param type : type de boite à vérifier
     * @param num  : numéro de la boite à vérifier
     * @return true si la boite a déjà été ajoutée à pageWebActive
     *         false si la boite n'a pas deja été ajoutée à pageWebActive et on l'ajoute
     */
    private boolean pageDejaOuverte(int type,int num){

        for (int i = 1; i <pageWebActive.length ; i++) {
            if ((pageWebActive[i][0]==type)&&(pageWebActive[i][1]==num)){
                return true;
                }
            }

        pageWebActive[0][0]= pageWebActive[0][0]+1;
        pageWebActive[pageWebActive[0][0]][0]=type;
        pageWebActive[pageWebActive[0][0]][1]=num;
        return false;

    }


}
