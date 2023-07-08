package org.example.clientLibrary;

import org.example.clientLibrary.windowLibrary.CommunicationManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ClientSpeakThread extends Thread {
    private final PrintWriter out;
    private final BufferedReader stdIn;

    public ClientSpeakThread( PrintWriter out , BufferedReader stdIn ) {
        this.out = out;
        this.stdIn = stdIn;
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