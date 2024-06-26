package martin.ufc.server.infra.request.no_connected;

import martin.ufc.server.infra.request.Request;
import martin.ufc.server.infra.request.types.NoConnectedRequestType;

public class CreationRequest extends NoConnectedRequest implements Request {
    private final String tamagotchiName;

    public CreationRequest(NoConnectedRequestType type, String owner, String tamagotchiName) {
        super(type, owner);
        this.tamagotchiName = tamagotchiName;
    }


    public String getTamagotchiName() { return tamagotchiName; }
}
