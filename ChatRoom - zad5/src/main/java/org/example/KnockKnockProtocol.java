package org.example;

import java.net.*;
import java.io.*;

public class KnockKnockProtocol {
    private static final int CONNECTED = 0;
    private static final int TALKING = 1;
    private int state = CONNECTED;
    public String processInput(String theInput) {
        String theOutput = null;

        if (state == CONNECTED) {
            theOutput = "Chat Room: Welcome to the Chat Room!";
            state = TALKING;
        }
        else if(theInput.equals("Bye") && state == TALKING) {
            theOutput = "Chat Room: You have disconnected from the Chat Room.";
            state = CONNECTED;
        }
        else theOutput = "ERROR OCCURRED!";

        return theOutput;
    }
}