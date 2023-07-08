package org.example.clientLibrary;

import org.example.clientLibrary.windowLibrary.CommunicationManager;

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
                if(!fromServer.equals ( "Received" )){
                    CommunicationManager.setReceivedInformation ( fromServer );
                }
                if ( fromServer.equals ( "User disconnected" ) ) {
                    break;
                }
            }
        } catch ( IOException e ) {
            e.printStackTrace ();
        }
    }
}