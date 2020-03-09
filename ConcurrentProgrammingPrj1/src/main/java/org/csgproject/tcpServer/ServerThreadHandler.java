package org.csgproject.tcpServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Set;

import static org.csgproject.util.Constants.SERVER_PORT;
import static org.csgproject.util.Constants.THREADS_NAME;

public class ServerThreadHandler {
    private ServerSocket seSocket;

    public void startServer() throws IOException {
        seSocket = new ServerSocket(SERVER_PORT);
        System.out.println("Server Started, awaiting connections!");
        while (true) {
            new TcpServerMultithread(seSocket.accept(), THREADS_NAME).start();
        }
    }

    public void closeServer() throws IOException {
        seSocket.close();
    }

    public static String retrieveAllUsersConnected() {
        StringBuffer connectedUsers = new StringBuffer();
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        for (Thread t : threadSet) {
            if (t instanceof TcpServerMultithread) {
                TcpServerMultithread tcpServerThread = (TcpServerMultithread)t;
                String clientName = tcpServerThread.getClientName() != null ? tcpServerThread.getClientName() : "Name N/A";
                connectedUsers.append(clientName + ", ");
            }
        }
        connectedUsers.setLength(connectedUsers.length() - 2);
        String result = connectedUsers.toString();
        return result;
    }
}
