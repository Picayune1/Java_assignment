package researchsim.entities;

import researchsim.logging.CollectEvent;
import researchsim.map.Coordinate;
import researchsim.map.Tile;
import researchsim.scenario.ScenarioManager;
import researchsim.util.Collectable;

/**
 * Flora is all the plant life present in a particular region or time, generally the naturally
 * occurring (indigenous) native plants.
 * Flora can be collected by the {@link User}.
 * <p>
 * NOTE: Some methods in this class require interaction with the {@link ScenarioManager}. Only
 * interact with it when you need it.
 *
 * @ass1_partial
 */
public class Flora extends Entity implements Collectable {
    /**
     * Creates a flora (plant) with a given size and coordinate.
     *
     * @param size       size associated with the plant
     * @param coordinate coordinate associated with the plant
     * @ass1
     */
    public Flora(Size size, Coordinate coordinate) {
        super(size, coordinate);
    }

    /**
     * Returns the human-readable name of this plant.
     * The name is determined by the following table.
     * <table border="1">
     *     <tr>
     *          <td colspan=2>Human-readable names</td>
     *     </tr>
     *     <tr>
     *         <td>SMALL</td>
     *         <td>Flower</td>
     *     </tr>
     *     <tr>
     *         <td>MEDIUM</td>
     *         <td>Shrub</td>
     *     </tr>
     *     <tr>
     *         <td>LARGE</td>
     *         <td>Sapling</td>
     *     </tr>
     *     <tr>
     *         <td>GIANT</td>
     *         <td>Tree</td>
     *     </tr>
     * </table>
     *
     * @return human-readable name
     * @ass1
     */
    @Override
    public String getName() {
        String name;
        switch (getSize()) {
            case SMALL:
                name = "Flower";
                break;
            case MEDIUM:
                name = "Shrub";
                break;
            case LARGE:
                name = "Sapling";
                break;
            case GIANT:
            default:
                name = "Tree";
        }
        return name;
    }

    /**
     * returns the points earned by collecting the current Flora
     * Creates collectEvent and adds it to current scenario Log
     * Removes Flora from current scenario mapGrid
     * @param user the user that is collecting other entities
     * @return the points user gains by collecting this entity
     */
    public int collect(User user) {
        ScenarioManager scenarioManager = ScenarioManager.getInstance();
        CollectEvent collectEvent = new CollectEvent(user, this);
        scenarioManager.getScenario().getLog().add(collectEvent);
        Tile oldTile = scenarioManager.getScenario().getMapGrid()[getCoordinate().getIndex()];
        oldTile.setContents(null);
        return getSize().points;
    }
}
