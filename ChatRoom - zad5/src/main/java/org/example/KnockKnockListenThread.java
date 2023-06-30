package org.example;

import java.io.BufferedReader;
import java.io.IOException;

public class KnockKnockListenThread extends Thread {
    private final BufferedReader input;

    public KnockKnockListenThread( BufferedReader input ) {
        this.input = input;
    }

    @Override
    public void run( ) {
        try {
            String fromServer;
            while ( (fromServer = input.readLine ()) != null ) {
                System.out.println (fromServer);
                if ( fromServer.equals ("Chat Room: You have disconnected from the Chat Room.") ) {
                    break;
                }
            }
        } catch ( IOException e ) {
            e.printStackTrace ();
        }
    }
}
