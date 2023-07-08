package org.example.serverlibrary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

public class ServerThread extends Thread implements IObserver {
    private static final Logger logger = Logger.getLogger ( ServerThread.class.getName () );
    private final Socket socket;
    private volatile String outputLine = null;

    public ServerThread( Socket socket ) {
        super ( "ServerThread" );
        this.socket = socket;
    }

    public void ReceiveMessage( String message ) {
        outputLine = message;
    }

    public void run( ) {
        try (
                PrintWriter out = new PrintWriter ( socket.getOutputStream () , true ) ;
                BufferedReader in = new BufferedReader (
                        new InputStreamReader (
                                socket.getInputStream () ) )
        ) {
            ChatRoom.getInstance ().AddClient ( this );
            TalkingProtocol kkp = new TalkingProtocol ();

            while ( ! socket.isClosed () && ! Thread.currentThread ().isInterrupted () ) {
                if ( in.ready () ) {
                    String inputLine = in.readLine (); // Receive message from client
                    if ( inputLine.equals ( "Bye" ) ) { // Client sends Bye / Disconnect
                        break;
                    }
                    ChatRoom.getInstance ().SendMessage ( this , inputLine ); // Forward the received message
                    out.println ( "Received" );
                    outputLine = null;
                }
                if ( outputLine != null ) {
                    out.println ( outputLine );
                    outputLine = null;
                }
            }

            ChatRoom.getInstance ().SendMessage ( this , "DISCONNECTED" );
            outputLine = kkp.processInput ( "Bye" ); //Disconnect from the Chat Room
            out.println ( outputLine );
            ChatRoom.getInstance ().RemoveClient ( this );
            socket.close ();
        } catch ( IOException e ) {
            e.printStackTrace ();
        }
    }
}