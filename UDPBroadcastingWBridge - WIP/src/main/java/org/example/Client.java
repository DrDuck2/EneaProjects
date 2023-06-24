package org.example;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.logging.Logger;

public class Client implements BridgeLink {
    private static Logger logger = Logger.getLogger(Client.class.getName());
    private static final int BUFFER_SIZE = 256;
    private final int portNumber;

    public Client(int portNumber) {
        this.portNumber = portNumber;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println(
                    "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        int portNumber = Integer.parseInt(args[0]);

        Client client = new Client(portNumber);
        Bridge.getInstance().addLink(client);

        client.startListeningThread();
    }
    private void startListeningThread() {
        Thread listeningThread = new Thread(() -> {
            try {
                DatagramSocket socket = new DatagramSocket(portNumber);
                byte[] buffer = new byte[BUFFER_SIZE];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                boolean listening = true;
                while (listening) {
                    logger.info("Waiting for packet");
                    socket.receive(packet);
                    logger.info("Received packet");
                    InetAddress sourceAddress = packet.getAddress(); //Get Ip address of the server
                    int serverPort = packet.getPort(); //Server port number.
                    socket.close();
                    listening = false;
                    connectToTheServer(sourceAddress,serverPort);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void connectToTheServer(InetAddress sourceAddress, int serverPort)
    {
        try (
                Socket kkSocket = new Socket(sourceAddress, serverPort);
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
            System.err.println("Don't know about host " + sourceAddress);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " + sourceAddress);
            System.exit(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
