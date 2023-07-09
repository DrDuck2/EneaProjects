package org.example.clientLibrary;

import org.example.clientLibrary.windowManager.CommunicationManager;

import java.io.BufferedReader;
import java.io.IOException;

public class ClientListenThread extends Thread {
    private final BufferedReader input;

    public ClientListenThread( BufferedReader input ) {
        this.input = input;
    }

    @Override
    public void run( ) {
        try {
            String fromServer;
            while ( (fromServer = input.readLine ()) != null ) {
                if ( fromServer.equals ( "User disconnected" ) ) {
                    break;
                }
                if(!fromServer.equals ( "Received" )){
                    CommunicationManager.setReceivedInformation ( fromServer );
                }
            }
        } catch ( IOException e ) {
            e.printStackTrace ();
        }
    }
}