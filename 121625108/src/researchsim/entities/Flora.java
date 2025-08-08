package researchsim.entities;

import researchsim.map.Coordinate;

/**
 * Subclass of class Entity
 * represents plants on the grid
 */
public class Flora extends Entity {

    /**
     * Constructor for Flora
     * @param size size of Flora
     * @param coordinate coordinate on grid of Flora
     */
    public Flora(Size size, Coordinate coordinate) {
        super(size, coordinate);
    }

    /**
     * name of plant decided based on size
     * @returns name of plant
     */
    @Override
    public String getName() {
        switch (super.getSize()) {
            case SMALL: 
                return "Flower";
            case MEDIUM:
                return "Shrub";
            case LARGE:
                return "Sapling";
            case GIANT:
                return "Tree";
            default:
                return null;
        }
    }
}
