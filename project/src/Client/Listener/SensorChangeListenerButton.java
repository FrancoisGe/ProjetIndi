package Client.Listener;

import com.google.gson.JsonObject;
import com.phidgets.event.SensorChangeEvent;
import com.phidgets.event.SensorChangeListener;

import java.io.PrintWriter;
import java.util.Date;

/**
 * Created by François on 05-11-16.
 */
public class SensorChangeListenerButton implements SensorChangeListener {

    private PrintWriter out ;

    private int[] i;

    /**
     *
     * @param out Buffer dans le quel on met les messages à envoyer (le port du boutton + date + valeur)
     * @param i tableau contenant une seul valeur à l'indice 0 qui est le nombre de paquets de données envoyées moins le nombre de paquets que le serveur à dit qu'il avait reçu
     */

    public SensorChangeListenerButton(PrintWriter out, int[] i){

        this.out = out;

        this.i=i;
    }
    @Override
    public void sensorChanged(SensorChangeEvent sensorChangeEvent) {


        //Lors de l'activation d'un bouton :

        int valeur = sensorChangeEvent.getValue();

        Date date = new Date();
        long heure =   date.getHours();
        int jour = date.getDay();


        // On envoie 1 packet de données(date+num du bouton) au serveur si la valeur du phidget est >=990 car sinon de fausse données son envoyées (lorsqu'on appuis sur un bouton il y a au moins 3 appels au listener donc pour eviter l envoie de message initile on envoie que quand la valeur est sup a 990)

        if (valeur >=990) {
            int index = sensorChangeEvent.getIndex();
            System.out.print("valeur event:" + index);

            JsonObject json = new JsonObject();
            json.addProperty("Heure", heure);
            json.addProperty("Valeur", valeur);
            json.addProperty("Index", index);
            json.addProperty("Jour",jour);

            System.out.println(json);

            i[0] = i[0] + 1;
            System.out.println("envoie :" + i[0]);

            out.println(json);
            out.flush();


       }
    }
}
