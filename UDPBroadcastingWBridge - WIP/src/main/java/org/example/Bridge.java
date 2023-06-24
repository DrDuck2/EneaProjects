package org.example;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.HashSet;

public class Bridge {
    private Logger logger = Logger.getLogger(Bridge.class.getName());
    private static volatile Bridge instance = null;
    private static final int BUFFER_SIZE = 256;
    private static volatile String[] portNumbers = {"20002","30003","40004","50005","60006"};
    private static volatile Set<String> usedPortNumbers;
    private static volatile Hashtable<BridgeLink,String> hashtable;
    private Bridge()
    {
        hashtable = new Hashtable<>();
        usedPortNumbers = new HashSet<>();
    }
    public static Bridge getInstance()
    {
        if(instance == null)
        {
            instance = new Bridge();
        }
        return instance;
    }

    public synchronized String addLink(BridgeLink link)
    {
        String availablePortNumber = getAvailablePortNumber();
        if (availablePortNumber != null) {
            System.out.println("Available port number: "+ availablePortNumber);
            logger.info("Link Added");
            hashtable.put(link, availablePortNumber);
            usedPortNumbers.add(availablePortNumber);
            logger.info("Starting Thread with port number: " + availablePortNumber);
        } else {
            System.out.println("No available port number.");
        }
        startListeningThread(availablePortNumber);
        System.out.println("Next Available port number: "+ getAvailablePortNumber());
        return availablePortNumber;
    }
    public synchronized void removeLink(BridgeLink link) {
        String assignedPortNumber = hashtable.remove(link);
        if (assignedPortNumber != null) {
            usedPortNumbers.remove(assignedPortNumber);
        } else {
            System.out.println("BridgeLink not found in the hashtable.");
        }
    }

    private synchronized String getAvailablePortNumber() {
        for (String portNumber : portNumbers) {
            if (!usedPortNumbers.contains(portNumber)) {
                return portNumber;
            }
        }
        return null;
    }
    private synchronized void startListeningThread(String portNumber)
    {
        Thread listeningThread = new Thread(() ->{
           try{
               DatagramSocket socket = new DatagramSocket(Integer.parseInt(portNumber));
               byte[] buffer = new byte[256];
               DatagramPacket packet = new DatagramPacket(buffer,BUFFER_SIZE);

               while(true)
               {
                   logger.info("Waiting for packet");
                   socket.receive(packet);
                   logger.info("Received packet");
                   System.out.println("Packet address: " + packet.getAddress().getHostAddress());
                   logger.info("Forwarding message");
                   forwardPacketToOtherPorts(packet,portNumber);
                   logger.info("Finished Forwarding");
               }
           }catch (IOException e)
           {
               e.printStackTrace();
           }
        });

        listeningThread.start();
    }

    private void forwardPacketToOtherPorts(DatagramPacket packet,String originatingPort) throws IOException{
        byte[] data = packet.getData();
        int length = packet.getLength();
        InetAddress address = packet.getAddress();
        if(address.isMulticastAddress() || address.getHostAddress().equals("192.168.56.1")){
           System.out.println("Broadcast address detected. Broadcasting packet to all other ports.");
           try(MulticastSocket multicastSocket = new MulticastSocket()){
               DatagramPacket forwardingPacket = new DatagramPacket(data,length,InetAddress.getByName("224.0.0.1"),0);

               for(String portNumber : hashtable.values()){
                   if(!portNumber.equals(originatingPort)){
                       forwardingPacket.setPort(Integer.parseInt(portNumber));
                        multicastSocket.send(forwardingPacket);
                        System.out.println("Forwarded broadcast message to port " + portNumber);
                   }
               }
           }
        }
        else{
            String portNumber = hashtable.get(this);
            if(portNumber!=null && !portNumber.equals(originatingPort)){
                DatagramPacket forwardingPacket = new DatagramPacket(data,length,Integer.parseInt(portNumber));
                try(DatagramSocket socket = new DatagramSocket()){
                    socket.send(forwardingPacket);
                }
                System.out.println("Forwarded message to port " + portNumber);
            }
        }

    }
}
