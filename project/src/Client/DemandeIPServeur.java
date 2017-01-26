package Client;

import java.io.IOException;
import java.net.*;

/**
 * Created by François on 23-01-17.
 */
public class DemandeIPServeur implements Runnable{

    private boolean isRun = true;
    private DatagramSocket clientSocket;

    public DemandeIPServeur(){

        try {
            this.clientSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        while(isRun){



            try {
                InetAddress IPAddress = InetAddress.getByName("255.255.255.255");
                byte[] sendData = new byte[28];
                String sentence = "Hello";
                sendData = sentence.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
                clientSocket.send(sendPacket);
                System.out.print("j ai envoyé ");


                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
    public void stopRun(){
        isRun=false;
    }

    public Socket socketIpServeur() throws IOException {
        byte[] receiveData = new byte[28];

        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        String ipServeurRecu = new String(receivePacket.getData());

        this.stopRun();
        clientSocket.close();
        try {

            return new Socket(ipServeurRecu, 2000 );

        }catch (IOException e){
           return this.socketIpServeur();
        }

    }
}