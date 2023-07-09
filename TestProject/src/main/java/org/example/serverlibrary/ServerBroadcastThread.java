package org.example.serverlibrary;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerBroadcastThread extends Thread {
    private final int portNumber;
    private final int SLEEP_TIME_MS = 5000;
    private final int serverPort;
    private final AtomicBoolean running = new AtomicBoolean ( false );

    public ServerBroadcastThread( int portNumber , int serverPort ) {
        super ( "BroadcastServerThread" );
        this.portNumber = portNumber;
        this.serverPort = serverPort;
    }

    @Override
    public void run( ) {
        running.set ( true );
        String BROADCAST_MESSAGE = serverPort + ":" + "Hello, are you there?";
        byte[] buffer = BROADCAST_MESSAGE.getBytes ();
        try ( DatagramSocket socket = new DatagramSocket () ) {
            socket.setBroadcast ( true );
            InetAddress address = InetAddress.getByName ( "255.255.255.255" );

            while ( running.get () ) {
                DatagramPacket packet;
                packet = new DatagramPacket ( buffer , buffer.length , address , portNumber );
                socket.send ( packet );
                try {
                    Thread.sleep ( SLEEP_TIME_MS );
                } catch ( InterruptedException e ) {
                    e.printStackTrace ();
                }
            }
        } catch ( IOException e ) {
            e.printStackTrace ();
        }
    }
}