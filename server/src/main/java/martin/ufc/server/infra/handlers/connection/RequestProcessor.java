package martin.ufc.server.infra.handlers.connection;

import martin.ufc.server.infra.response.dto.Response;

@FunctionalInterface
public interface RequestProcessor {
    Response runProcess() throws Exception;
}
