package Client;

import java.io.BufferedReader;
import java.io.IOException;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

/**
 * Created by User on 10-11-16.
 */
public class Reception implements Runnable {
    private BufferedReader in;
    private Integer i;
    public Reception(BufferedReader in,Integer i){
        this.in =in;
        this.i=i;
    }

    @Override
    public void run() {
        while (true){
            try {
                System.out.println("je suis la");
                String message = in.readLine();
                int m = Integer.parseInt(message);//Recoit le nombre de packet deja recu par le serveur, on va utiliser un objet pour parler entre les 2 thread
                i=i-m;
                System.out.println("reception :"+i);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
