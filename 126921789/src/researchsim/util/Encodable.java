package researchsim.util;

/**
 * Denotes a class whose state can be encoded and represented as a String.
 *
 * @ass2
 */
public interface Encodable {

    /**
     * returns the machine-readable string for the object
     * @return machine-readable string
     */
    String encode();
}
