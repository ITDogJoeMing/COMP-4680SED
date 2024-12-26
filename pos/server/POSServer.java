/* Total 20 marks*/
package pos.src.com.comp4680.pos.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.regex.Pattern;

public class POSServer {

    public static void main(String[] args) {
        ServerSocket ssk = null;
        AuctionEvents events = new AuctionEvents();
        try {
            /* 1. How to create ServerSocket and assign to "ssk" object? (2 marks) */
            ServerSocket ssk = new ServerSocket(12345);

            while (true) {
                /*
                 * 2. How to create ClientHandler object and run the thread? Beware, you may
                 * need to pass some arguments (2 marks)
                 */

                 Socket _socket = ssk.accept();
                 system.out.println("Connection started");
                 new ClientHandler(_socket).started();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } // end of try-catch
    }// end of main thread

}// end of POSServer

class ClientHandler extends Thread/* 3. How to make ClientHandler become multithread? (1 marks) */ {

    private Socket socket = null;
    private AuctionEvents events = null;
    private BufferedReader in;
    private PrintWriter out;
    private Products curProduct;
    private Date curProductLastDtm;

    /*
     * 4. When you pass something to contructor in main(), you also need to add the
     * arguments here. (2 marks)
     */ 
    public ClientHandler(Socket socket) {
        this.socket = socket;
        this.SetupInOutStreams();
        this.curProduct = null;
    }

    private void CloseConnection() {
        try {
            /* 5. How to close the connection? (3 marks) */
            _in.close();
            _out.close();
            _socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SetupInOutStreams() {
        try {
            /*
             * 6. How to create input and output streams from socket? Beware output stream
             * need auto flush (3 marks)
             */
            _out = new PrintWriter(_socket.getOutputStream(), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SendToClient(String msg) {
        try {
            // 7. How to send "msg" to client? (1 marks)
        } catch (Exception e) {
            e.printStackTrace();
        }
    }// end of SendToClient(String msg)

    private String GetUserCommand() {
        String cmd = "";
        try {
            // 8. How to get user input from client? (1 marks)
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cmd;
    }

    /*
     * 9. Replace the value xxx. What method should be override, so that the code
     * inside this method will be executed in a separate thread. Add any annotation
     * needed (2 marks)
     */
    public void xxx() {
        /****************** !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ******************/
        /****************** DON'T CHANGE ANYTHING BEYOND THIS LINE ******************/
        /****************** !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ******************/
        try {
            while (true) {
                String command = this.GetUserInput("Please enter your command (g|b|t|q): ", "^(g|b|t|q)$");
                if (command.equals("")) {
                    command = "q";
                } else {
                    command = command.trim().toLowerCase();
                }

                if (events.isAllCompleted()) {
                    this.SendToClient("Auction is over.");
                    command = "q";
                }

                switch (command) {
                    case "g":
                        // Get current product
                        this.SendCurrentUpdatedProductToClient();
                        break;
                    case "b":
                        // Check if there is a current product
                        if (curProduct == null) {
                            this.SendToClient("There is no current product.");
                            this.SendCurrentUpdatedProductToClient();
                        } else {
                            // Get the bid price from user
                            String sBid = this.GetUserInput("Please enter your bid price: ", "^[0-9]+(\\.[0-9]+)?$");
                            float fBid = Float.parseFloat(sBid);
                            // Place the bid, if the bid is successful, send a message to client
                            // Otherwise, send a message to client, and get the current updated product
                            if (events.placeBid(curProduct.getId(), fBid, curProductLastDtm)) {
                                this.SendToClient("Your bid is successfully placed.");
                                this.curProductLastDtm = curProduct.getLastUpdateDtm();
                            } else {
                                this.SendToClient("Your bid is outdated or target price is reached.");
                                this.SendCurrentUpdatedProductToClient();
                            }
                        }
                        break;
                    case "t":
                        // Set target product
                        String sTargetProductId = this.GetUserInput("Please enter the target product id: ", "^[0-9]+$");
                        int iTargetProductId = Integer.parseInt(sTargetProductId);
                        this.SendToClient("Please wait for the current bid to complete.");
                        this.curProduct = this.events.abortBidAndWaitForProducts(iTargetProductId);
                        this.SendToClient(curProduct.toString());
                        break;
                    case "q":
                        // Quit
                        this.SendToClient("Bye.");
                        this.CloseConnection();
                        return;
                    default:
                        break;
                }
            } // end of while
        } catch (Exception e) {
            e.printStackTrace();
        } // end of try-catch
    }// end of run

    private void SendCurrentUpdatedProductToClient() {
        curProduct = events.getCurrentProduct();
        curProductLastDtm = curProduct.getLastUpdateDtm();
        this.SendToClient(curProduct.toString());
    }

    private String GetUserInput(String _msg, String possibleValueRegx) throws IOException {
        String _cmd = "";

        do {
            // Send the variable _msg to client
            SendToClient(_msg);
            // 4. get the user input and assign to _cmd
            _cmd = this.GetUserCommand();

            _cmd = (_cmd == null) ? "" : _cmd;

            if (_cmd.isEmpty()
                    || possibleValueRegx.isEmpty()
                    || Pattern.compile(possibleValueRegx, Pattern.CASE_INSENSITIVE).matcher(_cmd).matches()) {
                break;
            }

            // Send the error message to client.
            SendToClient("Possible Value : [" + possibleValueRegx + "]. Please try again.");
        } while (true);

        return _cmd;
    }// end of GetUserInput(String _msg, String possibleValueRegx)
}// end of ClientHandler