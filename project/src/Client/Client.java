package Client;

import com.phidgets.PhidgetException;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by François on 29-10-16.
 */
public class Client {

    public static void main(String[] zero) throws IOException {

        ServerSocket socketserver;
        Socket socket = new Socket();

        PrintWriter out = null;
        BufferedReader in = null;

        int i[] = {0};



        try{
            socket = new Socket(InetAddress.getLocalHost(),2000);

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());



            Thread envoie = new Thread(new Envoie(out,i));
            Thread reception =new Thread(new Reception(in,i,out));
            envoie.start();
            reception.start();

            envoie.wait();
            reception.wait();

            socket.close();



        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
