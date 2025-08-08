package researchsim.map;

import researchsim.scenario.Scenario;
import researchsim.scenario.ScenarioManager;
import researchsim.util.*;


/**
 * A coordinate is a representation of the  X and Y positions on a graphical map.<br>
 * This X, Y position can be used to calculate the index of a Tile in the scenario tile map
 * depending on the currently active scenario. <br>
 * The X and Y positions will not change but the index will depending on the current scenario.
 * <p>
 * A coordinate is similar to a point on the cartesian plane.
 * <p>
 * NOTE: Some methods in this class require interaction with the {@link ScenarioManager}. Only
 * interact with it when you need it.
 *
 * @ass1_partial
 * @ass1_test_partial
 */
public class Coordinate {

    /**
     * The position in the Horizontal plane (Left-Right).
     */
    private final int xcoord;

    /**
     * The position in the Vertical plane (Up-Down).
     */
    private final int ycoord;

    /**
     * Creates a new coordinate at the top left position (0,0), index 0 (zero).
     *
     * @ass1
     */
    public Coordinate() {
        this(0, 0);
    }

    /**
     * Creates a new coordinate at the specified (x,y) position.
     *
     * @param xcoord horizontal position
     * @param ycoord vertical position
     * @ass1
     */
    public Coordinate(int xcoord, int ycoord) {
        this.xcoord = xcoord;
        this.ycoord = ycoord;
    }

    /**
     * Creates a new coordinate at the specified index.
     *
     * @param index index in the tile grid
     * @ass1
     */
    public Coordinate(int index) {
        int width = ScenarioManager.getInstance().getScenario().getWidth();
        this.xcoord = index % width;
        this.ycoord = index / width;
    }

    /**
     * The position in the Horizontal plane (Left-Right)
     *
     * @return the horizontal position
     * @ass1
     */
    public int getX() {
        return xcoord;
    }

    /**
     * The position in the Vertical plane (Up-Down)
     *
     * @return the vertical position
     * @ass1
     */
    public int getY() {
        return ycoord;
    }

    /**
     * The index in the tile grid of this coordinate.
     *
     * @return the grid index
     * @ass1
     */
    public int getIndex() {
        return Coordinate.convert(xcoord, ycoord);
    }

    /**
     * Determines if the coordinate in the bounds of the current scenario map
     *
     * @return true, if 0 &le; coordinate's x position &lt; current scenarios' width AND 0 &le;
     * coordinate's y position &lt; current scenarios' height
     * else, false
     * @ass1
     */
    public boolean isInBounds() {
        Scenario scenario = ScenarioManager.getInstance().getScenario();
        return xcoord < scenario.getWidth() && xcoord >= 0
            && ycoord < scenario.getHeight() && ycoord >= 0;
    }

    /**
     * Utility method to convert an (x,y) integer pair to an array index location.
     *
     * @param xcoord the x portion of a coordinate
     * @param ycoord the y portion of a coordinate
     * @return the converted index
     * @ass1
     */
    public static int convert(int xcoord, int ycoord) {
        return xcoord + ycoord * ScenarioManager.getInstance().getScenario().getWidth();
    }

    /**
     * Returns the human-readable string representation of this Coordinate.
     * <p>
     * The format of the string to return is:
     * <pre>(x,y)</pre>
     * Where:
     * <ul>
     *   <li>{@code x} is the position in the Horizontal plane (Left-Right)</li>
     *   <li>{@code y} is the position in the Vertical plane (Up-Down)</li>
     * </ul>
     * For example:
     *
     * <pre>(1,3)</pre>
     *
     * @return human-readable string representation of this Coordinate.
     * @ass1
     */
    @Override
    public String toString() {
        return String.format("(%d,%d)",
            this.xcoord, this.ycoord);
    }

    /**
     * gives the absolute value of xcoord
     * @return the absolute value of xcoord
     */
    public int getAbsX() {
        return Math.abs(xcoord);
    }

    /**
     * gives the absolute value of ycoord
     * @return the absolute value of ycoord
     */
    public int getAbsY() {
        return Math.abs(ycoord);
    }

    /**
     * returns a new coordinate based on the encoded string given
     * the String given should match the format of an encoded Coordinate
     * Format of encoded coordinate should be x,y
     * @param encoded the encoded String matching the format of an encoded coordinate
     * @return the new coordinate 
     * @throws BadSaveException if x or y cant be parsed as integers or improper format
     */
    public static Coordinate decode(String encoded) throws BadSaveException {
        try {
            String[] splitString = encoded.split(",", -1);
            if (splitString.length > 2) {
                throw new BadSaveException();
            }
            //creates array containing x and y values. Only works if encoded formatted correctly
            int x = Integer.parseInt(splitString[0]);
            int y = Integer.parseInt(splitString[1]);
            Coordinate coordinate = new Coordinate(x, y);
            return coordinate;
        } catch (Exception e) {
            throw new BadSaveException();
        }
    }

    /**
     * returns a new coordinate based on the distance between the current coordinate
     * and the other coordinate. 
     * result X = other X - this X and  result Y = other Y - this Y
     * @param other other coordinate to find the distance between
     * @return coordinate containing distance between the two coordinates.
     */
    public Coordinate distance(Coordinate other) {
        int x = other.getX() - xcoord;
        int y = other.getY() - ycoord;
        Coordinate coordinate = new Coordinate(x, y);
        return coordinate;
    }

    /**
     * translates a coordinate based on the given x and y values 
     * example : new Coordinate(0,-1).translate(1,3) == new Coordinate(1,2)
     * @param x the value to move the x value by 
     * @param y the value to move the y value by
     * @return coordinate thats been translated according to given x and y values
     */
    public Coordinate translate(int x, int y) {
        int xvalue = xcoord + x;
        int yvalue = ycoord + y;
        Coordinate coordinate = new Coordinate(xvalue, yvalue);
        return coordinate;
    }

    /**
     * returns the machine readable form of Coordinate
     * format is x,y
     * @return machine readable form of coordinate
     */
    public String encode() {
        String encoded = Integer.toString(getX()) + "," + Integer.toString(getY());
        return encoded;
    }

    /**
     * returns a hash value by multiplying hash values of x and y together
     * @return hashValue of coordinate
     */
    public int hashCode() {
        int x = (int) Math.pow(2, Integer.hashCode(getX()));
        int y = (int) Math.pow(3, Integer.hashCode(getY()));
        // 2 and 3 are raised to the power of x and y hash respectively this is to make sure
        // coordinates like (3,4) won't equal coordinates like (4,3)
        //reason for using numbers 2 and 3 are because they are primes
        return x * y;
    }

    /**
     * Compares this coordinate and other Object to check if they are equal
     * objects are equal if the x coord is equal and y coord is equal 
     * @param other Object being compared to this coordinate
     * @return true iff hashcodes are equal and both objects are coordinates
     */
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
