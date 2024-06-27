package martin.ufc.server.infra.handlers;

import martin.ufc.server.infra.handlers.connection.ConnectionHandler;

import java.net.Socket;

public class ThreadCommunicationHandler {
    private ThreadCommunicationHandler() {}
    public static void communicateWithClient(Socket client) {
        Thread thread = new Thread(new ConnectionHandler(client));
        thread.start();
    }
}
