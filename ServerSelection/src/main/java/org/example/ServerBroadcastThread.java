package org.example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class ServerBroadcastThread extends Thread {
    private static final Logger logger = Logger.getLogger ( ServerBroadcastThread.class.getName () );
    private final int SLEEP_TIME_MS;
    private final int portNumber;
    private final AtomicBoolean running = new AtomicBoolean ( false );

    public ServerBroadcastThread( int portNumber , int SLEEP_TIME_MS) {
        super ( "BroadcastServerThread" );
        this.portNumber = portNumber;
        this.SLEEP_TIME_MS = SLEEP_TIME_MS;
    }

    @Override
    public void run( ) {
        running.set ( true );
        String BROADCAST_MESSAGE = portNumber + ":" + "Hello, are you there?";
        byte[] buffer = BROADCAST_MESSAGE.getBytes ();
        try ( DatagramSocket socket = new DatagramSocket () ) {
            socket.setBroadcast ( true );
            InetAddress address = InetAddress.getByName ( "255.255.255.255" );

            while ( running.get () ) {
                logger.info ( "Broadcasting message: " + BROADCAST_MESSAGE );
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