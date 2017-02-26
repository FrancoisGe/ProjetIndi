package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

/**
 * Created by User on 10-11-16.
 */
public class Reception implements Runnable {
    private BufferedReader in;
    private int[] i;
    private boolean isRun;

    /**
     * Ce Thread permet de savoir le nombre de paquets que le serveur à reçu
     *
     * @param in Buffer dans le quel on va recevoir le nombre de paquets reçu par le serveur pendant la dernière seconde
     * @param i tableau contenant une seul valeur à l'indice 0 qui est le nombre de paquets de données envoyées moins le nombre de paquets que le serveur à dit qu'il avait reçu
     */


    public Reception(BufferedReader in,int[] i){
        this.in =in;
        this.i=i;
        isRun=true;

    }

    @Override
    public void run() {
        while ( isRun){
            try {

                String message = in.readLine();
                int m = Integer.parseInt(message);//Recoit le nombre de packet deja recu par le serveur, on va utiliser un objet pour parler entre les 2 thread
                System.out.println("reception : "+ m);
                i[0]=i[0]-m;//Retire du nombre total de packets envoyés, le nombre deja recu par le serveur


                //Si une trop grosse perte de packet on envoie un message d'erreur car cela signifie qu'il y un problème technique et cela permet d'en informer le serveur
                if(i[0]>100){
                    i[0]=0;
                    isRun=false;
                }

            } catch (IOException e) {
                isRun=false;
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
}
