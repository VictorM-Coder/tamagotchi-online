package martin.ufc.exception;

import martin.ufc.model.JSONfier;

public class ResponseException extends Exception implements JSONfier {
    public ResponseException(String message) {
        super(message);
    }

    @Override
    public String toJSON() {
        return "{"
                + "\"message\": " + super.getMessage()
                + "}";
    }
}
