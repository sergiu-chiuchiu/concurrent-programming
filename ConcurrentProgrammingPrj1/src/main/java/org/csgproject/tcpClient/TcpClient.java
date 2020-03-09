package org.csgproject.tcpClient;

import org.csgproject.util.Constants.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static org.csgproject.util.Constants.ServerResponse;

public class TcpClient {
    private PrintWriter send;
    private BufferedReader receive;
    BufferedReader consoleReader;
    private Socket clSocket;

    public void close() throws IOException {
        send.close();
        receive.close();
        clSocket.close();
    }

    public void establishSocketConnection(String targetIp, int port) throws IOException {
        clSocket = new Socket(targetIp, port);
        send = new PrintWriter(clSocket.getOutputStream(), true);
        receive = new BufferedReader(new InputStreamReader(clSocket.getInputStream()));
    }

    public String beginCommunication(String information) throws IOException {
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
        send.println("Hello!");
        String response;

        while (!(response = receive.readLine()).equals(ServerResponse.END_CONNECTION_SERVER_CONFIRMATION)) {
            System.out.println(response);
            String consoleInput = consoleReader.readLine();
            send.println(consoleInput);
        }
        System.out.println(response);
        send.println(ClientRequest.END_CONNECTION);
        close();
        return response;
    }




}
