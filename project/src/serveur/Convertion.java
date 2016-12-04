package serveur;

/**
 * Created by François on 03-12-16.
 */
public class Convertion {
    public String ConvertirIntJour(int jour){
        switch (jour) {
            case 1:
                return "Lundi";
            case 2:
                return "Mardi";
            case 3:
                return "Mercredi";
            case 4:
                return "Jeudi";
            case 5:
                return "Vendredi";
            case 6:
                return "Samedi";
            default : return "Dimanche";

        }
    }
}
