package nl.sogyo.mancala.exception;

public class InvalidMoveException extends Exception {

    private static final long serialVersionUID = -675401921818740102L;

    public InvalidMoveException() {
        super();
    }

    public InvalidMoveException(String message) {
        super(message);
    }

    public InvalidMoveException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMoveException(Throwable cause) {
        super(cause);
    }
}
