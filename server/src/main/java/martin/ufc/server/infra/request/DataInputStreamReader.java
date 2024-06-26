package martin.ufc.server.infra.request;

import martin.ufc.exception.RequestException;
import martin.ufc.util.LoggerUtil;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;

public class DataInputStreamReader {
    private DataInputStreamReader() {}
    public static String read(DataInputStream dataInputRequest) throws RequestException {
        try {
            return readInputStream(dataInputRequest);
        } catch (Exception e) {
            LoggerUtil.logError("Error while trying reading the request");
            throw new RequestException("Request not readable");
        }
    }

    private static String readInputStream(DataInputStream inputStream) throws IOException {
        BufferedReader in = new BufferedReader(new java.io.InputStreamReader(inputStream));
        return in.readLine();
    }
}
