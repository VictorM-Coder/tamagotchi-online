package martin.ufc.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggerUtil {
    private static final Logger logger = LogManager.getLogger();
    private LoggerUtil() {}

    public static void logTrace(String info) {
        logger.trace(info);
    }

    public static void logError(String error) {
        logger.error(error);
    }
}
