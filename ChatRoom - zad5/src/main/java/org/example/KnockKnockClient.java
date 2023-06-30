package org.example;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

public class KnockKnockClient {
    private static final Logger logger = Logger.getLogger ( KnockKnockClient.class.getName ( ) );
    public static void main( String[] args ){

        if ( args.length != 2 ) {
            logger.severe ("Usage: java EchoClient <host name> <port number>");
            System.exit (1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt (args[1]);

        try (
                Socket kkSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()))
        ) {
            KnockKnockListenThread listenThread = new KnockKnockListenThread(in);
            KnockKnockSpeakThread speakThread = new KnockKnockSpeakThread(out, new BufferedReader(new InputStreamReader(System.in)));

            listenThread.start();
            speakThread.start();

            try {
                listenThread.join();
                speakThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + hostName);
            System.exit(1);
        }
    }
}