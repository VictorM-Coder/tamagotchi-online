package martin.ufc.server.infra.response;

public enum ResponseStatus {
    /**
     * Represents a successful response, when all the action are recognized and executed
     */
    SUCCESS,

    /**
     * Represents that the request was recognized but not executed
     */
    FAIL,
    /**
     * Represents that an unknown error has occurred
     */
    ERROR,
}
