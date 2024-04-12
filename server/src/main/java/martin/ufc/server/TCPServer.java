package martin.ufc.server;

import martin.ufc.server.infra.ClientHandler;
import martin.ufc.util.LoggerUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private static final int PORT = 12345;

    public static void run() {
        try (ServerSocket server = new ServerSocket(PORT)) {
            LoggerUtil.logInfo("Server listening to the port " + PORT);
            while (true) {
                Socket client = server.accept();
                LoggerUtil.logInfo("Client Connected: " + client.getInetAddress());
                handleClient(client);
            }
        } catch (IOException ioException) {
            LoggerUtil.logError("Fail during server starting");
        }
    }

    private static void handleClient(Socket client) {
        ClientHandler clientHandler = new ClientHandler(client);
        clientHandler.communicateWithClient();
        clientHandler.closeClient();
    }
}
