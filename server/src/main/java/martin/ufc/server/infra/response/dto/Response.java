package martin.ufc.server.infra.response.dto;

import martin.ufc.model.JSONfier;

public class Response implements JSONfier{
    private final ResponseStatus status;
    private final ResponseBody responseBody;

    private Response(ResponseStatus status, ResponseBody responseBody) {
        this.status = status;
        this.responseBody = responseBody;
    }

    @Override
    public String toJSON() {
        return "{"
                + "\"status\": \"" + status + "\","
                + "\"body\": " + responseBody.toJSON()
                + "}";
    }

    public static Response createSuccessResponse(ResponseBody responseBody) {
        return new Response(ResponseStatus.SUCCESS, responseBody);
    }

    public static Response createFailResponse(JSONfier exception) {
        ExceptionResponseBody responseBody = new ExceptionResponseBody(exception);
        return new Response(ResponseStatus.FAIL, responseBody);
    }

    public static Response createErrorResponse(JSONfier exception) {
        ExceptionResponseBody responseBody = new ExceptionResponseBody(exception);
        return new Response(ResponseStatus.ERROR, responseBody);
    }
}
