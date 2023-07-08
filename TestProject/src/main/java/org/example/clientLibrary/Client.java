package org.example.clientLibrary;

import org.example.CountdownLatchWithInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Logger;

public class Client {
    private static final Logger logger = Logger.getLogger ( Client.class.getName () );

    public static void main( String[] args ) throws  InterruptedException {

        CountdownLatchWithInfo<String> eventLatch = new CountdownLatchWithInfo <> ( 1 );

        ClientWindowThread window = new ClientWindowThread (eventLatch);
        window.start ();

        ClientBroadcastThread broadcastThread = new ClientBroadcastThread ( );
        broadcastThread.start ();

        eventLatch.await ();
        broadcastThread.interrupt ();


        String serverData = eventLatch.getInformation ();
        String[] receivedMessageParts = serverData.split ( ":" );

        int serverPort = Integer.parseInt ( receivedMessageParts[0].trim () );
        String serverAddress = receivedMessageParts[1];



        try (
                Socket kkSocket = new Socket ( serverAddress , serverPort ) ;
                PrintWriter out = new PrintWriter ( kkSocket.getOutputStream () , true ) ;
                BufferedReader in = new BufferedReader ( new InputStreamReader ( kkSocket.getInputStream () ) )
        ) {
            ClientListenThread listenThread = new ClientListenThread ( in );
            ClientSpeakThread speakThread = new ClientSpeakThread ( out );

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