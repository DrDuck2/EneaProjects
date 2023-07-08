package org.example.serverlibrary;


import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class Server {
    private final static Logger logger = Logger.getLogger ( Server.class.getName () );

    public static void main( String[] args ) {

        if ( args.length != 2 ) {
            logger.severe ( "Usage: java KKMultiServer <port number> <server port>" );
            System.exit ( 1 );
        }

        int portNumber = Integer.parseInt ( args[0] );
        int serverPort = Integer.parseInt ( args[1] );

        ServerBroadcastThread broadcast = new ServerBroadcastThread ( portNumber,serverPort);
        broadcast.start ();

        try ( ServerSocket serverSocket = new ServerSocket ( serverPort ) ) {
            while ( true ) {
                new ServerThread ( serverSocket.accept () ).start ();
            }
        } catch ( IOException e ) {
            logger.severe ( "Could not listen on port " + portNumber );
            System.exit ( - 1 );
        }
    }
}