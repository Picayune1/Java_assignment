package researchsim.logging;

import java.util.ArrayList;
import java.util.List;
import researchsim.map.Coordinate;
import researchsim.map.Tile;
import researchsim.scenario.ScenarioManager;
import researchsim.util.NoSuchEntityException;

/**
 * A detailed log that contains a record of {@link Event}s and contains some event statistics.
 *
 * @ass2
 */
public class Logger extends Object {

    /**
     * The amount of entities that have been collected in collect events
     */
    private int entitiesCollected;

    /**
     * The amount of tiles travelled in move events
     */
    private int tilesTravelled;

    /**
     * the amount of points earned in  collect events
     */
    private int pointsEarned;

    /**
     * list holding all events that have taken place in the scenario
     */
    private List<Event> events;

    /**
     * Creates a new logger that holds a list of events that occur in a scenario
     * Keeps track of tiles travelled, entities collected and points collected
     * these stats are set to 0 when the logger is created
     */
    public Logger() {
        this.entitiesCollected = 0;
        this.pointsEarned = 0;
        this.tilesTravelled = 0;
        this.events = new ArrayList<Event>();
    }

    /**
     * the amount of tiles that have been travelled in all the move events of this scenario
     * @return the tiles travelled
     */
    public int getTilesTraversed() {
        return tilesTravelled;
    }

    /**
     * the amount of entities collected in collect events for this scenario
     * @return the amount of entities collected
     */
    public int getEntitiesCollected() {
        return entitiesCollected;
    }

    /**
     * the amount of points earned by user in collect events
     * @return the amount of points earned
     */
    public int getPointsEarned() {
        return pointsEarned;
    }

    /**
     * returns events list of all events that have happened in current scenario
     * @return events
     */
    public List<Event> getEvents() {
        List<Event> copy = new ArrayList<>(events);
        return copy;
    }

    /**
     * Adds the event parameter to the list of events in the logger
     * if event is MoveEvent then adds the distance travelled to tilesTravelled
     * if event is CollectEvent than increments entitiesCollected and
     * adds the amount of points the entity is worth to PointsEarned
     * @param event event taken place in scenario to be added to logger
     */
    public void add(Event event) {
        ScenarioManager scenarioManager = ScenarioManager.getInstance();
        if (event instanceof CollectEvent) {
            try {
                Tile tile =
                        scenarioManager.getScenario().getMapGrid()
                                [event.getCoordinate().getIndex()];
                int points = tile.getContents().getSize().points;
                this.pointsEarned = this.getPointsEarned() + points;
                this.entitiesCollected++;
                this.events.add(event);
            } catch (NoSuchEntityException e) {
                assert true;
            } catch (NullPointerException e) {
                this.events.add(event);
            }
        } else if (event instanceof MoveEvent) {
            Coordinate distanceTravelled =
                    event.getInitialCoordinate().distance(event.getCoordinate());
            int distance = distanceTravelled.getAbsX() + distanceTravelled.getAbsY();
            this.tilesTravelled = tilesTravelled + distance;
            this.events.add(event);
        } else {
            this.events.add(event);
        }
    }

    /**
     * returns the human-readable String for the logger class
     * formatted as
     *  logEntry
     *  logEntry
     *  ...
     *  where every logEntry is an event in its string layout in the list events
     * @return human_readable logger class
     */
    @Override
    public String toString() {
        String text = "";
        for (Event event : getEvents()) {
            text = text + event.toString();
        }
        return text;
    }
}
