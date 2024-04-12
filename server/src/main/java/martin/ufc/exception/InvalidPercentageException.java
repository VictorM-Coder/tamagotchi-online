package martin.ufc.exception;

public class InvalidPercentageException extends RuntimeException {
    public InvalidPercentageException(int percent) {
        super(percent + " is not a valid percentage");
    }
}
