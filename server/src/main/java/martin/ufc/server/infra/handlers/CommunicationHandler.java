package martin.ufc.server.infra.handlers;

import java.net.Socket;

public class CommunicationHandler {
    private final Socket client;

    public CommunicationHandler(Socket client) {
        this.client = client;
    }

    public void communicateWithClient() {
        Thread thread = new Thread(new ActionRequestsHandler(client));
        thread.start();
    }
}
