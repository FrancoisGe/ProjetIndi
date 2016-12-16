package serveur.fonctionTest;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by User on 02-12-16.
 */
public class afficherIP {
    public static void main(String[] args) {

        InetAddress LocaleAdresse ;
        InetAddress ServeurAdresse;

        try {

            LocaleAdresse = InetAddress.getLocalHost();
            System.out.println("L'adresse locale est : "+LocaleAdresse );


            System.in.read();

        } catch (UnknownHostException e) {

            e.printStackTrace();
        } catch (IOException e) {


        }
    }

}
