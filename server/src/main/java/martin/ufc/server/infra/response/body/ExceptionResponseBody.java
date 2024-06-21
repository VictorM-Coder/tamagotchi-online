package martin.ufc.server.infra.response.body;

import martin.ufc.model.JSONfier;

public class ExceptionResponseBody implements ResponseBody {
    private final JSONfier exception;

    public ExceptionResponseBody(JSONfier exception) {
        this.exception = exception;
    }

    @Override
    public String toJSON() {
        return exception.toJSON();
    }
}
