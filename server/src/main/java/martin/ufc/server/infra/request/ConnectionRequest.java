package martin.ufc.server.infra.request;

import martin.ufc.exception.InvalidMessageException;

public class ConnectionRequest {
    private static final int OWNER_INDEX = 0;
    private static final int BODY_INDEX = 1;
    private String owner;
    private int id;

    @Override
    public String toString() {
        return owner + " " + id;
    }

    public ConnectionRequest(String message) throws InvalidMessageException {
        String[] splitMessage;
        splitMessage = message.split(" ");

        if (splitMessage.length == 2) {
            buildMessageFromStringArray(splitMessage);
        } else {
            throw new InvalidMessageException("Invalid Message format");
        }
    }

    private void buildMessageFromStringArray(String[] splitMessage) throws InvalidMessageException {
        if (splitMessage[OWNER_INDEX].isEmpty()) {
            throw new InvalidMessageException("No owner passed");
        }

        owner = splitMessage[OWNER_INDEX];
        try {
            id = Integer.parseInt(splitMessage[BODY_INDEX]);
        } catch (NumberFormatException e) {
            throw new InvalidMessageException("Invalid id format");
        }
    }

    public String getOwner() {
        return owner;
    }

    public int getId() {
        return id;
    }
}
