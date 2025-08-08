package researchsim.entities;

import researchsim.map.Coordinate;
import researchsim.util.Encodable;

import java.util.Objects;

/**
 * An entity is an item that can exist as an inhabitant of a tile. <br>
 * They will always have an associated size and coordinate. <br>
 * This governs the key attributes and methods of every entity on the Scenario grid. <br>
 *
 * @ass1_partial
 * @see researchsim.map.Tile
 */
public abstract class Entity implements Encodable {

    /**
     * Size associated with the entity.
     * That is, an entities physical size and attributes.
     */
    private final Size size;

    /**
     * Coordinate associated with the entity.
     * That is, where the entity is located on the map grid.
     */
    private Coordinate coordinate;

    /**
     * Creates an entity with a given size and coordinate.
     *
     * @param size       size associated with the entity
     * @param coordinate coordinate associated with the entity
     * @ass1
     */
    public Entity(Size size, Coordinate coordinate) {
        this.size = size;
        this.coordinate = coordinate;
    }

    /**
     * Returns this entity's size.
     *
     * @return associated size.
     * @ass1
     */
    public Size getSize() {
        return size;
    }

    /**
     * Returns this entity's scenario grid coordinate.
     *
     * @return associated coordinate.
     * @ass1
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Updates this entity's scenario grid coordinate.
     *
     * @param coordinate the new coordinate
     * @ass1
     */
    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    /**
     * Returns the human-readable name of this entity.
     *
     * @return human-readable name
     * @ass1
     */
    public abstract String getName();

    /**
     * Returns the human-readable string representation of this Entity.
     * <p>
     * The format of the string to return is:
     * <pre>name [EntityClass] at coordinate</pre>
     * Where:
     * <ul>
     *   <li>{@code EntityClass} is the entity's instance class simple name (see {@link #getClass()}
     *   )</li>
     *   <li>{@code name} is the entity's human-readable name according to the
     *   extended class implementation of {@link #getName()}</li>
     *   <li>{@code coordinate} is the entity's associated coordinate in human-readable form</li>
     * </ul>
     * For example:
     *
     * <pre>Fish [Fauna] at (1,3)</pre>
     * OR
     * <pre>Flower [Flora] at (6,4)</pre>
     *
     * @return human-readable string representation of this Entity
     * @ass1
     * @see Fauna#getName()
     */
    @Override
    public String toString() {
        return String.format("%s [%s] at %s",
            getName(),
            this.getClass().getSimpleName(),
            this.coordinate);
    }

    /**
     * returns the hashcode of an entity
     * Hashcode of entity made by hashing size and coordinate and multiplying them
     * this is to ensure the hash is coordinate and size dependent
     * @return hashcode of entity
     */
    @Override
    public int hashCode() {
        return (int) this.size.hashCode() * this.coordinate.hashCode();
    }

    /**
     * compares entity and another object to check if they are equal
     * they are equal if both are entities and their size and coordinate are equal
     * @param other Object to compare to current entity
     * @return True iff the Object is an entity and has the same size and coordinate
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() == this.getClass()
                && other.hashCode() == hashCode()) {
            return true;
        }
        return false;
    }

    /**
     * returns the machine readable String of the entity
     * string is formated as EntityClass-size-coordinate 
     * @return machine readabe String of the entity
     */
    public String encode() {
        String name = this.getClass().getSimpleName();
        name = name + '-' + getSize() + '-' + coordinate.getX() + ',' + coordinate.getY();
        return name;
    }
}
