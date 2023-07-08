package org.example.serverlibrary;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public class ChatRoom implements ISubject {
    private final Set < IObserver > clients;
    private static final ChatRoom instance = new ChatRoom ();

    private ChatRoom( ) {
        clients = new HashSet <> ();
    }

    public static ChatRoom getInstance( ) {
        return instance;
    }

    public synchronized void AddClient( IObserver client ) {
        clients.add (client);
    }

    public synchronized void RemoveClient( IObserver client ) {
        clients.remove (client);
    }

    public synchronized void SendMessage( IObserver client , String message ) {
        for ( IObserver clientele : clients ) {
            if ( clientele != client ) {
                clientele.ReceiveMessage (message);
            }
        }
    }
}