package martin.ufc.server.infra.handlers;

import martin.ufc.server.infra.response.Response;
import martin.ufc.util.LoggerUtil;

import java.io.*;
import java.net.Socket;

public class ClientHandler {
    private final Socket client;
    private final RequestMessageHandler requestMessageHandler;

    public ClientHandler(Socket client) {
        this.client = client;
        this.requestMessageHandler = new RequestMessageHandler();
    }

    public void communicateWithClient() {
        try (
                DataInputStream dataInput = new DataInputStream(client.getInputStream());
                OutputStream dataOutput = client.getOutputStream()
        ) {
            String request = readInputStream(dataInput);
            Response response = requestMessageHandler.handleClientMessage(request);
            dataOutput.write(response.toJSON().getBytes());
        } catch (Exception e) {
            LoggerUtil.logError(e.getMessage());
        }
    }

    public void closeClient() {
        try {
            client.close();
        } catch (IOException e) {
            LoggerUtil.logTrace(e.getMessage());
        }
    }

    private String readInputStream(DataInputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        return in.readLine();
    }
}
