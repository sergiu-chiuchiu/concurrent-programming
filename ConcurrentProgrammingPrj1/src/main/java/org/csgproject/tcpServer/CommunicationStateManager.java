package org.csgproject.tcpServer;

import org.csgproject.util.Constants;

public class CommunicationStateManager {

    CommunicationState communicationState = CommunicationState.INITIALIZING;

    public CommunicationStateManager() {
    }

    public CommunicationState getCommunicationState() {
        return communicationState;
    }

    public void setCommunicationState(CommunicationState communicationState) {
        this.communicationState = communicationState;
    }
}
