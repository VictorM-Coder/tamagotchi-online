package martin.ufc;

import martin.ufc.exception.InvalidMessageException;
import martin.ufc.exception.SQLiteException;
import martin.ufc.model.tamagotchi.Tamagotchi;
import martin.ufc.persistence.repository.TamagotchiRepository;
import martin.ufc.server.TCPServer;
import martin.ufc.util.LoggerUtil;

public class Main {
    public static void main(String[] args) throws InvalidMessageException {
        LoggerUtil.logTrace("\n" +
                "████████  █████  ███    ███  █████   ██████   ██████  ████████  ██████ ██   ██ ██       ███████ ███████ ██████  ██    ██ ███████ ██████  \n" +
                "   ██    ██   ██ ████  ████ ██   ██ ██       ██    ██    ██    ██      ██   ██ ██       ██      ██      ██   ██ ██    ██ ██      ██   ██ \n" +
                "   ██    ███████ ██ ████ ██ ███████ ██   ███ ██    ██    ██    ██      ███████ ██ █████ ███████ █████   ██████  ██    ██ █████   ██████  \n" +
                "   ██    ██   ██ ██  ██  ██ ██   ██ ██    ██ ██    ██    ██    ██      ██   ██ ██            ██ ██      ██   ██  ██  ██  ██      ██   ██ \n" +
                "   ██    ██   ██ ██      ██ ██   ██  ██████   ██████     ██     ██████ ██   ██ ██       ███████ ███████ ██   ██   ████   ███████ ██   ██");
        LoggerUtil.logTrace("Starting Tamagotchi Server...");
        TCPServer.run();
    }
}