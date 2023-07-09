package org.example.serverlibrary;
public class TalkingProtocol {
    private static final int CONNECTED = 0;
    private static final int TALKING = 1;
    private int state = CONNECTED;

    public String processInput( String theInput ) {
        String theOutput;

        if ( state == CONNECTED ) {
            theOutput = "User connected";
            state = TALKING;
        } else if ( theInput.equals ("Bye") && state == TALKING ) {
            theOutput = "User disconnected";
            state = CONNECTED;
        } else theOutput = "ERROR OCCURRED!";

        return theOutput;
    }
}