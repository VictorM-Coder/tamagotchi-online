package martin.ufc.server.infra.data_stream_handlers;

import martin.ufc.exception.WriteOutputStreamException;
import martin.ufc.server.infra.response.dto.Response;
import martin.ufc.util.LoggerUtil;

import java.io.IOException;
import java.io.OutputStream;

public class ResponseMessenger {
    private ResponseMessenger() {}
    public static void sendResponse(OutputStream outputStream, Response response) throws WriteOutputStreamException {
        try {
            outputStream.write(response.toJSON().getBytes());
        } catch (IOException e) {
            LoggerUtil.logError("Error while sending response to client");
            throw new WriteOutputStreamException("");
        }
    }
}
