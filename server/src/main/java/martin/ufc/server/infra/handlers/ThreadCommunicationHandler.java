package martin.ufc.server.infra.handlers;

import java.net.Socket;

public class ThreadCommunicationHandler {
    private ThreadCommunicationHandler() {}
    public static void communicateWithClient(Socket client) {
        Thread thread = new Thread(new RequestHandler(client));
        thread.start();
    }
}
