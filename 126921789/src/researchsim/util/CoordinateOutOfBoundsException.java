package researchsim.util;

/**
 * Exception thrown when the program attempts to access a Tile using an invalid Coordinate / index.
 *
 * @ass2
 */
public class CoordinateOutOfBoundsException extends Exception {

    /**
     * Constructs a CoordinateOutOfBoundsException without a message
     */
    public CoordinateOutOfBoundsException() {
        super();
    }

    /**
     * Constructs a CoordinateOutOfBoundsException
     * with a message detailing what caused the exception
     * @param message message detailing the cause of the error
     */
    public CoordinateOutOfBoundsException(String message) {
        super(message);
    }

    /**
     * Constructs a CoordinateOutOfBoundsException that stores the cause of the error
     * @param cause the issue that caused the exception
     */
    public CoordinateOutOfBoundsException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a CoordinateOutOfBoundsException with both the cause of the error and a message
     * the message details what the error is
     * @param message details on the exception
     * @param cause the issue that caused the exception to be thrown
     */
    public CoordinateOutOfBoundsException(String message, Throwable cause) {
        super(message, cause);
    }
}
