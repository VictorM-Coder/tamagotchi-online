package martin.ufc.exception;

import martin.ufc.server.infra.response.body.ResponseBody;

public class RequestException extends Exception implements ResponseBody {
    public RequestException(String message) {
        super(message);
    }

    @Override
    public String toJSON() {
        return "{"
                + "\"message\": \"" + super.getMessage() + "\""
                + "}";
    }
}
