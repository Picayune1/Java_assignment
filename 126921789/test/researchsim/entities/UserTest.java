package researchsim.entities;

import org.junit.Test;
import researchsim.map.Coordinate;
import researchsim.map.Tile;
import researchsim.map.TileType;
import researchsim.scenario.Scenario;
import researchsim.scenario.ScenarioManager;
import researchsim.util.BadSaveException;
import researchsim.util.CoordinateOutOfBoundsException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UserTest {

    /**
     * Creates a new scenario and adds it to the scenario manager.
     * The scenario created has a 5x5 map of LAND. A Seed of 0.
     *
     * @param name of the scenario
     * @return generated scenario
     * @see #createSafeTestScenario(String, TileType[])
     */
    public static Scenario createSafeTestScenario(String name) {
        return createSafeTestScenario(name, new TileType[] {
            TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND,
            TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND,
            TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND,
            TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND,
            TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND
        });
    }

    /**
     * Creates a new scenario and adds it to the scenario manager.
     * The scenario created has a 5x5 map with the array of tiles based on the array provided. A
     * Seed of 0.
     *
     * @param name  of the scenario
     * @param tiles the map of the scenario
     * @return generated scenario
     * @see #createSafeTestScenario(String, TileType[], int, int)
     */
    public static Scenario createSafeTestScenario(String name, TileType[] tiles) {
        return createSafeTestScenario(name, tiles, 5, 5);
    }

    /**
     * Creates a new scenario and adds it to the scenario manager.
     * The scenario created has an n x m map with the array of LAND tiles. A
     * Seed of 0.
     *
     * @param name  of the scenario
     * @param width  the width of the scenario
     * @param height the height of the scenario
     * @return generated scenario
     * @see #createSafeTestScenario(String, TileType[], int, int)
     */
    public static Scenario createSafeTestScenario(String name, int width, int height) {
        int size = width * height;
        TileType[] tiles = new TileType[size];
        Arrays.fill(tiles,0,size,TileType.LAND);
        return createSafeTestScenario(name, tiles, width, height);
    }

    /**
     * Creates a new scenario and adds it to the scenario manager.
     * The scenario created has a n x m map with the array of tiles based on the array provided. A
     * Seed of 0.
     *
     * @param name   of the scenario
     * @param tiles  the map of the scenario
     * @param width  the width of the scenario
     * @param height the height of the scenario
     * @return generated scenario
     */
    public static Scenario createSafeTestScenario(String name, TileType[] tiles,
                                                  int width, int height) {
        Scenario s = new Scenario(name, width, height, 0);
        Tile[] map = Arrays.stream(tiles).map(Tile::new).toArray(Tile[]::new);
        try {
            s.setMapGrid(map);
        } catch (CoordinateOutOfBoundsException error) {
            fail("Failed to update a scenario map for test: " + name + "\n "
                + error.getMessage());
        }
        ScenarioManager.getInstance().addScenario(s);
        try {
            ScenarioManager.getInstance().setScenario(name);
        } catch (BadSaveException error) {
            fail("Failed to update a scenario map for test: " + name + "\n "
                + error.getMessage());
        }
        return s;
    }

    Coordinate coordinate = new Coordinate();
    Coordinate coordinate2 = new Coordinate(3,4);
    Coordinate coordinate4 = new Coordinate(4,3);
    User user1 = new User(coordinate, "Phil");
    User user2 = new User(coordinate2, "Henry");
    User user4 = new User(coordinate2, null);
    User user1Dup = new User(coordinate, "Phil");
    User user5 = new User(coordinate4, "Henry");
    ScenarioManager scenarioManager = ScenarioManager.getInstance();

    TileType[] tiles = new TileType[] {
            TileType.OCEAN, TileType.SAND, TileType.SAND, TileType.OCEAN, TileType.OCEAN,
            TileType.OCEAN, TileType.OCEAN, TileType.SAND, TileType.LAND, TileType.OCEAN,
            TileType.LAND, TileType.LAND, TileType.LAND, TileType.MOUNTAIN, TileType.LAND,
            TileType.LAND, TileType.OCEAN, TileType.OCEAN, TileType.LAND, TileType.OCEAN,
            TileType.OCEAN, TileType.OCEAN, TileType.OCEAN, TileType.OCEAN, TileType.OCEAN,};

    TileType[] tiles2 = new TileType[] {
            TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND,
            TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND,
            TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND,
            TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND,
            TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND, TileType.LAND,};

    @Test
    public void testGetName() {

        assertEquals("Phil", user1.getName());
        assertEquals("Henry", user2.getName());
        assertEquals(null, user4.getName());
    }

    @Test
    public void testEncode() {
        Scenario scenario = createSafeTestScenario("Gh");
        scenarioManager.addScenario(scenario);
        Coordinate coordinate3 = new Coordinate(12);
        User user3 = new User(coordinate3, "O");
        assertEquals("User-3,4-Henry", user2.encode());
        assertEquals("User-0,0-Phil", user1.encode());
        assertEquals("User-2,2-O", user3.encode());
    }

    @Test
    public void testHashCode() {
        User user6 = new User(coordinate, "Harold");
        User user7 = new User(coordinate, "Mo");
        assertEquals(user1.hashCode(),user1Dup.hashCode());
        assertNotEquals(user1.hashCode(),user2.hashCode());
        assertNotEquals(user2.hashCode(), user5.hashCode());
        assertNotEquals(user6.hashCode(), user2.hashCode());
        assertNotEquals(user6.hashCode(),user7.hashCode());
    }

    @Test
    public void testGetPossibleMoves() {
        createSafeTestScenario("j", tiles, 5, 5);
        List<Coordinate> user1Moves = List.of(new Coordinate(1,0), new Coordinate(2,0),
         new Coordinate(2,1));
        assertTrue(user1Moves.containsAll(user1.getPossibleMoves()) &&
                user1.getPossibleMoves().containsAll(user1Moves));

        List<Coordinate> user2Moves = List.of(new Coordinate(3,3));
        assertTrue(user2Moves.containsAll(user2.getPossibleMoves()) &&
            user2.getPossibleMoves().containsAll(user2Moves));

    }

    @Test
    public void testMove() {
        createSafeTestScenario("l", tiles2, 5, 5);
        ScenarioManager scenarioManager =  ScenarioManager.getInstance();
        user1.move(coordinate2);
        assertFalse(scenarioManager.getScenario().getMapGrid()[0].hasContents());
        assertTrue(scenarioManager.getScenario().getMapGrid()[coordinate2.getIndex()].hasContents());
        assertTrue(scenarioManager.getScenario().getLog().getEvents().size() == 1);
        assertTrue(scenarioManager.getScenario().getLog().getTilesTraversed() == 7);

    }

    @Test
    public void testCollectAndMove() {
        createSafeTestScenario("l", tiles2, 5, 5);
        ScenarioManager scenarioManager =  ScenarioManager.getInstance();
        Fauna fauna = new Fauna(Size.MEDIUM,coordinate2,TileType.LAND);
        scenarioManager.getScenario().getMapGrid()[coordinate2.getIndex()].setContents(fauna);
        user5.move(coordinate2);
        assertFalse(scenarioManager.getScenario().getMapGrid()[coordinate4.getIndex()].hasContents());
        assertTrue(scenarioManager.getScenario().getLog().getEvents().size() == 2);
        assertEquals(1,scenarioManager.getScenario().getLog().getEntitiesCollected());
        assertEquals(2,scenarioManager.getScenario().getLog().getPointsEarned());
        assertEquals(2,scenarioManager.getScenario().getLog().getTilesTraversed());
    }

    @Test
    public void testCollect() {
        createSafeTestScenario("l", tiles2, 5, 5);
        ScenarioManager scenarioManager =  ScenarioManager.getInstance();
        Fauna fauna = new Fauna(Size.MEDIUM,coordinate2,TileType.LAND);
        Flora flora = new Flora(Size.LARGE,coordinate);
        scenarioManager.getScenario().getMapGrid()[coordinate2.getIndex()].setContents(fauna);
        scenarioManager.getScenario().getMapGrid()[coordinate.getIndex()].setContents(flora);
        try {
            user1.collect(coordinate);
        }catch (Exception E) {assertFalse(true);}
        assertEquals(1,scenarioManager.getScenario().getLog().getEntitiesCollected());
        assertEquals(3,scenarioManager.getScenario().getLog().getPointsEarned());
        assertEquals(0,scenarioManager.getScenario().getLog().getTilesTraversed());
        assertEquals(false,scenarioManager.getScenario().getMapGrid()[coordinate.getIndex()].hasContents());

        try {
            user1.collect(coordinate2);
        }catch (Exception e) {assertFalse(true);}
        assertEquals(2,scenarioManager.getScenario().getLog().getEntitiesCollected());
        assertEquals(5,scenarioManager.getScenario().getLog().getPointsEarned());
        assertEquals(0,scenarioManager.getScenario().getLog().getTilesTraversed());
        assertEquals(false,scenarioManager.getScenario().getMapGrid()[coordinate.getIndex()].hasContents());
        System.out.print(scenarioManager.getScenario().getLog().toString());

    }

}