package martin.ufc.exception;

import martin.ufc.server.infra.response.body.ResponseBody;

public class TamagotchiNotFoundException extends Exception implements ResponseBody {
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
