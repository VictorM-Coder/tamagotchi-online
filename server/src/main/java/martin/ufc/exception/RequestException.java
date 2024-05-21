package martin.ufc.exception;

import martin.ufc.model.JSONfier;

public class RequestException extends Exception implements JSONfier {
    public RequestException(String message) {
        super(message);
    }

    @Override
    public String toJSON() {
        return "{"
                + "\"message\": " + super.getMessage()
                + "}";
    }
}
