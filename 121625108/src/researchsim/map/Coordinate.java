package researchsim.map;

import researchsim.scenario.ScenarioManager;

/**
 * represents the Coordinates on the grid
 */
public class Coordinate extends Object {
    /**
     * xcoord is the x value coordinate
     */
    private int xcoord;
    /**
     * ycoord is the y value of coordinate
     */
    private int ycoord;
    /**
     * Scenariomanager instance grabbed to do methods like inBounds
     */
    private static ScenarioManager scenariomanager = ScenarioManager.getInstance();

    /**
     * default creation of coordinate with no parameters
     * gives default value of (0,0)
     */
    public Coordinate() {
        this.xcoord = 0;
        this.ycoord = 0;
    }

    /**
     * Creates Coordinate with given x and y values
     * @param xcoord x value for coordinate
     * @param ycoord y value for coordinate
     */
    public Coordinate(int xcoord, int ycoord) {
        this.xcoord = xcoord;
        this.ycoord = ycoord;
    }

    /**
     * creates coordinate based on index and current grid dimensions
     * @param index the index on the grid
     */
    public Coordinate(int index) {
        int width = scenariomanager.getScenario().getWidth();
        int height = scenariomanager.getScenario().getHeight();
        xcoord = index % width;
        double y = Math.floor(index / height);
        ycoord = (int) y;
    }

    /**
     * returns the x value of coordinate
     * @return x value of coordinate
     */
    public int getX() {
        return xcoord;
    }
    
    /**
     * returns y value of coordinate
     * @return y value of coordinate
     */
    public int getY() {
        return ycoord;
    }    

    /**
     * returns index based on current gird dimensions and x and y values
     * @return index of current x and y value
     */
    public int getIndex() {
        int width = scenariomanager.getScenario().getWidth();
        return xcoord + (ycoord * width);
    }

    /**
     * finds out if coordinate is in bounds based on grid size
     * @return true iff the coordinate is in bounds
     */
    public boolean isInBounds() {
        int width = scenariomanager.getScenario().getWidth();
        int height = scenariomanager.getScenario().getHeight();
        if (xcoord < 0 || xcoord > width) {
            return false;
        } else if (ycoord < 0 || ycoord > height) {
            return false;
        } else {
            return true;
        }    
    }

    /**
     * returns the index based on the given y and x
     * @param xcoord the x value of the coordinate
     * @param ycoord the y value of the coordinate
     * @return the index of the coordinate
     */
    public static int convert(int xcoord, int ycoord) {
        int width = scenariomanager.getScenario().getWidth();
        return xcoord + (ycoord * width);
    }


    /**
     * returns readable form of coordinate
     * @returns coordinate in String readable form
     */
    @Override
    public String toString() {
        return "(" + getX() + "," + getY() + ")";
    }    
}
