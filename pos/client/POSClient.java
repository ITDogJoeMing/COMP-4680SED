/* Total 20 marks */
package pos.src.com.comp4680.pos.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class POSClient {

    public static void main(String[] args) {
        PrintWriter pw;
        BufferedReader input;
        Socket skt;
        try {
            // 1. Create a Socket and connect to localhost, port 12345. And assign to "skt"
            // object (2 marks)
            skt = new Socket("localhost", 12345);

            // 2. Create the DisplayHandler object and run the thread. Beware you may need
            // to pass some arguments (3 marks)
            DisplayHandler handler = new DisplayHandler(skt);
            Thread DisplayThread = new Thread(handler);
            DisplayThread.start();

            // 3. Get the output stream from socket and bind the stream to "pw" object and
            // auto flush (2 marks)

            pw = new PrintWriter(skt.getOutputStream(), true);

            input = new BufferedReader(new InputStreamReader(System.in));
            String msg = "";
            while ((msg = input.readLine()) != null) {
                // 4. Send the message to server using "pw" object (1 marks)

                pw.println(msg);

                if (msg.equals("q")) {
                    break;
                }
            }
            // 5. Do you need to close something? (3 marks)

            pw.close();
            input.close();
            skt.close();
    

        } catch (Exception e) {
            System.out.println("Connection Closed");
        } // end of try-catch
    }// end of main()

}// end of class POSClient

class DisplayHandler extends Thread/*
                     * 6. Which abstract class should be inherit from so that make the
                     * DisplayHandler class become multi-thread (1 marks)
                     */ {

    private Socket skt = null;

    /*
     * 7. When you pass something to contructor in main(), you also need to add the
     * arguments here and assign to some local variables. (2 marks)
     */
    public DisplayHandler(Socket skt) {
        this.skt = skt;
    }

    /*
     * 8. Replace the value xxx. What method should be override, so that the code
     * inside this method will be executed in a separate thread. Add any annotation
     * needed (2 marks)
     */
    
     @Override
     public void run(){
        BufferedReader br;
        try {
            // 9. Get the input stream from socket and then bind to the "br" object (2 marks)
            br = new BufferedReader(new InputStreamReader(skt.getInputStream()));

            String msg = "";
            /* 10. How can you get the message from "br"/server side continuously? (1 marks) */
            while ((msg = br.readLine()) != null) {
                if (msg.equals("Bye.")) {
                    System.out.println("Connection Closed!");
                    break;
                }
                System.out.println(msg);
            }
            // 11. Close the input stream (1 marks)
            br.close();
        } catch (Exception e) {
            System.out.println("Connection Closed");
        } // end of try-catch
    }// end of run()
}