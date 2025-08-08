package researchsim.map;

import researchsim.entities.Entity;
import researchsim.util.NoSuchEntityException;

/**
 * represents spaces in the gird
 */
public class Tile extends Object {
    /**
     * The habitat or environment of the tile
     */
    private TileType type;
    /**
     * entities living on this tile
     */
    private Entity entity;

    /**
     * constructs a tile instance
     * @param type the type or habitat at this tile
     */
    public Tile(TileType type) {
        this.type = type;
        this.entity = null;
    }

    /**
     * returns the type of the tile
     * @return TileType
     */
    public TileType getType() {
        return type;
    }

    /**
     * returns the entity at this tile
     * @return entity at this tile
     * @throws NoSuchEntityException if no entity is their
     */
    public Entity getContents() throws NoSuchEntityException {
        if (entity == null) {
            throw new NoSuchEntityException();
        } else {
            return entity;
        }
    }

    /**
     * sets a entity at the tile
     * @param item the entity to put in the tile
     */
    public void setContents(Entity item) {
        this.entity = item;
    }

    /**
     * tests if tile has any entities
     * @return True iff the tile has entity
     */
    public boolean hasContents() {
        if (entity == null) {
            return false;
        } else {
            return true;
        }

    }
}
