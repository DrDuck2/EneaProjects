package org.example;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.concurrent.CountDownLatch;

public class Client {
    private static final Logger logger = Logger.getLogger ( Client.class.getName () );

    public static void main( String[] args ) throws  InterruptedException {

        // This line is necessary for code continuation
        // We cannot create a connection between server and client if the client didn't choose which server
        // to connect to, after he chooses then the program continues creating threads for listening and speaking
        CountDownLatch eventLatch = new CountDownLatch ( 1 );

        // Creating Window thread for displaying the servers
        logger.info ( "Starting Client Window..." );
        ClientWindowThread window = new ClientWindowThread ( eventLatch );
        window.start ();

        // After window thread is active, we create client thread that is listening for broadcasts from the servers
        logger.info ( "Starting Client Broadcast Thread..." );
        ClientBroadcastThread broadcastThread = new ClientBroadcastThread ( window );
        broadcastThread.start ();

        //Every client will have a window displaying caught broadcasts from various servers
        //After a client chooses a certain server box, he will connect to that server for communication

        // Event latch is changed inside the window thread after the client chooses the server
        eventLatch.await ();

        HashMap< ClickableArea, String > map = window.getServers ();
        for(String value : map.values ()){
            logger.info ( value );
        }

        // Closing broadcast thread because we are already connected to the server
        broadcastThread.interrupt ();

        // Extracting port and server address from the chosen server
        String serverData = window.getClickedServerData ();
        String[] receivedMessageParts = serverData.split ( ":" );

        int serverPort = Integer.parseInt ( receivedMessageParts[0].trim () );
        String serverAddress = receivedMessageParts[1];

        logger.info("Connected to server on port: " + serverPort + " and address: " + serverAddress);
        // Establishing communication with the server through TCP and listening and speaking thread
        try (
                Socket kkSocket = new Socket ( serverAddress , serverPort ) ;
                PrintWriter out = new PrintWriter ( kkSocket.getOutputStream () , true ) ;
                BufferedReader in = new BufferedReader ( new InputStreamReader ( kkSocket.getInputStream () ) )
        ) {
            ClientListenThread listenThread = new ClientListenThread ( in );
            ClientSpeakThread speakThread = new ClientSpeakThread ( out , new BufferedReader ( new InputStreamReader ( System.in ) ) );

            listenThread.start ();
            speakThread.start ();

            try {
                listenThread.join ();
                speakThread.join ();
            } catch ( InterruptedException e ) {
                e.printStackTrace ();
            }
        } catch ( UnknownHostException e ) {
            logger.severe ( "Don't know about host " + serverAddress );
            System.exit ( 1 );
        } catch ( IOException e ) {
            logger.severe ( "Couldn't get I/O for the connection to " + serverAddress );
            System.exit ( 1 );
        }
    }
}