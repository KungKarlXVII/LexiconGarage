package se.lexicon.exception;

public class GarageException extends Exception {

    public GarageException() {
        super();
    }
    public GarageException(String message) {
        super(message);
    }
    public GarageException(String message, Throwable cause) {
        super(message, cause);
    }

}
