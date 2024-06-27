package martin.ufc.server.infra.data_stream_handlers;

import martin.ufc.exception.RequestException;
import martin.ufc.util.LoggerUtil;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.concurrent.*;

public class RequestReader {
    private RequestReader() {}
    public static String read(DataInputStream dataInputRequest) throws TimeoutException, RequestException {
        try {
            return readInputStream(dataInputRequest);
        } catch (TimeoutException e) {
            LoggerUtil.logError("Request take too long");
            throw new TimeoutException();
        } catch (Exception e) {
            LoggerUtil.logError("Error while trying reading the request");
            throw new RequestException("Request not readable");
        }
    }

    //TODO Adiconar uma exceçào que fecha a conexão caso ocorra
    private static String readInputStream(DataInputStream inputStream) throws TimeoutException {
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
        String request = "";

        final Duration timeout = Duration.ofSeconds(300);
        ExecutorService executor = Executors.newSingleThreadExecutor();

        final Future<String> handler = executor.submit(in::readLine);

        try {
            request = handler.get(timeout.toMillis(), TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            handler.cancel(true);
            throw new TimeoutException();
        } catch (InterruptedException | ExecutionException e) {
            handler.cancel(true);
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdownNow();
        }

        return request;
    }
}
