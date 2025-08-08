package researchsim.util;

import researchsim.entities.User;

/**
 * Denotes an entity that can be collected by the {@link researchsim.entities.User} in the
 * simulation.
 *
 * @ass2
 */
public interface Collectable {

    /**
     * Allows user to interact with other entities and collect them
     * @param user the user that is collecting other entities
     * @return points on depending on entity being collected by user
     */
    int collect(User user);
}
