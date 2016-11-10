package Client.Listener;

import com.phidgets.event.AttachEvent;
import com.phidgets.event.AttachListener;

import java.io.*;
import java.net.Socket;

/**
 * Created by François on 05-11-16.
 */
public class AttachListenerCo implements AttachListener{
    private PrintWriter out ;
    private BufferedReader in ;

    public AttachListenerCo(Socket socket){
        try {
            System.out.print("je suis la");
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public BufferedReader getBufferReader(){

        return this.in;
    }

    public PrintWriter getPrintWriter(){
        return this.out;
    }


    public void attached(AttachEvent attachEvent) {
        System.out.print("kikiki");
        System.out.print(in);
    }
}
