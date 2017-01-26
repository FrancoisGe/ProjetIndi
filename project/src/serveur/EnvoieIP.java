package serveur;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by François on 23-01-17.
 */
public class EnvoieIP implements Runnable {

    private boolean isRun =true;

    @Override
    public void run() {
        try {
            DatagramSocket serverSocket = new DatagramSocket(9876);
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];

            while (isRun){
                //Réception du Datagram pour demander l IP
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                String sentence = new String(receivePacket.getData());
                System.out.println(sentence);


                //Création et envoie du Datagram avec l IP du Serveur
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                sentence = InetAddress.getLocalHost().getHostAddress();
                sendData = sentence.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);



            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public void stopRun(){
        isRun=false;
    }
}
