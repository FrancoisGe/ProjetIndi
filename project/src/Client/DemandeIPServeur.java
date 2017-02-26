package Client;

import java.io.IOException;
import java.net.*;

/**
 * Created by François on 23-01-17.
 */
public class DemandeIPServeur implements Runnable{

    private boolean isRun = true;
    private DatagramSocket clientSocket;
    private String ipServeurRecu;
    private boolean ipRecu = false;

    /**
     * Thread qui va envoyer une demande en broad cast toutes les secondes jusqu'à recevoir une réponse du serveur avec son ip (Ip du serveur).
     */

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
                //envoie d'un Datagram en broadcast, quand le serveur recevra ce message le serveur envoie son IP comme réponse
                //On envoie toutes les secondes jusqu'à obtenir une réponse.
                InetAddress IPAddress = InetAddress.getByName("255.255.255.255");
                byte[] sendData = new byte[28];
                String sentence = "Hello";
                sendData = sentence.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
                clientSocket.send(sendPacket);

                System.out.print("j ai envoyé ");
                Thread.sleep(1000);
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
    /**
     * Arrête le Thread si il est actif
     */
    public void stopRun(){
        isRun=false;
    }

    /**
     * Cette fonction recoit un datagrame qui doit contenir l'ip du serveur et l'enregiste.
     *
     * @throws IOException
     */
    public void receptionIP() throws IOException {
        //POST : renvoie l'ip recue dans le datagramme du serveur

        System.out.println("Attends reponse du serveur (IP)");
        byte[] receiveData = new byte[28];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        this.ipServeurRecu = new String(receivePacket.getData());
        this.ipRecu=true;
        clientSocket.close();

    }

    /**
     *
     * @return  la socket créée appartir de l'adresse ip du serveur reçue par Datagramme
     * @throws IOException
     */

    public Socket socketIpServeur() throws IOException {
        //Post : Arrête l'envoie de message en broadcast quand on a recu l'Ip du serveur dans un Datagram
        //      et Return la socket créer appartir de l'adress ip du serveur
        if (!this.ipRecu){receptionIP();}
        while (!this.ipRecu){}
        try {

            Socket s= new Socket(this.ipServeurRecu, 2000 );
            s.setSoTimeout(2147483647);//Permet d'éviter un TimeOut pendant l'utilisation du dispositif (24 j fonctionnel)
            this.stopRun();
            return s;

        }catch (IOException e){
            System.out.println("Erreur de connection à l'IP reçue");
            return this.socketIpServeur();
        }

    }
}
