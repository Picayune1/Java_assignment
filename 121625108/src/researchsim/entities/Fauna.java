package researchsim.entities;

import researchsim.map.*;

/**
 * Subclass of Entity
 * represents animals on the gird
 */
public class Fauna extends Entity {
    /**
     * habitat of the entity
     */
    private TileType habitat;

    /**
     * Constructor for Fauna
     * @param size size of Fauna
     * @param coordinate coordinate of Fauna on grid
     * @param habitat habitat of
     * @throws IllegalArgumentException if habitat is mountain or sand
     */
    public Fauna(Size size, Coordinate coordinate, TileType habitat) {
        super(size, coordinate);
        if (habitat == TileType.SAND || habitat == TileType.MOUNTAIN) {
            throw new IllegalArgumentException(); 
        }
        this.habitat = habitat;
    }

    /**
     * returns the habitat
     * @return habitat of Fauna
     */
    public TileType getHabitat() {
        return habitat;
    }
    
    /**
     * returns animal name based on habitat and size
     * @returns name of animal
     */
    @Override
    public String getName() {
        if (habitat == TileType.LAND) {
            if (super.getSize() == Size.SMALL) {
                return "Mouse"; 
            } else if (super.getSize() == Size.MEDIUM) {
                return "Dog"; 
            } else if (super.getSize() == Size.LARGE) {
                return "Horse"; 
            } else if (super.getSize() == Size.GIANT) {
                return "Elephant"; 
            } else {
                return "none"; 
            }
        } else if (habitat == TileType.OCEAN) {
            if (super.getSize() == Size.SMALL) {
                return "Crab"; 
            } else if (super.getSize() == Size.MEDIUM) {
                return "Fish"; 
            } else if (super.getSize() == Size.LARGE) {
                return "Shark"; 
            } else if (super.getSize() == Size.GIANT) {
                return "Whale"; 
            }
        }
        return null;
    }
    
    /**
     * human readable form of Fauna
     * @returns readable form of class Fauna
     */
    @Override
    public String toString() {
        return getName() + " [Fauna]" + " at " + getCoordinate();
    }
}
