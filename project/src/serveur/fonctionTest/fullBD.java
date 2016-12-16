package serveur.fonctionTest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by User on 02-12-16.
 */
public class fullBD {

public static void main(String[] args) {//Ajouter un fichier de configuration
    try {
        Class.forName("org.sqlite.JDBC");
        System.out.println("Driver O.K.");
        Connection conn = DriverManager.getConnection("jdbc:sqlite:BaseDeDonnees.db");
        System.out.println("Opened database successfully");


        Statement state = conn.createStatement();
        int i = 0;
        while (i < 100000) {
            String sql = "INSERT INTO Boite1(Valeur,Jour,Ind,Heure) " +
                    "VALUES (1,1,1,1);";

            state.executeUpdate(sql);
            i++;
            System.out.println(i);
        }
        System.out.print("j ai fini");
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}
