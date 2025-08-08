package researchsim.util;

/**
 * Custom exception for when no entity is in a tile
 */
public class NoSuchEntityException extends Exception {

    /**
     * constructor if no message is given
     */
    public NoSuchEntityException() {}

    /**
     * constructor if message is given
     * @param message error message of what's wrong
     */
    public NoSuchEntityException(String message) {
        super(message);
    }
}
