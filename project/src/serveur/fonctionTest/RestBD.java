package serveur.fonctionTest;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Created by User on 16-11-16.
 * !!!!!!!!!!!!!!!!!!!!!!!!!!!Classe utilisée pour retirer des données de la BD !!!!!!!!!!!!!!!!!!!!!!
 */
public class RestBD {
    public static void main(String[] args) {//Ajouter un fichier de configuration
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("Driver O.K.");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:BDProjetIndividuel.db");
            System.out.println("Opened database successfully");


            Statement state = conn.createStatement();


            String sql = "DELETE FROM BoiteForce1;";

            state.executeUpdate(sql);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
