package org.example;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
public class BroadcastThread extends  Thread {
    private static final int BUFFER_SIZE = 256;
    private static final String BROADCAST_MESSAGE = "Hello, are you there?";
    private static final int SLEEP_TIME_MS = 5000;
    private int portNumber;

    public BroadcastThread(int portNumber){
        super("BroadcastServerThread");
        this.portNumber = portNumber;
    }

    public void run()
    {
        try{
            InetAddress address = InetAddress.getByName("127.0.0.1");
            DatagramSocket socket = new DatagramSocket(portNumber);
            socket.setBroadcast(true);
            byte[] buffer = BROADCAST_MESSAGE.getBytes();
            int[] destinationPorts = {5000,6000,7000};

            while(true)
            {
                for(int port : destinationPorts){
                    DatagramPacket packet = new DatagramPacket(buffer,buffer.length,address,port);
                    socket.send(packet);
                    System.out.println("Sending packet with info: "+BROADCAST_MESSAGE+" Address: " + packet.getAddress().getHostAddress() + " to port: " + packet.getPort());
                }
                try{
                    Thread.sleep(SLEEP_TIME_MS);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
