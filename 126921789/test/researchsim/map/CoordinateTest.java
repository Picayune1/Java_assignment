package researchsim.map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import researchsim.scenario.Scenario;
import researchsim.scenario.ScenarioManager;
import researchsim.util.BadSaveException;
import researchsim.util.CoordinateOutOfBoundsException;

import java.util.Arrays;

import static org.junit.Assert.*;


public class CoordinateTest {

    private Coordinate coordinate11;
    private Coordinate coordinate11Dup;
    private Coordinate coordinate21;
    private Coordinate coordinateneg21;

    @Before
    public void setUp() throws Exception {
        coordinate11 = new Coordinate(1, 1);
        coordinate11Dup = new Coordinate(1, 1);
        coordinate21 = new Coordinate(2, 1);
        coordinateneg21 = new Coordinate(-2, -1);
    }

    @After
    public void tearDown() throws Exception {
        ScenarioManager.getInstance().reset();
    }

    @Test
    public void testDefaultConstructor() {
        Coordinate origin = new Coordinate();
        assertEquals("Incorrect value was returned.", 0, origin.getX());
        assertEquals("Incorrect value was returned.", 0, origin.getY());
    }

    @Test
    public void testIndexConstructor() {
        createSafeTestScenario("testIndexConstructor", 5, 5);
        Coordinate origin = new Coordinate(12);
        assertEquals("Incorrect value was returned.", 2, origin.getX());
        assertEquals("Incorrect value was returned.", 2, origin.getY());
    }

    @Test
    public void testGetX() {
        assertEquals("Incorrect value was returned.", 1, coordinate11.getX());
        assertEquals("Incorrect value was returned.", -2, coordinateneg21.getX());

    }

    @Test
    public void testGetY() {
        assertEquals("Incorrect value was returned.", 1, coordinate11.getY());
        assertEquals("Incorrect value was returned.", -1, coordinateneg21.getY());
    }

    @Test
    public void testGetIndex() {
        createSafeTestScenario("testGetIndex", 5, 5);
        assertEquals("Incorrect value was returned.",
            6, coordinate11.getIndex());
        assertEquals("Incorrect value was returned.",
            7, coordinate21.getIndex());
        assertEquals("Incorrect value was returned.",
            -7, coordinateneg21.getIndex());
    }

    @Test
    public void testIsInBounds() {
        createSafeTestScenario("testIsInBounds", 10, 10);
        assertTrue("Incorrect value was returned.", coordinate11.isInBounds());
        assertFalse("Incorrect value was returned.", coordinateneg21.isInBounds());
    }

    @Test
    public void testConvert() {
        createSafeTestScenario("testConvert", 10, 10);
        assertEquals("Incorrect value was returned.",
            55, Coordinate.convert(5, 5));
        createSafeTestScenario("testConvert2", 5, 7);
        assertEquals("Incorrect value was returned.",
            30, Coordinate.convert(5, 5));
    }

    @Test
    public void testToString() {
        assertEquals("Incorrect value was returned.",
            "(1,1)", coordinate11.toString());
        assertEquals("Incorrect value was returned.",
            "(0,0)", new Coordinate().toString());
        assertEquals("Incorrect value was returned.",
            "(-2,-1)", coordinateneg21.toString());
    }

    @Test
    public void testGetAbsX() {
        assertEquals(2, coordinateneg21.getAbsX());
        assertEquals(2, coordinate21.getAbsX());
        assertEquals(1, coordinate11.getAbsX());
        assertEquals(0, new Coordinate().getAbsX());
    }

    @Test
    public void testGetAbsY() {
        assertEquals(1, coordinateneg21.getAbsY());
        assertEquals(1,coordinate11.getAbsY());
        assertEquals(1,coordinate21.getAbsY());
        assertEquals(0, new Coordinate().getAbsY());
    }

    @Test
    public void testDecode1() {
        Coordinate coordinate6 = new Coordinate(1000, 2304);
        try {
            Coordinate coordinate = Coordinate.decode("1,1");
            Coordinate coordinate1 = Coordinate.decode("-2,-1");
            Coordinate coordinate2 = Coordinate.decode("2,1");
            Coordinate coordinate3 = Coordinate.decode("1000,2304");
            Coordinate coordinate4 = Coordinate.decode("0002,0001");
            Coordinate coordinate5 = Coordinate.decode("-00002,-00001");

            assertEquals(coordinate11, coordinate);
            assertEquals(coordinateneg21, coordinate1);
            assertEquals(coordinate21, coordinate2);
            assertEquals(coordinate6, coordinate3);
            assertEquals(coordinate21, coordinate4);
            assertEquals(coordinateneg21, coordinate5);

        } catch (BadSaveException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testDecode2() {
        Boolean pass = false;
        try {
            Coordinate coordinate = Coordinate.decode("21");
        }
        catch (BadSaveException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    @Test
    public void testDecode3() {
        Boolean pass = false;
        try {
            Coordinate coordinate = Coordinate.decode("2,,1");
        }catch (BadSaveException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    @Test
    public void testDecode4() {
        Boolean pass = false;
        try {
            Coordinate coordinate = Coordinate.decode("2,");
        }
        catch (BadSaveException e){
            pass = true;
        }
        assertTrue(pass);
    }

    @Test
    public void testDecode5() {
        Boolean pass = false;
        try {
            Coordinate coordinate = Coordinate.decode(null);
        }
        catch (BadSaveException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    @Test
    public void testDecode6() {
        Boolean pass = false;
        try {
            Coordinate coordinate = Coordinate.decode("t,l");
        }
        catch (BadSaveException E) {
            pass = true;
        }
        assertTrue(pass);
    }

    @Test
    public void testDecode7() {
        Boolean pass = false;
        try {
            Coordinate coordinate = Coordinate.decode("1,2t");
        }
        catch (BadSaveException E) {
            pass = true;
        }
        assertTrue(pass);
    }

    @Test
    public void testDecode8() {
        Boolean pass = false;
        try {
            Coordinate coordinate = Coordinate.decode("1,2,3");
        }
        catch (BadSaveException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    @Test
    public void testDecode9() {
        Boolean pass = false;
        try {
            Coordinate coordinate = Coordinate.decode("0.5,1.8");
        }
        catch (BadSaveException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    @Test
    public void testDecode10() {
        Boolean pass = false;
        try {
            Coordinate coordinate = Coordinate.decode("1,2,");
        }
        catch (BadSaveException e) {
            pass = true;
        }
        assertTrue(pass);
    }

    @Test
    public void testDecode11() {
        Boolean pass = false;
        try {
            Coordinate coordinate = Coordinate.decode(",2");
        }
        catch (BadSaveException e)  {
            pass = true;
        }
        assertTrue(pass);
    }

    @Test
    public void testDecode12() {
        Boolean pass = false;
        try {
            Coordinate coordinate = Coordinate.decode(",2,1");
        }
        catch (BadSaveException E) {
            pass = true;
        }
        assertTrue(pass);
    }



    @Test
    public void testHashCode() {
        Coordinate coordinate = new Coordinate(1,1);
        Coordinate coordinate1 = new Coordinate(2,1);
        Coordinate coordinate2 = new Coordinate(-2,-1);
        Coordinate coordinate3 = new Coordinate(1,2);

        assertEquals(coordinate11.hashCode(),coordinate.hashCode());
        assertEquals(coordinate21.hashCode(),coordinate1.hashCode());
        assertEquals(coordinateneg21.hashCode(),coordinate2.hashCode());

        if (coordinate3.hashCode() == coordinate21.hashCode()) {
            assertFalse(true);
        }
    }

    @Test
    public void testEncode() {
        assertEquals("1,1",coordinate11.encode());
        assertEquals("2,1", coordinate21.encode());
        assertEquals("-2,-1", coordinateneg21.encode());
    }

    @Test
    public void testDistance() {
        Coordinate coordinate = new Coordinate(1,0);
        Coordinate coordinate2 = new Coordinate(-1,0);
        Coordinate coordinate3 = new Coordinate (0,0);
        Coordinate coordinate4 = new Coordinate(3,2);
        Coordinate coordinate5 = new Coordinate(-1,0);

        assertEquals(coordinate,coordinate11.distance(coordinate21));
        assertEquals(coordinate2, coordinate21.distance(coordinate11));
        assertEquals(coordinate3, coordinate11.distance(coordinate11));
        assertEquals(coordinate4,coordinateneg21.distance(coordinate11));
        assertEquals(coordinate5,coordinate11.distance(coordinateneg21));
    }

    @Test
    public void testTranslate() {
        Coordinate coordinate = new Coordinate(3,2);
        Coordinate coordinate1 = new Coordinate(2,2);
        Coordinate coordinate2 = new Coordinate(-1,0);
        Coordinate coordinate3 = new Coordinate(0,0);

        assertEquals(coordinate,coordinate11.translate(2,1));
        assertEquals(coordinate1,coordinate11.translate(1,1));
        assertEquals(coordinate,coordinate21.translate(1,1));
        assertEquals(coordinate2, coordinate11.translate(-2,-1));
        assertEquals(coordinate3, coordinateneg21.translate(2,1));
    }

    @Test
    public void testEquals() {
        Coordinate coordinate = new Coordinate(1,2);
        assertTrue(coordinate11.equals(coordinate11Dup));
        assertFalse(coordinate21.equals(coordinateneg21));
        assertFalse(coordinate21.equals(coordinate));
        assertFalse(coordinate21.equals(null));
        assertFalse(coordinate11.equals(coordinate11Dup.translate(0,1)));
        assertTrue(coordinate11.equals(coordinate11Dup.translate(0,0)));
    }

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
}