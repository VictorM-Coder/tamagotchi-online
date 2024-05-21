package martin.ufc.server.infra.response;

import martin.ufc.model.JSONfier;

public class Response implements JSONfier{
    private final ResponseStatus status;
    private final JSONfier body;

    private Response(ResponseStatus status, JSONfier body) {
        this.status = status;
        this.body = body;
    }

    @Override
    public String toJSON() {
        return "{"
                + "\"status\": \"" + status + "\","
                + "\"body\": " + body.toJSON()
                + "}";
    }

    public static Response createSuccessResponse(JSONfier body) {
        return new Response(ResponseStatus.SUCCESS, body);
    }

    public static Response createFailResponse(JSONfier body) {
        return new Response(ResponseStatus.FAIL, body);
    }

    public static Response createErrorResponse(JSONfier body) {
        return new Response(ResponseStatus.ERROR, body);
    }
}
