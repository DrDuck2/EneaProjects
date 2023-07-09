package org.example.serverlibrary;

import java.util.HashSet;
import java.util.Set;

public class ConversationManager implements ISubject {
    private final Set < IObserver > clients;
    private static final ConversationManager instance = new ConversationManager ();

    private ConversationManager( ) {
        clients = new HashSet <> ();
    }

    public static ConversationManager getInstance( ) {
        return instance;
    }

    @Override
    public synchronized void AddClient( IObserver client ) {
        clients.add ( client );
    }

    @Override
    public synchronized void RemoveClient( IObserver client ) {
        clients.remove ( client );
    }

    @Override
    public synchronized void SendMessage( IObserver client , String message ) {
        for ( IObserver clientele : clients ) {
            if ( clientele != client ) {
                clientele.ReceiveMessage ( message );
            }
        }
    }
}