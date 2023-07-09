package org.example.serverlibrary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerCommunicationThread extends Thread implements IObserver {
    private final Socket socket;
    private volatile String outputLine = null;
    public ServerCommunicationThread( Socket socket ) {
        super ( "ServerThread" );
        this.socket = socket;
    }

    @Override
    public void ReceiveMessage( String message ) {
        outputLine = message;
    }

    @Override
    public void run( ) {
        try (
                PrintWriter out = new PrintWriter ( socket.getOutputStream () , true ) ;
                BufferedReader in = new BufferedReader (
                        new InputStreamReader (
                                socket.getInputStream () ) )
        ) {

            ConversationManager.getInstance ().AddClient ( this );
            String inputLine = null;
            while ( ! socket.isClosed () && ! Thread.currentThread ().isInterrupted () ) {
                if ( in.ready () ) {
                    inputLine = in.readLine (); // Receive message from client
                    if ( inputLine.split ( ":" )[1].equals ( "Bye" )) { // Client sends Bye / Disconnect
                        break;
                    }
                    ConversationManager.getInstance ().SendMessage ( this , inputLine ); // Forward the received message
                    out.println ( "Received" );
                    outputLine = null;
                }
                if ( outputLine != null ) {
                    out.println ( outputLine );
                    outputLine = null;
                }
            }

            //TODO: Enable disconnecting when user pressed ESC
            if(inputLine!=null){
                ConversationManager.getInstance ().SendMessage ( this , inputLine.split(":")[0] +":"+ "Disconnected" );
            }
            out.println ( "User disconnected" );
            ConversationManager.getInstance ().RemoveClient ( this );
            socket.close ();

        } catch ( IOException e ) {
            e.printStackTrace ();
        }
    }
}