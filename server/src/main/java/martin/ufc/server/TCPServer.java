package martin.ufc.server;

import martin.ufc.model.tamagotchi.TamagotchiKeeper;
import martin.ufc.server.infra.request.handlers.ClientHandler;
import martin.ufc.util.LoggerUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    private static final int PORT = 12345;
    private static final TamagotchiKeeper tamagotchiKeeper = new TamagotchiKeeper();

    public static void run() {
        try (ServerSocket server = new ServerSocket(PORT)) {
            LoggerUtil.logTrace("Server listening to the port " + PORT);
            while (true) {
                Socket client = server.accept();
                LoggerUtil.logTrace("Client Connected: " + client.getInetAddress());
                handleClient(client);
                LoggerUtil.logTrace("Client Disconnected: " + client.getInetAddress());
            }
        } catch (IOException ioException) {
            LoggerUtil.logError("Server failed");
        }
    }

    private static void handleClient(Socket client) {
        ClientHandler clientHandler = new ClientHandler(client, tamagotchiKeeper);
        clientHandler.communicateWithClient();
        clientHandler.closeClient();
    }
}
