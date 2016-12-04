package serveur.Bouton;

import serveur.Envoie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by François on 27-11-16.
 */
public class ServeurBouton implements Runnable{

    ServerSocket socketserver;
    Socket socket = new Socket();

    PrintWriter out = null;
    BufferedReader in = null;

    int numBoite;

    int[] checkReception = {0};//Est utilisé pour vérifie le nombre de message envoyé au serveur moins le nombres de message que le serveur a recu

    Connection connection;
    Statement statement;


    public ServeurBouton(int numBoite, Connection c){
        this.numBoite=numBoite;
        try {
            socketserver = new ServerSocket(2000+numBoite);
            socket = socketserver.accept();

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());


            this.connection= c;
            this.statement = connection.createStatement();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {

        Thread reception = new Thread(new ReceptionBouton(in,checkReception,statement,numBoite));
        Thread envoie = new Thread(new Envoie(out,checkReception));
        Thread screen = new Thread(new UptateDataScreenButton(connection,numBoite));
        screen.start();

        reception.start();
        envoie.start();


        try {
            reception.wait();
            envoie.wait();
            screen.wait();



            socket.close();



        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
