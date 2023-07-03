package org.example.serverlibrary;

import java.util.Set;
import java.util.logging.Logger;
import java.util.HashSet;

public class ChatRoom implements ISubject {

    private static final Logger logger = Logger.getLogger (ChatRoom.class.getName ());
    private final Set < IObserver > clients;
    private static final ChatRoom instance = new ChatRoom ();

    private ChatRoom( ) {
        clients = new HashSet <> ();
    }

    public static ChatRoom getInstance( ) {
        logger.info ("Get ChatRoom instance.");
        return instance;
    }

    public synchronized void AddClient( IObserver client ) {
        logger.info ("Added Client: " + Integer.toHexString (client.hashCode ()));
        clients.add (client);
    }

    public synchronized void RemoveClient( IObserver client ) {
        logger.info ("Client Removed: " + Integer.toHexString (client.hashCode ()));
        clients.remove (client);
    }

    public synchronized void SendMessage( IObserver client , String message ) {
        logger.info ("Message Sender: " + Integer.toHexString (client.hashCode ()));
        for ( IObserver clientele : clients ) {
            if ( clientele != client ) {
                logger.info ("Sending message from: " + Integer.toHexString (client.hashCode ()) + " to: " + Integer.toHexString (clientele.hashCode ()));
                clientele.ReceiveMessage (Integer.toHexString (client.hashCode ()) + ":" + message);
            }
        }
    }
}