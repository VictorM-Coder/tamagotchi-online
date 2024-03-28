package martin.ufc.model;

public class InvalidPercentageException extends RuntimeException {
    public InvalidPercentageException(int percent) {
        super(percent + " is not a valid percentage");
    }
}
