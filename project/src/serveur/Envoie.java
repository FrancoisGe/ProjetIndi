package serveur;

import java.io.PrintWriter;

/**
 * Created by User on 10-11-16.
 */
public class Envoie implements Runnable{

    private int[] i;
    private PrintWriter out;
    private int send ;
    private boolean isRun=true;
    private boolean verbose;
    private String nom;

    public Envoie(PrintWriter out,int[] i,boolean verbose,String nom){
        this.i=i;
        this.out=out;
        this.verbose=verbose;
        this.nom=nom;


    }
    @Override
    public void run() {
        while (isRun) {



            try {
                //Envoie le nombre de packet recu depuis 1 sec
                if (verbose){ System.out.println("j'envoie un "+ i[0]+" à "+nom);}
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
    public void stopRun(){
        isRun=false;
    }


}
