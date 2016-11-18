package serveur;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by User on 15-11-16.
 */
public class UptateDataScreen implements Runnable {
    private Statement state;
    private File f;



    public UptateDataScreen (Statement state){
        this.state =state;
        f = new File("C:\\ProjetIndividuel\\src\\serveur\\project\\mydata.txt") ;
    }
    @Override
    public void run() {





        try {

            ResultSet rs = state.executeQuery( "SELECT * FROM Box1;" );

            FileWriter fw =new FileWriter(f);
            int date;
            int value;

            while (rs.next()){
                date =rs.getInt("Date");
                value=rs.getInt("Valeur");

                fw.write("Date : "+ date+"  "+"Valeur : "+value+"\r\n");


            }

            Process proc=Runtime.getRuntime().exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe  C:\\ProjetIndividuel\\src\\serveur\\project\\mapage.html");





        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
