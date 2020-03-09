package org.csgproject;

import org.csgproject.tcpClient.TcpClient;

import java.io.IOException;

public class AppClient {
    public static void main(String[] args) {
        TcpClient cl1 = new TcpClient();
        try {
            cl1.establishSocketConnection("127.0.0.1", 8080);
            String payload1 = cl1.beginCommunication("Good morning");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
