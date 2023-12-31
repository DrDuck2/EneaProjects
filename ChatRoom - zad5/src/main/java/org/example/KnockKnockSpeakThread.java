package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class KnockKnockSpeakThread extends Thread {
    private final PrintWriter out;
    private final BufferedReader stdIn;

    public KnockKnockSpeakThread( PrintWriter out , BufferedReader stdIn ) {
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
                    out.println (fromUser);
                    if ( fromUser.equals ("Bye") ) {
                        break;
                    }
                }
            }
        } catch ( IOException e ) {
            e.printStackTrace ();
        }
    }
}