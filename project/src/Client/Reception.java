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
    PrintWriter out;
    boolean isRun;

    public Reception(BufferedReader in,int[] i,PrintWriter out){
        this.in =in;
        this.i=i;
        this.out =out;
        isRun=true;
    }

    @Override
    public void run() {
        while ( isRun){
            try {

                String message = in.readLine();
                int m = Integer.parseInt(message);//Recoit le nombre de packet deja recu par le serveur, on va utiliser un objet pour parler entre les 2 thread
                System.out.println("reception : "+ m);
                i[0]=i[0]-m;//Retire du nombre total de packets envoyés, lenombre deja recu par le serveur

                //Si une trop grosse perte de packet on envoie un message d'erreur
                if(i[0]>1000){
                    System.out.println("il y a un problème");
                    out.println("erreur 1");
                    out.flush();
                    i[0]=100;
                }

            } catch (IOException e) {
                isRun=false;

                e.printStackTrace();

            }


        }
        System.out.println("reception est fini");



    }
    public void stopRun(){
        isRun=false;
    }
}
