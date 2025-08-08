package researchsim.map;

import researchsim.entities.Entity;
import researchsim.util.NoSuchEntityException;

/**
 * A tile on the scenario map that entities' items operate on.
 *
 * @ass1_partial
 * @ass1_test
 */
public class Tile {

    /**
     * The type of the tile
     */
    private final TileType type;
    /**
     * The entity that is occupying the tile (if any)
     */
    private Entity contents;

    /**
     * Creates a new tile with a given type.
     * Every new tile is initialised as unoccupied (empty).
     *
     * @param type tile type
     * @ass1
     */
    public Tile(TileType type) {
        this.type = type;
        this.contents = null;
    }

    /**
     * Returns the type of the tile.
     *
     * @return tile type
     * @ass1
     */
    public TileType getType() {
        return type;
    }

    /**
     * Returns the contents of the tile.
     *
     * @return tile contents
     * @throws NoSuchEntityException if the tile is empty
     * @ass1
     */
    public Entity getContents() throws NoSuchEntityException {
        if (!hasContents()) {
            throw new NoSuchEntityException("Attempted to get the entity at an empty Tile.");
        }
        return contents;
    }

    /**
     * Updates the contents of the tile.
     *
     * @param item new tile contents
     * @ass1
     */
    public void setContents(Entity item) {
        this.contents = item;
    }

    /**
     * Checks if the tile is currently occupied.
     *
     * @return true if occupied (has contents), else false
     * @ass1
     */
    public boolean hasContents() {
        return contents != null;
    }

    /**
     * returns the hashCode of the tile object
     * hashCode dependent on tiletype and inhabitant
     * @return hash value of tile
     */
    @Override
    public int hashCode() {
        if (hasContents()) {
            return (int) this.type.hashCode() * this.contents.hashCode();
        } else {
            return (int) this.type.hashCode();
        }
    }

    /**
     * Compares this tile and object to decide whether they are equal
     * @param other Object to compare to this tile
     * @return True iff the tile type, contents and Object type are the same
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() == this.getClass() && other.hashCode() == hashCode()) {
            return true;
        }
        return false;
    }
}
