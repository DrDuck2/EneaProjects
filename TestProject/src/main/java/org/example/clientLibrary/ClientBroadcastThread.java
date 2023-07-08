package org.example.clientLibrary;


import org.example.clientLibrary.windowLibrary.ServerSelectionHandling.ServerSelectionBlock;
import org.example.clientLibrary.windowManager.SetupManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.logging.Logger;

public class ClientBroadcastThread extends Thread {

    private static final Logger logger = Logger.getLogger ( ClientBroadcastThread.class.getName () );
    private static final int BUFFER_SIZE = 256;
    private final ClientWindowThread window;
    public ClientBroadcastThread( ClientWindowThread window ) {
        super ( "ClientBroadcastThread" );
        this.window = window;
    }

    public void run( ) {
        String receivedMessage;
        String serverAddress;

        try ( DatagramSocket socket = new DatagramSocket ( null ) ) {
            socket.setReuseAddress ( true );
            InetSocketAddress address = new InetSocketAddress ( 6666 );
            socket.bind ( address );
            byte[] buffer = new byte[BUFFER_SIZE];
            DatagramPacket packet = new DatagramPacket ( buffer , buffer.length );

            while ( ! isInterrupted () ) {
                logger.info ( "Waiting for a packet..." );
                socket.receive ( packet );
                if ( isInterrupted () ) break;
                serverAddress = packet.getAddress ().getHostAddress ();
                logger.info ( "Packet received" );
                receivedMessage = new String ( packet.getData () , 0 , packet.getLength () );


                String[] receivedMessageParts = receivedMessage.split ( ":" );
                int serverPort = Integer.parseInt ( receivedMessageParts[0].trim () );
                logger.info ( receivedMessageParts[1].trim () + " From:" );
                logger.info ( "Senders Port: " + serverPort + " Senders address: " + serverAddress );

                String serverInformation = serverPort+":"+serverAddress;

                //Todo
                //Add server port and address to the server screen for selection
                SetupManager.addObject ("ServerSelectHandler",new ServerSelectionBlock ( serverInformation ));
                ////

            }
        } catch ( IOException e ) {
            throw new RuntimeException ( e );
        }
    }

}
