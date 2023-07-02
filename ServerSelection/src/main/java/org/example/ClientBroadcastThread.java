package org.example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

public class ClientBroadcastThread extends Thread {

    private static final Logger logger = Logger.getLogger (ClientBroadcastThread.class.getName ());
    private static final int BUFFER_SIZE = 256;
    private final AtomicBoolean running = new AtomicBoolean (false);
    private final Set<String> servers = new HashSet <> ();
    public ClientBroadcastThread() {
        super ("ClientBroadcastThread");
    }

    private void addServer(String server)
    {
        servers.add (server);
    }

    public Set<String> getServers()
    {
        return servers;
    }
    public void run()
    {
        running.set(true);
        String receivedMessage;
        String serverAddress;
        try ( DatagramSocket socket = new DatagramSocket (null) ) {
            socket.setReuseAddress (true);
            socket.bind (new InetSocketAddress (6666));
            byte[] buffer = new byte[BUFFER_SIZE];
            DatagramPacket packet = new DatagramPacket (buffer , buffer.length);

            while(running.get ()){
                System.out.println ("Waiting for a packet...");
                socket.receive (packet);
                serverAddress = packet.getAddress ().getHostAddress ();
                System.out.println ("Packet received");
                receivedMessage = new String (packet.getData () , 0 , packet.getLength ());


                String[] receivedMessageParts = receivedMessage.split (":");
                int serverPort = Integer.parseInt (receivedMessageParts[0].trim ());
                logger.info(receivedMessageParts[1].trim() + " From:");
                logger.info ("Senders Port: " + serverPort + " Senders address: " + serverAddress);
                addServer (serverPort + ":" + serverAddress);
            }
        } catch ( IOException e ) {
            throw new RuntimeException (e);
        }
    }
}
