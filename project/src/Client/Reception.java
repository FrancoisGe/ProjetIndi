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

    public Reception(BufferedReader in,int[] i,PrintWriter out){
        this.in =in;
        this.i=i;
        this.out =out;
    }

    @Override
    public void run() {
        while (true){
            try {

                String message = in.readLine();
                int m = Integer.parseInt(message);//Recoit le nombre de packet deja recu par le serveur, on va utiliser un objet pour parler entre les 2 thread
                System.out.println("reception : "+ m);
                i[0]=i[0]-m;


                if(i[0]>10){
                    System.out.println("il y a un problème");
                    out.println("erreur 1");
                    out.flush();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
