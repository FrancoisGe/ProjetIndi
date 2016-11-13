package serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by François on 29-10-16.
 */
public class Serveur {
    public static void main(String[] zero){

        ServerSocket socketserver;
        Socket socket = new Socket();

        PrintWriter out = null;
        BufferedReader in = null;




        try{
            socketserver = new ServerSocket(2000);
            socket= socketserver.accept();
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());



            Thread t = new Thread(new Reception(in));
            Thread t1 = new Thread(new Envoie(out));
            t.start();
            t1.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
