package org.example.clientLibrary;

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
        try {
            String fromUser;
            while ( true ) {
                fromUser = stdIn.readLine ();
                if ( fromUser != null ) {
                    out.println ( fromUser );
                    if ( fromUser.equals ( "Bye" ) ) {
                        break;
                    }
                }
            }
        } catch ( IOException e ) {
            e.printStackTrace ();
        }
    }
}