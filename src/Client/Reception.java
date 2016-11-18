package Client;

import java.io.BufferedReader;
import java.io.IOException;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

/**
 * Created by User on 10-11-16.
 */
public class Reception implements Runnable {
    private BufferedReader in;
    private int[] i;
    public Reception(BufferedReader in,int[] i){
        this.in =in;
        this.i=i;
    }

    @Override
    public void run() {
        while (true){
            try {

                String message = in.readLine();
                int m = Integer.parseInt(message);//Recoit le nombre de packet deja recu par le serveur, on va utiliser un objet pour parler entre les 2 thread
                System.out.println("reception : "+ m);
                i[0]=i[0]-m;


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
