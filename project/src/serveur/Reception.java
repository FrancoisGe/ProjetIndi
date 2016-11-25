package serveur;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by François on 29-10-16.
 */
public class Reception implements Runnable{
    private String message;//Contient le dernier message recu
    private BufferedReader in;
    JsonParser parser;

    int[] i;
    Statement state;
    String sql;

    public Reception(BufferedReader in, int[] i,Statement state){
        parser = new JsonParser();
        this.in = in;
        this.i = i;
        this.state=state;
    }



    public void run(){

        while(true){
            try {
                message = in.readLine();
                JsonObject json = parser.parse(message).getAsJsonObject();


                System.out.println(message);


                i[0]=i[0]+1;
                System.out.println(i[0]);


                 sql = "INSERT INTO Box2 (Valeur,Date,Ind) " +
                       "VALUES ("+json.get("Valeur")+","+ json.get("Heure")+","+json.get("Index")+");";
                System.out.println(sql);
                state.executeUpdate(sql);

            } catch (IOException e) {
                e.printStackTrace();
           } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
