package martin.ufc.server.infra.request.message;

import martin.ufc.exception.InvalidMessageException;

/**
 * MESSAGE FORMAT: OWNER ACTION BODY
 */
public class Message {
    private static final int OWNER_INDEX = 0;
    private static final int MESSAGE_TYPE_INDEX = 1;
    private static final int BODY_INDEX = 2;
    private MessageType messageType;
    private String owner;
    private String body;

    @Override
    public String toString() {
        return owner + " " + messageType + " " + body;
    }

    public Message(String message) throws InvalidMessageException {
        String[] splitMessage;
        splitMessage = message.split(" ");

        if (splitMessage.length == 3 || splitMessage.length == 2) {
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
            messageType = MessageType.valueOf(splitMessage[MESSAGE_TYPE_INDEX]);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new InvalidMessageException("Invalid MessageType");
        }

        if (splitMessage.length == 3) {
            body = splitMessage[BODY_INDEX];
        } else {
            body = "";
        }
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public String getOwner() {
        return owner;
    }

    public String getBody() {
        return body;
    }
}
