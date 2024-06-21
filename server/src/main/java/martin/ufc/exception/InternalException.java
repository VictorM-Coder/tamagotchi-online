package martin.ufc.exception;

import martin.ufc.model.JSONfier;

public class InternalException extends Exception implements JSONfier {
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
