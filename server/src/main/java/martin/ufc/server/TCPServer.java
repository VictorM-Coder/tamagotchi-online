package martin.ufc.server;

import martin.ufc.util.LoggerUtil;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPServer {
    private static final int PORT = 12345;

    public static void run() {
        try (ServerSocket server = new ServerSocket(PORT)) {
            LoggerUtil.logInfo("Server listening to the port " + PORT);
            while(true) {

            }
        } catch (IOException ioException) {
            LoggerUtil.logError("Fail during server starting");
        }
    }
}
