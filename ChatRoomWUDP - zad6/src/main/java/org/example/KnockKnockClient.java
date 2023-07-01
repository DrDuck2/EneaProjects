package org.example;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

public class KnockKnockClient {
    private static final Logger logger = Logger.getLogger (KnockKnockClient.class.getName ());
    private static final int BUFFER_SIZE = 256;

    public static void main( String[] args ) throws IOException {

        String receivedMessage;
        String serverAddress;
        try ( DatagramSocket socket = new DatagramSocket (null) ) {
            socket.setReuseAddress (true);
            socket.bind (new InetSocketAddress (6666));
            byte[] buffer = new byte[BUFFER_SIZE];
            DatagramPacket packet = new DatagramPacket (buffer , buffer.length);
            System.out.println ("Waiting for a packet...");
            socket.receive (packet);
            serverAddress = packet.getAddress ().getHostAddress ();
            System.out.println ("Packet received");
            receivedMessage = new String (packet.getData () , 0 , packet.getLength ());
        }

        String[] receivedMessageParts = receivedMessage.split (":");
        int serverPort = Integer.parseInt (receivedMessageParts[0].trim ());
        logger.info ("Senders Port: " + serverPort + " Senders address: " + serverAddress);

        try (
                Socket kkSocket = new Socket (serverAddress , serverPort) ;
                PrintWriter out = new PrintWriter (kkSocket.getOutputStream () , true) ;
                BufferedReader in = new BufferedReader (new InputStreamReader (kkSocket.getInputStream ()))
        ) {
            KnockKnockListenThread listenThread = new KnockKnockListenThread (in);
            KnockKnockSpeakThread speakThread = new KnockKnockSpeakThread (out , new BufferedReader (new InputStreamReader (System.in)));

            listenThread.start ();
            speakThread.start ();

            try {
                listenThread.join ();
                speakThread.join ();
            } catch ( InterruptedException e ) {
                e.printStackTrace ();
            }
        } catch ( UnknownHostException e ) {
            logger.severe ("Don't know about host " + serverAddress);
            System.exit (1);
        } catch ( IOException e ) {
            logger.severe ("Couldn't get I/O for the connection to " + serverAddress);
            System.exit (1);
        }
    }
}