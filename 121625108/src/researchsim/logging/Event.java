package researchsim.logging;

import researchsim.map.Coordinate;
import researchsim.entities.Entity;

/**
 * parent class of moveEvent
 * represents events that take place on the grid
 */
public abstract class Event extends Object {
    /**
     * entity involved in event
     */
    private Entity entity;
    
    /**
     * coordinate of event
     */
    private Coordinate coordinate;
    
    /**
     * original coordinate of event
     */
    private Coordinate originalCoordinate;

    /**
     * Constructor for Event
     * @param entity the entity involved in the event
     * @param coordinate the coordinate involved in the event
     */
    public Event(Entity entity, Coordinate coordinate) {
        this.entity = entity;
        this.coordinate = coordinate;
        //orignalCoordinate will hold the initial coordinate
        this.originalCoordinate = coordinate;
    }

    /**
     * returns coordinate of event
     * @return coordinate of event
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * returns the original coordinate of event
     * @return originalCoordinate
     */
    public Coordinate getInitialCoordinate() {
        return originalCoordinate;
    }

    /**
     * return the entity in this event
     * @return entity
     */
    public Entity getEntity() {
        return entity;
    }


    /**
     * abstract class to be overwritten in moveEvent
     */
    @Override
    public abstract String toString();
}
