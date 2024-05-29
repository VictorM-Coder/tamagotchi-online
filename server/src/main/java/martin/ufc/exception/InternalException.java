package martin.ufc.exception;

import martin.ufc.server.infra.response.body.ResponseBody;

public class InternalException extends Exception implements ResponseBody {
    public InternalException(String message) {
        super(message);
    }

    @Override
    public String toJSON() {
        return "{"
                + "\"message\": \"" + super.getMessage() + "\""
                + "}";
    }
}
