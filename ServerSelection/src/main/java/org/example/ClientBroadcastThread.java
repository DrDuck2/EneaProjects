package org.example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class ClientBroadcastThread extends Thread {

    private static final Logger logger = Logger.getLogger ( ClientBroadcastThread.class.getName () );
    private static final int BUFFER_SIZE = 256;
    private final ClientWindowThread window;
    private boolean switcher = true;
    public ClientBroadcastThread( ClientWindowThread window ) {
        super ( "ClientBroadcastThread" );
        this.window = window;
    }

    public void run( ) {
        String receivedMessage;
        String serverAddress;
        try ( DatagramSocket socket = new DatagramSocket ( null ) ) {
            socket.setReuseAddress ( true );
            InetSocketAddress sixSocket = new InetSocketAddress ( 6666 );
            InetSocketAddress sevenSocket = new InetSocketAddress ( 7777 );
            byte[] buffer = new byte[BUFFER_SIZE];
            DatagramPacket packet = new DatagramPacket ( buffer , buffer.length );

            while ( ! isInterrupted () ) {

                if(switcher){
                    socket.bind(sixSocket);
                    switcher = false;
                }
                else{
                    socket.bind(sevenSocket);
                    switcher = true;
                }
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

                window.addServer ( serverPort + ":" + serverAddress );

                socket.close();
            }
        } catch ( IOException e ) {
            throw new RuntimeException ( e );
        }
    }

}
