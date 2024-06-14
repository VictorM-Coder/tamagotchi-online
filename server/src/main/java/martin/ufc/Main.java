package martin.ufc;

import martin.ufc.server.TCPServer;
import martin.ufc.util.LoggerUtil;

public class Main {
    public static void main(String[] args) {
        LoggerUtil.logTrace(
                """

                        ████████  █████  ███    ███  █████   ██████   ██████  ████████  ██████ ██   ██ ██       ███████ ███████ ██████  ██    ██ ███████ ██████ \s
                           ██    ██   ██ ████  ████ ██   ██ ██       ██    ██    ██    ██      ██   ██ ██       ██      ██      ██   ██ ██    ██ ██      ██   ██\s
                           ██    ███████ ██ ████ ██ ███████ ██   ███ ██    ██    ██    ██      ███████ ██ █████ ███████ █████   ██████  ██    ██ █████   ██████ \s
                           ██    ██   ██ ██  ██  ██ ██   ██ ██    ██ ██    ██    ██    ██      ██   ██ ██            ██ ██      ██   ██  ██  ██  ██      ██   ██\s
                           ██    ██   ██ ██      ██ ██   ██  ██████   ██████     ██     ██████ ██   ██ ██       ███████ ███████ ██   ██   ████   ███████ ██   ██""");
        LoggerUtil.logTrace("Starting Tamagotchi Server...");
        TCPServer.run();
    }
}