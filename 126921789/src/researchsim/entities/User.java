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
import java.util.Objects;

/**
 * User is the player controlled character in the simulation.
 * A user can {@code collect} any class that implements the {@link researchsim.util.Collectable}
 * interface.
 * <p>
 * NOTE: Some methods in this class require interaction with the {@link ScenarioManager}. Only
 * interact with it when you need it.
 *
 * @ass2
 * @ass2_test
 */
public class User extends Entity implements Movable, Encodable   {

    /**
     * name of the user
     */
    private String name;

    /**
     * Creates a new user. The user's size is always MEDIUM
     * @param coordinate the coordinate the user is created at
     * @param name the name of the user
     */
    public User(Coordinate coordinate, String name) {
        super(Size.MEDIUM, coordinate);
        this.name = name;
    }

    /**
     * returns the name of the user
     * @return the name of the user
     */
    public String getName() {
        return this.name;
    }

    /**
     * returns the machine-readable name of user in the format :
     * User-coordinate-name
     * @return Machine-readable name
     */
    public String encode() {
        String text = getClass().getSimpleName() + "-" + getCoordinate().encode() + "-";
        text = text + getName();
        return text;
    }

    /**
     * Checks if this and given object are equal
     * @param other other Object being compared to this user
     * @return true iff the given Object has the same hashcode and both objects are users
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

    /**
     * returns the hashcode of user. Hashcode based on user's name and coordinate
     * @return hashcode for user
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name);
    }

    /**
     * Determines if a user can move to the new coordinate only if all these conditions are true
     * The new coordinate must be different from the current coordinate.
     * The coordinate given is on the current scenario map.
     * The distance between coordinate and the users current coordinate not greater than 34
     * The tile at the coordinate is NOT OCEAN or MOUNTAIN
     * The user has an unimpeded path for each tile it must traverse
     * to reach the destination coordinate
     * @param coordinate the target coordinate to move to
     * @return True iff all the conditions above are true
     * @throws CoordinateOutOfBoundsException if given coordinate is out of bounds
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
            if (coordinate == getCoordinate()) {
                return false;
            } else if (distance > 4) { // 4 comes from javadoc specification
                return false;
            } else if (tile.getType() == TileType.OCEAN | tile.getType() == TileType.MOUNTAIN) {
                return false;
            }
        }
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
     * returns the possible moves of the User given its size and if it can move there
     * uses checkRange to find all possible coordinates that can be moved to given its size
     * Then uses canMove to see which of those coordinates it can move to.
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
                assert true;
            }
        }
        return possibleMoves;
    }

    /**
     * Moves the user to the coordinate in the parameter.
     * sets user coordinate to new coordinate and sets tile contents at old coordinate to null
     * if entity  that implements collectable at coordinate then collects it
     * @param coordinate the coordinate entity is moving to
     * @requires canMove == true
     */
    @Override
    public void move(Coordinate coordinate) {
        ScenarioManager scenarioManager = ScenarioManager.getInstance();
        MoveEvent moveEvent = new MoveEvent(this, coordinate);
        Tile oldTile = scenarioManager.getScenario().getMapGrid()[getCoordinate().getIndex()];
        oldTile.setContents(null);
        setCoordinate(coordinate);
        Tile newTile = scenarioManager.getScenario().getMapGrid()[coordinate.getIndex()];
        scenarioManager.getScenario().getLog().add(moveEvent);
        try {
            if (newTile.getContents() instanceof Collectable) {
                collect(coordinate);
                newTile.setContents(this);
            }
        } catch (NoSuchEntityException e) {
            newTile.setContents(this); //If theirs no entity at the point then just move user
        } catch (CoordinateOutOfBoundsException e) {
            assert true;
        } // ignore error thrown by collect specified in JavaDoc
    }

    /**
     * returns a list of possible coordinates the user can collect from.
     * User has a collect range of 1.
     * Can only collect things that implement the interface collectable.
     * @return list of coordinates that User can collect from
     */
    public List<Coordinate> getPossibleCollection() {
        ScenarioManager scenarioManager = ScenarioManager.getInstance();
        List<Coordinate> coordinates = checkRange(1, getCoordinate());
        List<Coordinate> possibleCollection = new ArrayList<>();
        for (Coordinate coordinate : coordinates) {
            Tile tile = scenarioManager.getScenario().getMapGrid()[coordinate.getIndex()];
            try {
                if (coordinate.isInBounds() && tile.getContents() instanceof Collectable) {
                    possibleCollection.add(coordinate);
                }
            } catch (NoSuchEntityException e) {
                ;
            }
        }
        return possibleCollection;
    }


    /**
     * collect takes the entity at given coordinate and collects it
     * If entity at coordinate doesn't implement collectable then ignores it
     * the entity at the coordinate is removed from the mapgrid for the scenario
     * collect event is then added to the log
     * @param coordinate coordinate to collect from
     * @throws NoSuchEntityException if no entity at given coordinate
     * @throws CoordinateOutOfBoundsException if given coordinate out of bounds
     */
    public void collect(Coordinate coordinate)
            throws NoSuchEntityException, CoordinateOutOfBoundsException {
        if (!coordinate.isInBounds()) {
            throw new CoordinateOutOfBoundsException();
        }
        ScenarioManager scenarioManager = ScenarioManager.getInstance();
        Tile tile = scenarioManager.getScenario().getMapGrid()[coordinate.getIndex()];
        if (!tile.hasContents()) {
            throw new NoSuchEntityException();
        }
        if (tile.getContents() instanceof Collectable) {
            if (tile.getContents() instanceof Flora) {
                Flora flora = new Flora(tile.getContents().getSize(),
                        tile.getContents().getCoordinate());
                tile.setContents(flora);
                flora.collect(this);
            } else if (tile.getContents() instanceof  Fauna) {
                TileType habitat;
                if (tile.getType() == TileType.OCEAN) {
                    habitat = TileType.OCEAN;
                } else {
                    habitat = TileType.LAND;
                }
                Fauna fauna = new Fauna(tile.getContents().getSize(),
                        tile.getContents().getCoordinate(), habitat);
                tile.setContents(fauna);
                fauna.collect(this);
            }
        }
    }
}
