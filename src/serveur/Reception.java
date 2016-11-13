package serveur;

import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by François on 29-10-16.
 */
public class Reception implements Runnable{
    private String message;
    private BufferedReader in;
    JsonParser parser;

    public Reception(BufferedReader in){
        parser = new JsonParser();
        this.in = in;
    }



    public void run(){

        while(true){
            try {
                message = in.readLine();
                //parser.parse(message).getAsJsonObject();
                System.out.println(message);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
