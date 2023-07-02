package org.example;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

public class Client {
    private static final Logger logger = Logger.getLogger (Client.class.getName ());
    private static final int BUFFER_SIZE = 256;

    public static void main( String[] args ) throws IOException {

        logger.info ("Creating Client Broadcast Thread...");
        ClientBroadcastThread clientBroadcastThread = new ClientBroadcastThread ();

        //Thread will listen to incoming UDP packets from various servers, store it into HashSet
        //If it is the same server it will not store it twice
        //It stores a String that contains the server port and server address
        //If needed other threads or objects can access the HashSet with function getServers()
        clientBroadcastThread.start ();


        //Every client will have a window displaying caught broadcasts from various servers
        //After a client chooses a certain server box he will connect to that server for communication



        //TODO
        //Code for displaying the servers and selecting the proper server


        int serverPort = 0; //4noError
        String serverAddress = ""; //4noError

        try (
                Socket kkSocket = new Socket (serverAddress , serverPort) ;
                PrintWriter out = new PrintWriter (kkSocket.getOutputStream () , true) ;
                BufferedReader in = new BufferedReader (new InputStreamReader (kkSocket.getInputStream ()))
        ) {
            ClientListenThread listenThread = new ClientListenThread (in);
            ClientSpeakThread speakThread = new ClientSpeakThread (out , new BufferedReader (new InputStreamReader (System.in)));

            listenThread.start ();
            speakThread.start ();

            try {
                listenThread.join ();
                speakThread.join ();
            } catch ( InterruptedException e ) {
                e.printStackTrace ();
            }
        } catch ( UnknownHostException e ) {
            logger.severe ("Don't know about host " + serverAddress);
            System.exit (1);
        } catch ( IOException e ) {
            logger.severe ("Couldn't get I/O for the connection to " + serverAddress);
            System.exit (1);
        }
    }
}