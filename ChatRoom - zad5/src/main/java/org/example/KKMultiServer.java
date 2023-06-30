package org.example;


import java.net.*;
import java.io.*;
import java.util.logging.Logger;

public class KKMultiServer {
    private static final Logger logger = Logger.getLogger ( KKMultiServer.class.getName ( ) );
    public static void main( String[] args ){

        if ( args.length != 1 ) {
            logger.severe ("Usage: java KKMultiServer <port number>");
            System.exit (1);
        }

        int portNumber = Integer.parseInt (args[0]);
        try ( ServerSocket serverSocket = new ServerSocket (portNumber) ) {
            while ( true ) {
                new KKMultiServerThread (serverSocket.accept ()).start ();
            }
        } catch ( IOException e ) {
            logger.severe ("Could not listen on port " + portNumber);
            System.exit (- 1);
        }
    }
}