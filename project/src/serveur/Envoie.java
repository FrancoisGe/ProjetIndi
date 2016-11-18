package serveur;

import java.io.PrintWriter;

/**
 * Created by User on 10-11-16.
 */
public class Envoie implements Runnable{

    private int[] i;
    private PrintWriter out;
    private int send ;

    public Envoie(PrintWriter out,int[] i){
        this.i=i;
        this.out=out;

    }
    @Override
    public void run() {
        while (true) {



            try {
                System.out.println("je viens d envoyer un "+ i[0]);
                send=i[0];
                out.println(i[0]);
                i[0]=i[0]-send;
                out.flush();

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
