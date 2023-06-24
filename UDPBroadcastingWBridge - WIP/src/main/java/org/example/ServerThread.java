package org.example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerThread extends Thread implements BridgeLink{

    private static final int PORT_NUMBER = 22222;
    private int ConnectedPort;
    private static final int WAIT_TIME_MS = 5000;
    private static final String BROADCAST_MESSAGE = "Hello clients!";
    private static final int BUFFER_SIZE = 256;
    public ServerThread()
    {
        super("ServerThread");
        ConnectedPort = Integer.parseInt(Bridge.getInstance().addLink(this));
    }

    public void run()
    {
        try{
            byte[] buffer = BROADCAST_MESSAGE.getBytes();
            InetAddress broadcastAddress = InetAddress.getByName("255.255.255.255");
            while (true) {
                try(DatagramSocket socket = new DatagramSocket(PORT_NUMBER)){
                    DatagramPacket packet = new DatagramPacket(buffer,buffer.length,broadcastAddress,ConnectedPort);
                    System.out.println("Packet destination address: "+ packet.getAddress().getHostAddress());
                    socket.send(packet);
                }catch (IOException e){
                    e.printStackTrace();
                }
                try{
                    Thread.sleep(WAIT_TIME_MS);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
    }
}
