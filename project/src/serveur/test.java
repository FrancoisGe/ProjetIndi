package serveur;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by François on 13-11-16.
 */
public class test {
    public static void main(String[] zero) {
        java.util.Date date = new java.util.Date();
        //date.setTime(1000000);
        System.out.println("voici le test :" + date.getHours());
        System.out.println("voici le test :" + date);
        try {
            Class.forName("org.sqlite.JDBC");
            System.out.println("Driver O.K.");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:C:\\ProjetIndividuel\\project\\BaseDeDonnees.db");
            System.out.println("Opened database successfully");

            String sql = "INSERT INTO box3 (Valeur,Date,Ind) " +
                    "VALUES (" + 1200 + "," + 12 + "," + 14 + ");";
            System.out.println(sql);
            Statement state = conn.createStatement();
            state.executeUpdate(sql);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            Process proc = Runtime.getRuntime().exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe http://localhost/mapage2");
        } catch (IOException e1) {
                e1.printStackTrace();
        }
        //Runtime.getRuntime().ex

    }






}

