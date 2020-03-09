package org.csgproject.util;

public final class Constants {

    public static final int SERVER_PORT = 8080;
    public static final String THREADS_NAME = "TCPThread";

    public static final class ServerResponse {
        public ServerResponse() {}
        public static final String END_CONNECTION_SERVER_CONFIRMATION = "Server closing the connection";
        public static final String MAIN_MENU = "Hi, %s! What would you like to do? 1. Greet Server 2. Display current users connected 3. Close connection";
        public static final String USERS_CONNECTED_MENU = "The users currently connected are: %s. Would you like to do something else? 1. Yes 2. No";
        public static final String GREETING_MENU = "Greetings you too! Would you like to do something else? 1. Yes 2. No";
        public static final String ASK_CLOSE_CONNECTION_CONFIRMATION = "Are you sure you want to terminate the connection? 1. Yes 2. No";

    }

    public static final class ClientRequest {
        public ClientRequest() {}
        public static final String END_CONNECTION = "END_CONNECTION";
        public static final String GREET_SERVER = "GREET_SERVER";
        public static final String DISPLAY_USERS_CONNECTED = "BUY_COOKIE";

    }


}
