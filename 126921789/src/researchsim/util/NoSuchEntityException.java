package researchsim.util;

/**
 * Exception thrown when attempting to get the entity at an empty tile.
 *
 * @ass1_partial
 */
public class NoSuchEntityException extends Exception {

    /**
     * Constructs a NoSuchEntityException with no detail message.
     *
     * @ass1
     * @see Exception#Exception()
     */
    public NoSuchEntityException() {
        super();
    }

    /**
     * Constructs a NoSuchEntityException that contains a helpful detail message explaining why the
     * exception occurred.
     * <p>
     * <b>Important:</b> do not write JUnit tests that expect a valid implementation of the
     * assignment to have a certain error message, as the official solution will use different
     * messages to those you are expecting, if any at all.
     *
     * @param message detail message
     * @ass1
     * @see Exception#Exception(String)
     */
    public NoSuchEntityException(String message) {
        super(message);
    }

    /**
     * Constructs NoSuchEntityException with the cause of the error
     * @param cause the issue that raised the error
     */
    public NoSuchEntityException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs NoSuchEntityException with the cause of the error and a message.
     * The message contains helpful info about the error
     * @param message Information about the exception
     * @param cause Issue that caused the error
     */
    public NoSuchEntityException(String message, Throwable cause) {
        super(message, cause);
    }

}
