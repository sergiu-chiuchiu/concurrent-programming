package org.csgproject.tcpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import static org.csgproject.util.Constants.ClientRequest;
import static org.csgproject.util.Constants.ServerResponse;

public class TcpServerMultithread extends Thread {
    private PrintWriter send;
    private Socket clSocket;
    private BufferedReader receive;
    private CommunicationStateManager csm;

    private String clientName;

    public TcpServerMultithread(Socket clSocket, String name) {
        super (name);
        this.clSocket = clSocket;
    }

    public TcpServerMultithread() {
    }

    public void close() {
        send.close();
        try {
            clSocket.close();
            receive.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            System.out.println("New connection at port " + clSocket.getPort());
            send = new PrintWriter(clSocket.getOutputStream(), true);
            receive = new BufferedReader(new InputStreamReader(clSocket.getInputStream()));
            csm = new CommunicationStateManager();

            // Initial response
            String receivedLine = receive.readLine();
            send.println("Hello, you have been assigned to port " + clSocket.getPort() + ". What is your name?");

            while ((receivedLine = receive.readLine()) != null) {
                if (ClientRequest.END_CONNECTION.equals(receivedLine)) {
                    send.println(ServerResponse.END_CONNECTION_SERVER_CONFIRMATION);
                    break;
                }
                if ((csm.getCommunicationState().equals(CommunicationState.INITIALIZING)) || receivedLine.equals("1") || receivedLine.equals("2") || receivedLine.equals("3")) {
                    communicateWithClient(receivedLine, csm.getCommunicationState());
                } else {
                    send.printf(ServerResponse.MAIN_MENU, clientName).println();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        close();
    }

    private void communicateWithClient(String receivedLine, CommunicationState communicationState) {
        switch (receivedLine) {
            case "1":
                if (communicationState.equals(CommunicationState.MENU_DISPLAYED)) {
                    csm.setCommunicationState(CommunicationState.GREET_SERVER);
                    send.println(ServerResponse.GREETING_MENU);
                } else if (communicationState.equals(CommunicationState.GREET_SERVER) || communicationState.equals(CommunicationState.DISPLAY_USERS_CONNECTED)) {
                    csm.setCommunicationState(CommunicationState.MENU_DISPLAYED);
                    send.printf(ServerResponse.MAIN_MENU, clientName).println();
                } else if (communicationState.equals(CommunicationState.CLOSE_CONNECTION_CONFIRMATION)) {
                    csm.setCommunicationState(CommunicationState.CLOSE_CONNECTION);
                    send.println(ServerResponse.END_CONNECTION_SERVER_CONFIRMATION);
                }
                break;
            case "2":
                if (communicationState.equals(CommunicationState.MENU_DISPLAYED)) {
                    csm.setCommunicationState(CommunicationState.DISPLAY_USERS_CONNECTED);
                    send.printf(ServerResponse.USERS_CONNECTED_MENU,  ServerThreadHandler.retrieveAllUsersConnected()).println();
                } else if (communicationState.equals(CommunicationState.GREET_SERVER) || communicationState.equals(CommunicationState.DISPLAY_USERS_CONNECTED)) {
                    csm.setCommunicationState(CommunicationState.CLOSE_CONNECTION);
                    send.println(ServerResponse.END_CONNECTION_SERVER_CONFIRMATION);
                } else if (communicationState.equals(CommunicationState.CLOSE_CONNECTION_CONFIRMATION)) {
                    csm.setCommunicationState(CommunicationState.MENU_DISPLAYED);
                    send.printf(ServerResponse.MAIN_MENU, clientName).println();
                }
                    break;
            case "3":
                if (communicationState.equals(CommunicationState.MENU_DISPLAYED)) {
                    csm.setCommunicationState(CommunicationState.CLOSE_CONNECTION_CONFIRMATION);
                    send.println(ServerResponse.ASK_CLOSE_CONNECTION_CONFIRMATION);
                }
                break;
            default:
                if (communicationState.equals(CommunicationState.INITIALIZING)) {
                    csm.setCommunicationState(CommunicationState.MENU_DISPLAYED);
                    clientName = receivedLine;
                    send.printf(ServerResponse.MAIN_MENU, clientName).println();
                }
                break;
        }
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
