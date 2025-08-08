package researchsim.entities;

import researchsim.map.Coordinate;

/**
 * Entity is the parent class to Fauna and Flora
 * Entity represents living things on a tile
 */
public abstract class Entity extends Object {
    /**
     * coordinate on the entity
     */
    private Coordinate coordinate;
    
    /**
     * size of the entity
     */
    private Size size;

    /**
     * Constructs class Entity
     * @param coordinate represents position of entity
     * @param size represents size of entity
     */
    public Entity(Size size, Coordinate coordinate) {
        this.coordinate = coordinate;
        this.size = size;
    }

    /**
     * returns the size
     * @return the size of Entity
     */
    public Size getSize() {
        return this.size;
    }

    /**
     * Returns the coordinate
     * @return the coordinate of the Entity
     */
    public Coordinate getCoordinate() {
        return this.coordinate;
    }

    /**
     * Sets the coordinate
     * @param coordinate the coordinate to set
     */
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    /**
     *Method that will overriden and used in the subclasses
     * @return the name of entity
     */
    public abstract String getName();
    
    /**
     * Returns the readable name of an Entity
     * @returns name of entity in readable form
     */
    @Override
    public String toString() {
        String name = getName() + " [" + this.getClass().getSimpleName() + "]" + " at ";
        name = name + getCoordinate();
        return name;
    }
}
