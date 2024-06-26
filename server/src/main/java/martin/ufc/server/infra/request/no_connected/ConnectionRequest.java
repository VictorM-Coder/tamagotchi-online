package martin.ufc.server.infra.request.no_connected;

import martin.ufc.server.infra.request.Request;
import martin.ufc.server.infra.request.types.NoConnectedRequestType;

public class ConnectionRequest extends NoConnectedRequest implements Request {
    private int id;

    public ConnectionRequest(NoConnectedRequestType type, String owner, int id) {
        super(type, owner);
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
