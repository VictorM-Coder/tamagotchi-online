package martin.ufc.server.infra.request.no_connected;

import martin.ufc.server.infra.request.Request;

public abstract class NoConnectedRequest implements Request {
    private final NoConnectedRequestType type;
    private final String owner;

    protected NoConnectedRequest(NoConnectedRequestType type, String owner) {
        this.type = type;
        this.owner = owner;
    }

    public NoConnectedRequestType getType() {
        return type;
    }

    public String getOwner() {
        return owner;
    }
}
