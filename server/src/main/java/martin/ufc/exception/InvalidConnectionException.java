package martin.ufc.exception;

import martin.ufc.model.JSONfier;

public class InvalidConnectionException extends Exception implements JSONfier {
    public InvalidConnectionException(String message) {
        super(message);
    }

    @Override
    public String toJSON() {
        return "{"
                + "\"message\": \"" + super.getMessage() + "\""
                + "}";
    }
}
