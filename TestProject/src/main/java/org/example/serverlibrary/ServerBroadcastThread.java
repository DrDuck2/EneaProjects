package org.example.serverlibrary;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerBroadcastThread extends Thread {
    private final int portNumber;
    private final int serverPort;

    public ServerBroadcastThread( int portNumber , int serverPort ) {
        super ( "BroadcastServerThread" );
        this.portNumber = portNumber;
        this.serverPort = serverPort;
    }

    @Override
    public void run( ) {
        String broadcastMessage = serverPort + ":" + "Hello, are you there?";
        byte[] buffer = broadcastMessage.getBytes ();

        try ( DatagramSocket socket = new DatagramSocket () ) {
            socket.setBroadcast ( true );
            InetAddress address = InetAddress.getByName ( "255.255.255.255" );

            while ( !Thread.currentThread ().isInterrupted () ) {
                DatagramPacket packet = new DatagramPacket ( buffer , buffer.length , address , portNumber );
                socket.send ( packet );

                try {
                    int sleepTimeMS = 5000;
                    Thread.sleep ( sleepTimeMS );
                } catch ( InterruptedException e ) {
                    e.printStackTrace ();
                    // Restore interrupted status
                    Thread.currentThread ().interrupt ();
                }
            }
        } catch ( IOException e ) {
            e.printStackTrace ();
        }
    }
}