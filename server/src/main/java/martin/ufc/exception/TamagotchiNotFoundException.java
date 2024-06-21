package martin.ufc.exception;

import martin.ufc.model.JSONfier;

public class TamagotchiNotFoundException extends Exception implements JSONfier {
    public TamagotchiNotFoundException(String message) {
        super(message);
    }

    @Override
    public String toJSON() {
        return "{"
                + "\"message\": \"" + super.getMessage() + "\""
                + "}";
    }
}
