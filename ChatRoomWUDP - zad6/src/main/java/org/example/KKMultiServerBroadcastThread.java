package org.example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class KKMultiServerBroadcastThread extends Thread {
    private static final Logger logger = Logger.getLogger (KKMultiServerBroadcastThread.class.getName ());
    private static final int SLEEP_TIME_MS = 5000;
    private final int portNumber;
    private final AtomicBoolean running = new AtomicBoolean (false);

    public KKMultiServerBroadcastThread(int portNumber) {
        super ("BroadcastServerThread");
        this.portNumber = portNumber;
    }

    @Override
    public void run( ) {
        running.set(true);
        String BROADCAST_MESSAGE = portNumber + ":" + "Hello, are you there?";
        byte[] buffer = BROADCAST_MESSAGE.getBytes ();
        try(DatagramSocket socket = new DatagramSocket ()) {
            socket.setBroadcast (true);
            InetAddress address = InetAddress.getByName ("255.255.255.255");

            while ( running.get () ) {
                logger.info ("Broadcasting message: " + BROADCAST_MESSAGE);
                DatagramPacket packet;
                packet = new DatagramPacket(buffer,buffer.length,address,portNumber);
                socket.send(packet);
                try {
                    Thread.sleep (SLEEP_TIME_MS);
                } catch ( InterruptedException e ) {
                    e.printStackTrace ();
                }
            }
        } catch ( IOException e ) {
            e.printStackTrace ();
        }
    }
}
