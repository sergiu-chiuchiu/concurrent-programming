package org.csgproject;

import org.csgproject.tcpServer.ServerThreadHandler;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{


    public static void main( String[] args )
    {
//        TcpServer tcpServer = new TcpServer();
//        tcpServer.openServer(SERVER_PORT);

        try {
            ServerThreadHandler serverThreadHandler = new ServerThreadHandler();
            serverThreadHandler.startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
