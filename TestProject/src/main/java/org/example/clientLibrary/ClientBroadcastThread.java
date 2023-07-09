package org.example.clientLibrary;


import org.example.clientLibrary.windowLibrary.ServerSelectionHandling.ServerSelectionBlock;
import org.example.clientLibrary.windowManager.SetupManager;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class ClientBroadcastThread extends Thread {

    private static final int BUFFER_SIZE = 256;

    public ClientBroadcastThread( ) {
        super ( "ClientBroadcastThread" );
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
                socket.receive ( packet );
                if ( isInterrupted () ) break;
                serverAddress = packet.getAddress ().getHostAddress ();
                receivedMessage = new String ( packet.getData () , 0 , packet.getLength () );


                String[] receivedMessageParts = receivedMessage.split ( ":" );
                int serverPort = Integer.parseInt ( receivedMessageParts[0].trim () );

                String serverInformation = serverPort + ":" + serverAddress;

                SetupManager.addObject ( "ServerSelectHandler" , new ServerSelectionBlock ( serverInformation ) );

            }
        } catch ( IOException e ) {
            throw new RuntimeException ( e );
        }
    }

}
