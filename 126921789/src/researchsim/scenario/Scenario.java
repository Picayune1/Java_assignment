package researchsim.scenario;

import researchsim.entities.Fauna;
import researchsim.entities.Flora;
import researchsim.entities.Size;
import researchsim.entities.User;
import researchsim.logging.Logger;
import researchsim.map.Coordinate;
import researchsim.map.Tile;
import researchsim.map.TileType;
import researchsim.util.BadSaveException;
import researchsim.util.CoordinateOutOfBoundsException;
import researchsim.util.NoSuchEntityException;

import java.io.IOException;
import java.io.Reader;
import java.util.*;


/**
 * The scenario is the overriding class of the simulation.
 * It is similar to a level in a video game.
 * <p>
 * NOTE: Some methods in this class require interaction with the {@link ScenarioManager}. Only
 * interact with it when you need it.
 *
 * @ass1_partial
 */
public class Scenario {

    /**
     * The minimum dimensions of the map grid.
     * The value of this constant is {@value}
     *
     * @ass1
     */
    public static final int MIN_SIZE = 5;
    /**
     * The maximum dimensions of the map grid.
     * The value of this constant is {@value}
     *
     * @ass1
     */
    public static final int MAX_SIZE = 15;
    /**
     * Maximum number of tiles that the grid contains.
     * The value of this constant is {@value}
     *
     * @ass1
     */
    public static final int MAX_TILES = MAX_SIZE * MAX_SIZE;
    /**
     * The name of this scenario.
     */
    private final String name;
    /**
     * The width of the map in the scenario.
     */
    private final int width;
    /**
     * The height of the map in the scenario.
     */
    private final int height;
    /**
     * The tile grid for this scenario.
     */
    private Tile[] mapGrid;
    /**
     * The log for events for this scenario
     */
    private Logger log;
    /**
     * The controller for animals in the scenario
     */
    private AnimalController animalController;
    /**
     * The random instance for the seed of the scenario
     */
    private Random seed;
    
    /**
     * Creates a new Scenario with a given name, width, height and random seed. <br>
     * A one dimensional (1D) array of tiles is created as the board with the given width and
     * height. <br>
     * An empty Animal Controller and logger is also initialised. <br>
     * An instance of the {@link Random} class in initialised with the given seed.
     *
     * @param name   scenario name
     * @param width  width of the board
     * @param height height of the board
     * @param seed   the random seed for this scenario
     * @throws IllegalArgumentException if width &lt; {@value Scenario#MIN_SIZE} or width &gt;
     * {@value Scenario#MAX_SIZE} or height
     * &lt; {@value Scenario#MIN_SIZE} or height &gt;
     * {@value Scenario#MAX_SIZE} or seed &lt; 0 or name is {@code
     * null}
     * @ass1_partial
     * @see Random (<a href="https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/Random.html">Link</a>)
     */
    public Scenario(String name, int width, int height, int seed) throws IllegalArgumentException {
        if (width > MAX_SIZE || width < MIN_SIZE) {
            throw new IllegalArgumentException("The given width does not conform to the "
                + "requirement: " + MIN_SIZE + " <= width <= " + MAX_SIZE + ".");
        }
        if (height > MAX_SIZE || height < MIN_SIZE) {
            throw new IllegalArgumentException("The given height does not conform to the "
                + "requirement: " + MIN_SIZE + " <= height <= " + MAX_SIZE + ".");
        }
        if (name == null) {
            throw new IllegalArgumentException("The given name does not conform to the "
                + "requirement: name != null.");
        }
        this.name = name;
        this.width = width;
        this.height = height;
        this.mapGrid = new Tile[width * height];
        this.seed = new Random(seed);
        this.log = new Logger();
        this.animalController = new AnimalController();
    }

    /**
     * Returns the name of the scenario.
     *
     * @return scenario name
     * @ass1
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the map grid for this scenario.
     * <p>
     * Adding or removing elements from the returned array should not affect the original array.
     *
     * @return map grid
     * @ass1
     */
    public Tile[] getMapGrid() {
        return Arrays.copyOf(mapGrid, getSize());
    }

    /**
     * Updates the map grid for this scenario.
     * <p>
     * Adding or removing elements from the array that was passed should not affect the class
     * instance array.
     *
     * @param map the new map
     * @throws CoordinateOutOfBoundsException (param) map length != size of current scenario map
     * @ass1_partial
     */
    public void setMapGrid(Tile[] map) throws CoordinateOutOfBoundsException {
        if (map.length != getSize()) {
            throw new CoordinateOutOfBoundsException();
        }
        mapGrid = Arrays.copyOf(map, getSize());
    }


    /**
     * Returns the width of the map for this scenario.
     *
     * @return map width
     * @ass1
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the map for this scenario.
     *
     * @return map height
     * @ass1
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the size of the map in the scenario.<br>
     * The size of a map is the total number of tiles in the Tile array.
     *
     * @return map size
     * @ass1
     */
    public int getSize() {
        return width * height;
    }

    /**
     * return the random seed for the current scenario
     * @return random seed
     */
    public Random getRandom() {
        return seed;
    }

    /**
     * returns the log for all events for the current scenario
     * @return logger for scenario
     */
    public Logger getLog() {
        return log;
    }

    /**
     * returns the controller controls how all animals move in a scenario
     * @return AnimalController for current scenario
     */
    public AnimalController getController() {
        return animalController;
    }

    /**
     * the load method takes a reader laid out using the scenario encode and creates a scenario.
     * The scenario is then added to scenario manager as well as returned.
     * The layout of a valid reader is :
     * {ScenarioName}
     *  Width:{Width}
     *  Height:{Height}
     *  Seed:{Seed}
     *  {Separator}
     *  {map}
     *  {Separator}
     *  {entity}
     *  {entity...}
     *  where entity portion is optional and not required
     * @param reader The file with scenario to read from
     * @return scenario based on reader contents
     * @throws IOException if issue reading the reader
     * @throws BadSaveException if reader layout is invalid
     */
    public static Scenario load(Reader reader) throws IOException,
            BadSaveException {
        char[] chars = new char[10000];
        try {
            reader.read(chars);
            String file = new String(chars);
            String[] fileParts = file.split("[:\r\n]");
            // splits the file by new line and ":" character
            if (fileParts.length < 8) { //8 is the amount of elements in fileParts before the map
                throw new BadSaveException();
            }
            final String name = fileParts[0]; //name in first position of array
            int width = Integer.parseInt(fileParts[2]); //width value should be third value in array
            int height = Integer.parseInt(fileParts[4]); //Height should be fifth value in array
            int seed = Integer.parseInt(fileParts[6]); //seed should be seventh value in array

            if (fileParts.length < 9 + height) { //9+height is amount of elements before Entities
                throw new BadSaveException();
            }
            if (width == -1) {
                width = 5;
            }
            if (height == -1) {
                height = 5;
            }
            if (seed == -1) {
                seed = 0;
            }
            // if the width, height or seed are -1 then set it to default value which is 5
            final String separator = "=".repeat(width);

            //These are all checks to make sure the reader meets requirement
            if (!fileParts[1].equals("Width")) { // Width should always be 2nd element
                throw new BadSaveException();
            }
            if (!fileParts[3].equals("Height")) { //Height should always be 4th element
                throw new BadSaveException();
            }
            if (!fileParts[5].equals("Seed")) { //Seed should always be 6th element
                throw new BadSaveException();
            }
            if (!fileParts[7].equals(separator)) {
                // 1st Separator should always be 8th element
                throw new BadSaveException();
            }
            if (seed < -1) { //any seed less than negative 1 is invalid
                throw new BadSaveException();
            }
            Scenario scenario = new Scenario(name, width, height, seed);
            List<Tile> map = new ArrayList<>();
            for (int i = 0; i < height; i++) {
                if (fileParts[8 + i].length() != width) {
                    throw new BadSaveException();
                }
                for (int l = 0; l < width; l++) {
                    String tile = String.valueOf(fileParts[8 + i].charAt(l));
                    //the 8th element in file parts is the beginning of the map
                    Tile mapTile = new Tile(TileType.decode(tile));
                    map.add(mapTile);
                }
            }
            Tile[] newMap = new Tile[map.size()];
            map.toArray(newMap);
            try {
                scenario.setMapGrid(newMap);
            } catch (CoordinateOutOfBoundsException e) {
                throw new BadSaveException();
            }
            ScenarioManager scenarioManager = ScenarioManager.getInstance();
            scenarioManager.addScenario(scenario);
            if (fileParts.length > 9 + height) {
                //9 is the amount of elements not including map before entities
                for (int i = 0; i < fileParts.length - (9 + height); i++) {
                    String[] entity = fileParts[8 + height + i].split("-");
                    //+8 because list indexing begins at 0 and not 1
                    switch (entity[0]) {
                        case "User":
                            Coordinate coordinate = Coordinate.decode(entity[1]);
                            String userName = entity[2];
                            final User user = new User(coordinate, userName);
                            Tile tile = newMap[coordinate.getIndex()];

                            if (tile.hasContents()) {
                                throw new BadSaveException();
                                //tile should not have entity on it already
                            }
                            if (tile.getType() == TileType.OCEAN
                                    || tile.getType() == TileType.MOUNTAIN) {
                                throw new BadSaveException();
                            }
                            if (entity.length > 3) {
                                throw new BadSaveException();
                                //if more than three elements, user entity had to many hyphens
                            }
                            tile.setContents(user);
                            break;

                        case "Fauna":
                            Coordinate coordinate2 = Coordinate.decode(entity[2]);
                            Size size = Size.valueOf(entity[1]);
                            TileType tileType = TileType.valueOf(entity[3]);
                            Tile tile2 = newMap[coordinate2.getIndex()];
                            final Fauna fauna = new Fauna(size, coordinate2, tileType);

                            if (tile2.getType() != tileType) {
                                throw new BadSaveException();
                            }
                            if (tile2.hasContents()) {
                                throw new BadSaveException();
                            }
                            if (entity.length > 4) {
                                throw new BadSaveException(); // fauna entity had too many hyphens
                            }
                            tile2.setContents(fauna);
                            break;

                        case "Flora":
                            Coordinate coordinate3 = Coordinate.decode(entity[2]);
                            Size size2 = Size.valueOf(entity[1]);
                            TileType tileType2 = TileType.valueOf(entity[3]);
                            Tile tile3 = newMap[coordinate3.getIndex()];
                            final Flora flora = new Flora(size2, coordinate3);

                            if (tile3.hasContents()) {
                                throw new BadSaveException();
                            }
                            if (tileType2 == TileType.OCEAN) {
                                throw new BadSaveException();
                            }
                            if (entity.length > 4) {
                                throw new BadSaveException();
                            }
                            tile3.setContents(flora);
                            break;
                        default:
                            throw new BadSaveException();
                    }
                }
            }
            return scenario;
        } catch (IOException e) {
            throw new IOException();
        } catch (IllegalArgumentException e) {
            throw new BadSaveException();
        } catch (Exception e) {
            throw new BadSaveException();
        }
    }

    /**
     * Compares object and current Scenario to see if they are equal
     * equal only if hash value is the same and both are scenario class
     * @param other object to compare to this scenario
     * @return true iff hash values are the same and both are scenario objects
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.hashCode() == hashCode()
                && other.getClass().equals(getClass())) {
            return true;
        }
        return false;
    }

    /**
     * return the hash value of the scenario object
     * hash value based on name, width height and map of the scenario
     * @return hash value
     */
    @Override
    public int hashCode() {
        return name.hashCode() * Integer.hashCode(width)
                * Integer.hashCode(height) * Arrays.hashCode(mapGrid);
    }

    /**
     * returns machine-readable format for the scenario
     * format of encode is :
     * {ScenarioName}
     *  Width:{Width}
     *  Height:{Height}
     *  Seed:{Seed}
     *  {Separator}
     *  {map}
     *  {Separator}
     *  {entity}
     *  {entity...}
     *  where separator is character = for the width of
     *  the scenario amount of times
     *  entity part is optional and only their if scenario has entities
     * @return machine-readable string for scenario
     */
    public String encode() {
        String encode = getName() + System.lineSeparator() + "Width:" + getWidth()
                + System.lineSeparator();
        encode = encode + "Height:" + getHeight() + System.lineSeparator() + "Seed:" + getRandom();
        encode = encode + System.lineSeparator() + "=".repeat(getWidth()) + System.lineSeparator();
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                encode = encode + getMapGrid()[width * y + x].getType().encode();
            }
            encode = encode + System.lineSeparator();
        }
        encode = encode + "=".repeat(getWidth());
        for (Tile tile : getMapGrid()) {
            if (tile.hasContents()) {
                encode = encode + System.lineSeparator();
                try {
                    encode = encode + tile.getContents().encode();
                } catch (NoSuchEntityException e) {
                    assert true;
                    //do nothing since we know theirs an entity their by has content check
                }
            }
        }
        return  encode;
    }

    /**
     * Returns the human-readable string representation of this scenario.
     * <p>
     * The format of the string to return is:
     * <pre>
     *     (name)
     *     Width: (width), Height: (height)
     *     Entities: (entities)
     * </pre>
     * Where:
     * <ul>
     *   <li>{@code (name)} is the scenario's name</li>
     *   <li>{@code (width)} is the scenario's width</li>
     *   <li>{@code (height)} is the scenario's height</li>
     *   <li>{@code (entities)} is the number of entities currently on the map in the scenario</li>
     * </ul>
     * For example:
     *
     * <pre>
     *     Beach retreat
     *     Width: 6, Height: 5
     *     Entities: 4
     * </pre>
     * <p>
     * Each line should be separated by a system-dependent line separator.
     *
     * @return human-readable string representation of this scenario
     * @ass1
     */
    @Override
    public String toString() {
        StringJoiner result = new StringJoiner(System.lineSeparator());
        result.add(name);
        result.add(String.format("Width: %d, Height: %d", width, height));
        result.add(String.format("Entities: %d",
            Arrays.stream(mapGrid).filter(Objects::nonNull).filter(Tile::hasContents).count()));
        return result.toString();
    }
}
