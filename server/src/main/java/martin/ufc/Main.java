package martin.ufc;

import martin.ufc.exception.InvalidMessageException;
import martin.ufc.server.TCPServer;
import martin.ufc.server.infra.Message;
import martin.ufc.util.LoggerUtil;

public class Main {
    public static void main(String[] args) throws InvalidMessageException {
        LoggerUtil.logInfo("\n" +
                "████████  █████  ███    ███  █████   ██████   ██████  ████████  ██████ ██   ██ ██       ███████ ███████ ██████  ██    ██ ███████ ██████  \n" +
                "   ██    ██   ██ ████  ████ ██   ██ ██       ██    ██    ██    ██      ██   ██ ██       ██      ██      ██   ██ ██    ██ ██      ██   ██ \n" +
                "   ██    ███████ ██ ████ ██ ███████ ██   ███ ██    ██    ██    ██      ███████ ██ █████ ███████ █████   ██████  ██    ██ █████   ██████  \n" +
                "   ██    ██   ██ ██  ██  ██ ██   ██ ██    ██ ██    ██    ██    ██      ██   ██ ██            ██ ██      ██   ██  ██  ██  ██      ██   ██ \n" +
                "   ██    ██   ██ ██      ██ ██   ██  ██████   ██████     ██     ██████ ██   ██ ██       ███████ ███████ ██   ██   ████   ███████ ██   ██");
        LoggerUtil.logInfo("Starting Tamagotchi Server...");
        TCPServer.run();
    }
}