package org.example;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;
public class ChatRoom implements ISubject{

    private static final Logger logger = Logger.getLogger(ChatRoom.class.getName());
    private List<IObserver> clients;
    private static volatile ChatRoom instance = null;

    private ChatRoom()
    {
        clients = new CopyOnWriteArrayList<>();
    }
    public static ChatRoom getInstance()
    {
        logger.info("Get ChatRoom instance.");
        if(instance == null)
        {
            synchronized (ChatRoom.class){
                if(instance == null) //Double If statement for thread-safe lazy initialization.
                {
                    instance = new ChatRoom();
                    logger.info("ChatRoom instance created.");
                }
            }
        }
        if(instance == null) logger.severe("CHATROOM INSTANCE TAKEN BUT NOT INITIALIZED!");
        return instance;
    }

    public synchronized void AddClient(IObserver client)
    {
        logger.info("Added Client: " + Integer.toHexString(client.hashCode()));
        clients.add(client);
    }
    public synchronized void RemoveClient(IObserver client)
    {
        logger.info("Client Removed: " + Integer.toHexString(client.hashCode()));
        clients.remove(client);
    }
    public synchronized void SendMessage(IObserver client, String message)
    {
        logger.info("Message Sender: " + Integer.toHexString(client.hashCode()));
        for(IObserver clientele : clients)
        {
            if(clientele!=client)
            {
                logger.info("Sending message from: " + Integer.toHexString(client.hashCode()) + " to: " + Integer.toHexString(clientele.hashCode()));
                clientele.ReceiveMessage(Integer.toHexString(client.hashCode()) + ":" + message);
            }
        }
    }
}
