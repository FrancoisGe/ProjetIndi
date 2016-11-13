package serveur;

import java.io.PrintWriter;

/**
 * Created by User on 10-11-16.
 */
public class Envoie implements Runnable{

    private Integer i;
    private PrintWriter out;

    public Envoie(PrintWriter out){
        this.i=i;
        this.out=out;

    }
    @Override
    public void run() {
        while (true) {


            out.println(5);
            out.flush();
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
