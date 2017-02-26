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

    /**
     * Quand ce Thread est acitf il envoie dans out le nombre de paquets recu depuis la dernière seconde toutes les secondes.
     *
     * @param out : PrintWriter dans le quel on envoie le nombre de packets de données déjà recu au serveur pour la boite connectée à laquel out est relié
     * @param i : int[] qui contient à l'indice 0, le nombre de packets recu depuis le dernier envoie.
     * @param verbose : Permet de savoir si le mode verbose est actif
     *                  True = actif
     *                  False = desactivé
     * @param nom : Nom de la boite
     *              Exple : "BoiteTemp1"
     */
    public Envoie(PrintWriter out,int[] i,boolean verbose,String nom){
        this.i=i;
        this.out=out;
        this.verbose=verbose;
        this.nom=nom;


    }
    /*
     *Quand ce Thread est acrif, il envoie toutes les 1 sec le nombre de packets de données recu de la boite relié au out depuis le dernier envoie
     */

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
    /**
     * Arrête le Thread si il est actif
     */
    public void stopRun(){
        isRun=false;
    }


}
