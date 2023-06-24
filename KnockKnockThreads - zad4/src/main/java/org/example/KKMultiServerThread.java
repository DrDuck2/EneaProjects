package org.example;
import java.net.*;
import java.io.*;

public class KKMultiServerThread extends Thread implements IObserver {
    private Socket socket = null;
    private String inputLine, outputLine;
    public KKMultiServerThread(Socket socket) {
        super("KKMultiServerThread");
        this.socket = socket;
    }

    public void ReceiveMessage(String message)
    {
        outputLine = message;
    }
    public void run() {
        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
        ) {
            ChatRoom.getInstance().AddClient(this);
            KnockKnockProtocol kkp = new KnockKnockProtocol();

            outputLine = kkp.processInput(null); //Welcome to the Chat Room
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) { //Communication part
                if(inputLine.equals("Bye")){ //Client sends Bye / Disconnect
                    break;
                }
                ChatRoom.getInstance().SendMessage(this,inputLine);
                out.println("#MessageSent");
            }
            ChatRoom.getInstance().SendMessage(this,"User: " + Integer.toHexString(this.hashCode()) + " has disconnected");
            outputLine = kkp.processInput("Bye"); //Disconnect from the Chat Room
            out.println(outputLine);
            ChatRoom.getInstance().RemoveClient(this);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}