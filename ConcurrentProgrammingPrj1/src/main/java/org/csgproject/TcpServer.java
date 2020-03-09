package org.csgproject;
import java.net.*;
import java.io.*;

public class TcpServer {
    private PrintWriter send;
    private Socket clSocket;
    private ServerSocket seSocket;
    private BufferedReader receive;


    public void close() {
        try {
            send.close();
            clSocket.close();
            seSocket.close();
            receive.close();
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    public void openServer(int port) {
        //        Setup the sockets
        try {
            seSocket = new ServerSocket(port);
            clSocket = seSocket.accept();
            send = new PrintWriter(clSocket.getOutputStream(), true);
            receive = new BufferedReader(new InputStreamReader(clSocket.getInputStream()));
            String helloWorld = receive.readLine();
            if ("helloWorld".equals(helloWorld)) {
                send.println("Hi There");
            }
            else {
                send.println("Sorry");
            }
        } catch (IOException e) {
            System.out.println("IOException");
        }



    }


}
