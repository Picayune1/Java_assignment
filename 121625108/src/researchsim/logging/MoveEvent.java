package researchsim.logging;

import researchsim.map.Coordinate;
import researchsim.entities.Entity;

/**
 * subclass of Event
 * represents a moving event on the grid
 */
public class MoveEvent extends Event {
    /**
     * Represents the location of entity when object was initialised
     */
    private static String string;

    /**
     * Constructs MoveEvent
     * @param entity the entity involved in the MoveEvent
     * @param coordinate the coordinate the entity is at
     */
    public MoveEvent(Entity entity, Coordinate coordinate) {
        super(entity, coordinate);
        string = entity.toString();
    }
    
    /**
     * creates human-readable form of MoveEvent an returns it
     * @returns readable form of MoveEvent
     */
    @Override
    public String toString() {
        String text = "MOVED TO " + super.getCoordinate().toString();
        String finalText = string + System.lineSeparator() + text + System.lineSeparator();
        finalText = finalText + "-----";
        
        return finalText;
    }
    
    private String getString() {
        return string;
    }
}
