package org.example;

import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException{
        new ServerThread().start();
    }
}
