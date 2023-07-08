package org.example.clientLibrary;

import org.example.clientLibrary.windowManager.CommunicationManager;

import java.io.PrintWriter;

public class ClientSpeakThread extends Thread {
    private final PrintWriter out;

    public ClientSpeakThread( PrintWriter out ) {
        this.out = out;
    }

    @Override
    public void run( ) {
        String fromUser;
        while ( true ) {
            try {
                Thread.sleep ( 300 );
            } catch ( InterruptedException e ) {
                throw new RuntimeException ( e );
            }
            fromUser = CommunicationManager.sendInformation;
            if ( fromUser != null ) {
                out.println ( fromUser );
                if ( fromUser.equals ( "Bye" ) ) {
                    break;
                }
            }
        }
    }
}