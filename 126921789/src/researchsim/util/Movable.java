package researchsim.util;

import researchsim.entities.Size;
import researchsim.logging.MoveEvent;
import researchsim.map.Coordinate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Denotes a specific type of entity that can move around in the simulation.
 * <p>
 * <b>NOTE:</b> <br> Read the documentation for these methods well.
 * This is one of the harder parts of A2.
 * <br> It is recommended that you create some private helper methods to assist with these
 * functions. <br> Some example methods might be: <br> checkTile - see if a Tile can be moved to
 * <br> checkTraversal - see if all tiles along a path can be moved to
 *
 * @ass2
 */
public interface Movable {

    /**
     * returns a list of all possible tiles entity can move to
     * list based on checkRange and canMove to find all legal tiles that can be moved to
     * @return list of coordinates that can be moved to
     */
    List<Coordinate> getPossibleMoves();

    /**
     * moves entity from its current coordinate to target coordinate
     * creates move event and adds it to current scenario logger
     * @param coordinate the coordinate entity is moving to
     */
    void move(Coordinate coordinate);

    /**
     * Determines if entity can move to a specified coordinate based on certain conditions
     * coordinate is on scenario map
     * the distance between the two coordinates is not greater than the entity can move
     * the target coordinate tile is not uninhabitable for the entity
     * the tile is not occupied
     * the entity can move to all tiles on the path to the target tile
     * @param coordinate the target coordinate to move to
     * @return True iff the entity can move to that coordinate
     * @throws CoordinateOutOfBoundsException if given coordinate is out of bounds
     */
    boolean canMove(Coordinate coordinate) throws CoordinateOutOfBoundsException;

    /**
     * Finds all tiles that can be moved to based on the starting Coordinate and the radius
     * Returns all tiles that can be moved to, doesnt matter if tile is out of bounds
     * @param radius the distance that can be travelled
     * @param initialCoordinate the starting coordinate
     * @return list of coordinates that can be moved to based on radius and intialCoordinate
     */
    default List<Coordinate> checkRange(int radius, Coordinate initialCoordinate) {
        ArrayList<Coordinate> coordinates = new ArrayList<Coordinate>();
        for (int y = (radius * -1); y <= radius; y++) {
            for (int x = (radius * -1); x <= radius; x++) {
                coordinates.add(initialCoordinate.translate(x, y));
                //This will loop to add all coordinates in a (radius*2)+1 grid to the list
                //coordinates that cant be moved to will be taken out
            }
        }
        ArrayList<Coordinate> moves = new ArrayList<>();
        moves.addAll(coordinates);
        for (Coordinate coordinate : coordinates) {
            int x = coordinate.distance(initialCoordinate).getAbsX();
            int y = coordinate.distance(initialCoordinate).getAbsY();
            if (x + y > radius) {
                moves.remove(coordinate);
                //Removes coordinate if moving to it takes more moves then radium given
            }
        }
        return moves;
    }
    
    
}
