package org.example;
import java.io.*;
import java.net.*;
import java.util.logging.Logger;

public class KnockKnockClient {

    private static final int BUFFER_SIZE = 256;

    public static void main(String[] args) throws IOException {

        if (args.length != 1) {
            System.err.println(
                    "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);
        int serverPort = 0;
        String serverAddress = "";

        try{
            DatagramSocket socket = new DatagramSocket(portNumber);
            byte[] buffer = new byte[BUFFER_SIZE];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            System.out.println("Waiting for a packet...");
            socket.receive(packet);
            System.out.println("Packet received");
            serverPort = packet.getPort();
            serverAddress = packet.getAddress().getHostAddress();
            System.out.println("Senders Port: " + serverPort + " Senders address: " + serverAddress);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (
                Socket kkSocket = new Socket(serverAddress, serverPort);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
        ) {
            // Thread for reading server messages
            Thread serverThread = new Thread(() -> {
                try {
                    String fromServer;
                    while ((fromServer = in.readLine()) != null) {
                        System.out.println(fromServer);
                        if(fromServer.equals("Chat Room: You have disconnected from the Chat Room.")) break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            serverThread.start();

            // Thread for listening to user input
            Thread userInputThread = new Thread(() -> {
                try {
                    BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
                    String fromUser;
                    while (true) {
                        fromUser = stdIn.readLine();
                        if (fromUser != null) {
                            out.println(fromUser);
                            if(fromUser.equals("Bye")) break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            userInputThread.start();

            serverThread.join();
            userInputThread.join();
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host 127.0.0.1");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to 127.0.0.1");
            System.exit(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}