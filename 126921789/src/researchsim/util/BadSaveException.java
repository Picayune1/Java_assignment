package researchsim.util;

/**
 * Exception thrown when a save file is invalid or contains incorrect data.
 *
 * @ass2
 */
public class BadSaveException extends Exception {

    /**
     * Constructs a BadSaveException with no detail message.
     */
    public BadSaveException() {
        super();
    }

    /**
     * Constructs a BadSaveException with a detail message describing the cause of the error
     * @param message the message on why this error happened
     */
    public BadSaveException(String message) {
        super(message);
    }

    /**
     * Constructs a bad save exception that contains the cause of the exception
     * @param cause the issue that led to the exception
     */
    public BadSaveException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a bad save exception with both a message and the cause of the exception
     * @param message message explaining why the error happened
     * @param cause issue that led to the error
     */
    public BadSaveException(String message, Throwable cause) {
        super(message, cause);
    }

}
