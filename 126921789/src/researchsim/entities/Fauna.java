package researchsim.entities;

import researchsim.logging.CollectEvent;
import researchsim.logging.MoveEvent;
import researchsim.map.Coordinate;
import researchsim.map.Tile;
import researchsim.map.TileType;
import researchsim.scenario.ScenarioManager;
import researchsim.util.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Fauna is all the animal life present in a particular region or time.
 * Fauna can move around the scenario and be collected by the {@link User}.
 * <p>
 * NOTE: Some methods in this class require interaction with the {@link ScenarioManager}. Only
 * interact with it when you need it.
 *
 * @ass1_partial
 * @ass1_test
 */
public class Fauna extends Entity implements Movable, Collectable {

    /**
     * The habitat associated with the animal.
     * That is, what tiles an animal can exist in.
     */
    private final TileType habitat;

    /**
     * Creates a fauna (Animal) with a given size, coordinate and habitat.
     *
     * @param size       size associated with the animal
     * @param coordinate coordinate associated with the animal
     * @param habitat    habitat tiles associated with the animal
     * @throws IllegalArgumentException if habitat is not {@link TileType#LAND} or
     *                                  {@link TileType#OCEAN}
     * @ass1
     */
    public Fauna(Size size, Coordinate coordinate, TileType habitat)
        throws IllegalArgumentException {
        super(size, coordinate);
        if (habitat != TileType.LAND && habitat != TileType.OCEAN) {
            throw new IllegalArgumentException("Animal was created with a bad habitat: " + habitat);
        }
        this.habitat = habitat;
    }

    /**
     * Returns the animal's habitat.
     *
     * @return animal's habitat
     * @ass1
     */
    public TileType getHabitat() {
        return habitat;
    }

    /**
     * Returns the human-readable name of this animal.
     * The name is determined by the following table.
     * <p>
     * <table border="1">
     *     <caption>Human-readable names</caption>
     *     <tr>
     *         <td rowspan="2" colspan="2" style="background-color:#808080">&nbsp;</td>
     *         <td colspan="3">Habitat</td>
     *     </tr>
     *     <tr>
     *         <td>LAND</td>
     *         <td>OCEAN</td>
     *     </tr>
     *     <tr>
     *         <td rowspan="4">Size</td>
     *         <td>SMALL</td>
     *         <td>Mouse</td>
     *         <td>Crab</td>
     *     </tr>
     *     <tr>
     *         <td>MEDIUM</td>
     *         <td>Dog</td>
     *         <td>Fish</td>
     *     </tr>
     *     <tr>
     *         <td>LARGE</td>
     *         <td>Horse</td>
     *         <td>Shark</td>
     *     </tr>
     *     <tr>
     *         <td>GIANT</td>
     *         <td>Elephant</td>
     *         <td>Whale</td>
     *     </tr>
     * </table>
     * <p>
     * e.g. if this animal is {@code MEDIUM} in size and has a habitat of {@code LAND} then its
     * name would be {@code "Dog"}
     *
     * @return human-readable name
     * @ass1
     */
    @Override
    public String getName() {
        String name;
        switch (getSize()) {
            case SMALL:
                name = habitat == TileType.LAND ? "Mouse" : "Crab";
                break;
            case MEDIUM:
                name = habitat == TileType.LAND ? "Dog" : "Fish";
                break;
            case LARGE:
                name = habitat == TileType.LAND ? "Horse" : "Shark";
                break;
            case GIANT:
            default:
                name = habitat == TileType.LAND ? "Elephant" : "Whale";
        }
        return name;
    }

    /**
     * Returns the human-readable string representation of this animal.
     * <p>
     * The format of the string to return is:
     * <pre>name [Fauna] at coordinate [habitat]</pre>
     * Where:
     * <ul>
     *   <li>{@code name} is the animal's human-readable name according to {@link #getName()}</li>
     *   <li>{@code coordinate} is the animal's associated coordinate in human-readable form</li>
     *   <li>{@code habitat} is the animal's associated habitat</li>
     *
     * </ul>
     * For example:
     *
     * <pre>Dog [Fauna] at (2,5) [LAND]</pre>
     *
     * @return human-readable string representation of this animal
     * @ass1
     */
    @Override
    public String toString() {
        return String.format("%s [%s]",
            super.toString(),
            this.habitat);
    }

    /**
     * returns the hash value of the Fauna instance
     * creates hash by hashing size, coordinate and habitat and multiplying them 
     * Hash is then size, coordinate and habitat dependent
     * @return the hash value of the Fauna
     */
    @Override
    public int hashCode() {
        return getSize().hashCode() * getCoordinate().hashCode() * this.habitat.hashCode();
    }

    /**
     * compares Fauna and another object to check if they are equal.
     * equal if size, coordinate and habitat are the same and both are Fauna objects
     * @param other Object to compare to current Fauna
     * @return true iff size, coordinate and habitat are the same and both are Fauna objects
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() == this.getClass()
                && other.hashCode() == this.hashCode()) {
            //Using hashcode to check if they have the same habitat,size and coordinate
            return true;
        }
        return false;
    }

    /**
     * returns the machine-readable representation of the Fauna class
     * format is Fauna-size-coordinate-habitat
     * @return machine readable fauna object
     */
    @Override
    public String encode() {
        String name = super.encode();
        return name + getHabitat().toString();
    }

    /**
     * returns the possible moves of the fauna given its size and if it can move there
     * uses checkRange to find all possible coordinates that can be moved to given its size
     * Then uses canMove to see which of those coordinates it can move to
     * Any CoordinateOutOfBoundsExceptions thrown are ignored
     * @return list of all possible moves
     */
    @Override
    public List<Coordinate> getPossibleMoves() {
        List<Coordinate> possibleMoves = new ArrayList<>();
        List<Coordinate> moves = checkRange(getSize().moveDistance, getCoordinate());
        for (Coordinate coordinate : moves) {
            try {
                if (canMove(coordinate)) {
                    possibleMoves.add(coordinate);
                }
            } catch (CoordinateOutOfBoundsException e) {
                ;
            }
        }
        return possibleMoves;
    }

    /**
     * Determines if an animal can move to the new coordinate only if all
     * these conditions are true.
     * The new coordinate must be different from the current coordinate.
     * The coordinate given is on the current scenario map.
     * The distance between coordinate and the animals current coordinate
     * is smaller or equal to animals move range.
     * If the animal's habitat is OCEAN then the tile at the
     * coordinate must be OCEAN.
     * If the animal's habitat is LAND then the tile at the
     * coordinate must NOT be OCEAN.
     * The tile at the coordinate is not already occupied.
     * The animal has an unimpeded path for each tile it must traverse to
     * reach the destination coordinate.
     * @param coordinate the target coordinate to move to
     * @return True iff all the conditions above are true
     * @throws CoordinateOutOfBoundsException if given coordinate
     * is out of bounds for current scenario
     */
    public boolean canMove(Coordinate coordinate) throws CoordinateOutOfBoundsException {

        ScenarioManager scenarioManager = ScenarioManager.getInstance();
        int absX = coordinate.distance(getCoordinate()).getAbsX();
        int absY = coordinate.distance(getCoordinate()).getAbsY();
        int distance = absX + absY;

        if (!coordinate.isInBounds()) {
            throw new CoordinateOutOfBoundsException();
        } else {
            Tile tile = scenarioManager.getScenario().getMapGrid()[coordinate.getIndex()];
            if (coordinate.equals(getCoordinate())) {
                return false;
            } else if (distance > getSize().moveDistance) {
                return false;
            } else if (getHabitat() == TileType.OCEAN && tile.getType() != TileType.OCEAN) {
                return false;
            } else if (getHabitat() == TileType.LAND && tile.getType() == TileType.OCEAN) {
                return false;
            } else if (tile.hasContents()) {
                return false;
            }
        }

        //Base step, if distance is 1 and all conditions are true, then can move to
        if (distance == 1) {
            return true;
        }
        if (getCoordinate().getY() == coordinate.getY()
                && getCoordinate().getX() > coordinate.getX()) {
            try {
                return canMove(coordinate.translate(1, 0));
            } catch (CoordinateOutOfBoundsException e) {
                return false;
            }

        } else if (getCoordinate().getY() == coordinate.getY()
                && getCoordinate().getX() < coordinate.getX()) {
            try {
                return canMove(coordinate.translate(-1, 0));
            } catch (CoordinateOutOfBoundsException e) {
                return false;
            }

        } else if (getCoordinate().getX() == coordinate.getX()
                && getCoordinate().getY() < coordinate.getY()) {
            try {
                return canMove(coordinate.translate(0, -1));
            } catch (CoordinateOutOfBoundsException e) {
                return false;
            }
        } else if (getCoordinate().getX() == coordinate.getX()
                && getCoordinate().getY() > coordinate.getY()) {
            try {
                return (canMove(coordinate.translate(0, 1)));
            } catch (CoordinateOutOfBoundsException e) {
                return false;
            }
        } else {
            Coordinate coordinate1 = new Coordinate(getCoordinate().getX(), getCoordinate().getY());
            int x = coordinate.distance(getCoordinate()).getX();
            int y = coordinate.distance(getCoordinate()).getY();
            Coordinate coordinate2 = coordinate.translate(0, y);
            Coordinate coordinate3 = coordinate.translate(x, 0);
            setCoordinate(coordinate2);
            if (canMove(coordinate)) {
                setCoordinate(coordinate1);
                if (canMove(coordinate2)) {
                    return true;
                }
            }
            setCoordinate(coordinate3);
            if (canMove(coordinate)) {
                setCoordinate(coordinate1);
                if (canMove(coordinate3)) {
                    return true;
                }
            }
            setCoordinate(coordinate1);
            return false;
        }
    }

    /**
     * Moves the animal to the given coordinate assuming canMove(coordinate) is true
     * The tile the animal moves to is occupied by the animal and the tile
     * The animal leaves is no longer occupied
     * A moveEvent is created and added to the current scenario logger
     * @requires canMove(coordinate) == True
     * @param coordinate the coordinate entity is moving to
     */
    @Override
    public void move(Coordinate coordinate) {
        ScenarioManager scenarioManager = ScenarioManager.getInstance();
        final MoveEvent moveEvent = new MoveEvent(this, coordinate);
        Tile oldTile = scenarioManager.getScenario().getMapGrid()[getCoordinate().getIndex()];
        oldTile.setContents(null);
        setCoordinate(coordinate);
        Tile newTile = scenarioManager.getScenario().getMapGrid()[coordinate.getIndex()];
        newTile.setContents(this);
        scenarioManager.getScenario().getLog().add(moveEvent);

    }

    /**
     * the animal is collected by user and returns points based on animals size
     * a collect event is created and added to current scenario log
     * animal is removed from scenario grid map and scenario animalController
     * @param user the user that is collecting other entities
     * @return points based on Fauna size
     */
    public int collect(User user) {
        ScenarioManager scenarioManager = ScenarioManager.getInstance();
        CollectEvent collectEvent = new CollectEvent(user, this);
        scenarioManager.getScenario().getLog().add(collectEvent);
        Tile oldTile = scenarioManager.getScenario().getMapGrid()[getCoordinate().getIndex()];
        oldTile.setContents(null);
        scenarioManager.getScenario().getController().removeAnimal(this);
        return getSize().points;
    }
}
