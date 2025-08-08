package researchsim.logging;

import researchsim.entities.*;

/**
 * The collection of an entity that implemented {@link researchsim.util.Collectable} by a
 * {@link User}.
 *
 * @ass2
 */
public class CollectEvent extends Event {

    /**
     * The user entity involved in this Collect event
     */
    private User user;

    /**
     *  The target entity that is being collected in this collect event
     */
    private Entity target;

    /**
     * the string representing the user when the CollectEvent is made
     */
    private final String userString;

    /**
     * The string representing the target when the CollectEvent is made
     */
    private final String targetString;

    /**
     * Creates a new event of a user collecting some entity 
     * @param user the user that is collecting
     * @param target the entity being collected by the user
     */
    public CollectEvent(User user, Entity target) {
        super(user, target.getCoordinate());
        this.target = target;
        this.userString = user.toString();
        this.targetString = target.toString();
    }

    /**
     * returns the target entity that is being collected in the collect event
     * @return the entity being collected
     */
    public Entity getTarget() {
        return target;
    }

    /**
     * return the String representation of the collect event the format being 
     *  user.toString
     *  COLLECTED
     *  entity.toString
     * @return the string representation of the collectEvent
     */
    @Override
    public String toString() {
        String string = userString + System.lineSeparator();
        string = string + "COLLECTED" + System.lineSeparator();
        string = string + targetString + System.lineSeparator();
        string = string + "-".repeat(5);
        return string;
    }
}
