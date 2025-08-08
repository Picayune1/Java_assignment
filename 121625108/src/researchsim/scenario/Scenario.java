package researchsim.scenario;

import researchsim.map.*;
import junit.framework.*;

/**
 * Scenario represents the situation, containing the grid for that scenario
 */
public class Scenario extends Object {
    /**
     * minimum size for height or width of grid
     */
    public static final int MIN_SIZE = 5;
    /**
     * max size for height or width of grid
     */
    public static final int MAX_SIZE = 15;
    /**
     * max tiles that can be stored in grid
     */
    public static final int MAX_TILES = 225;
    /**
     * array of tiles representing the grid
     */
    private Tile[] grid;
    /**
     * width of grid
     */
    private int width;
    /**
     * height of grid
     */
    private int height;
    /**
     * name of scenario
     */
    private String name;

    /**
     * Constructor for scenario
     * @param name name of scenario
     * @param width width of grid
     * @param height height of grid
     * @throws IllegalArgumentException width or height above max size or below min size
     */
    public Scenario(String name, int width, int height) throws IllegalArgumentException {
        if (width < MIN_SIZE || height < MIN_SIZE) {
            throw new IllegalArgumentException();
        } else if (width > MAX_SIZE || height > MAX_SIZE) {
            throw new IllegalArgumentException();
        } else if (name == null) {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.width = width;
        this.height = height;
        Tile[] arr = new Tile[width * height];
        this.grid = arr;
    }

    /**
     * return the name of scenario
     * @return name of scenario
     */
    public String getName() {
        return name;
    }

    /**
     * return the grid of scenario
     * @return a copy of the grid of scenario
     */
    public Tile[] getMapGrid() {
        Tile[] newGrid = this.grid.clone();
        return newGrid;
    }

    /**
     * set grid to new map
     * @param map new array of tiles to set grid to
     */
    public void setMapGrid(Tile[] map) {
        Tile[] newMap = map.clone();
        this.grid = newMap;
    }

    /**
     * return the width of grid
     * @return width of grid
     */
    public int getWidth() {
        return width;
    }

    /**
     * return the height of grid
     * @return height of grid
     */
    public int getHeight() {
        return height;
    }

    /**
     * return the size of a grid
     * @return the size of a grid
     */
    public int getSize() {
        int size = grid.length;
        return size;
    }
    
    /**
     * return human-readable scenario
     * @return String of scenario
     */
    @Override
    public String toString() {
        int entity = 0;
        String text = getName() + System.lineSeparator() + "Width: " + getWidth() + ", Height: ";
        text = text + getWidth() + System.lineSeparator();
        for (Tile tile : grid) {
            if (tile.hasContents()) {
                entity = entity + 1;
            }
        }
        String finalText = text + "Entities: " + entity;
        return finalText;
    }
}
