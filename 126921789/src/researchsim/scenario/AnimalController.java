package researchsim.scenario;

import researchsim.entities.Fauna;
import researchsim.map.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Manager that facilitates the movement of animals on the map.
 *
 * @ass2
 */
public class AnimalController extends Object {

    /**
     * List of all the animals this manager knows about
     */
    private final List<Fauna> animals;

    /**
     * Creates the controller to is in charge of all animal movements in a scenario
     */
    public AnimalController() {
        this.animals = new ArrayList<Fauna>();
    }

    /**
     * returns list of all animals that are under the instances control
     * @return list of animals
     */
    public List<Fauna> getAnimals() {
        List<Fauna> copy = new ArrayList<>(animals);
        return copy;
    }

    /**
     * Adds the given animal to the controller
     * @param animal to be added to controller
     */
    public void addAnimal(Fauna animal) {
        this.animals.add(animal);
    }

    /**
     * removes the given animal from the controller
     * @param animal the animal to remove
     */
    public void removeAnimal(Fauna animal) {
        animals.remove(animal);
    }


    /**
     * Attempts to move a selection of the animals.
     * <p>
     * <b>IMPORTANT:</b> This method makes use of a random number so the <b>ORDER</b> of these
     * operations is <b>CRITICAL</b>.
     * <br>
     * When this method is called the following occurs:
     * <ol>
     *     <li>If the number of animals this method controls ({@link #getAnimals()} returns an
     *     empty list) then the method should immediately return</li>
     *     <li>A random number ({@code num1}) is chosen such that: 0 <= num1 < size of animals<br>
     *         This step should only make <b>one</b> call to {@link Random#nextInt(int)}.</li>
     *     <li>The following is repeated {@code num1 + 1}  times:
     *          <ol>
     *              <li>A random animal is chosen from the animals list.<br>
     *              This step should only make <b>one</b> call to {@link Random#nextInt(int)}.</li>
     *              <li>IF that the number of elements in {@link Fauna#getPossibleMoves()}
     *              is equal to (=) 0 (that is, it is empty)<br>
     *              THEN no further action is required for this animal.</li>
     *              <li>IF that the number of elements in {@link Fauna#getPossibleMoves()}
     *              is 1 equal to (=) <br>
     *              THEN the animal should move to that coordinate.</li>
     *              <li>IF that the number of elements in {@link Fauna#getPossibleMoves()}
     *              is greater than (&gt;) 1 <br>
     *              THEN a random coordinate is chosen from the list of possible moves.<br>
     *              This step should only make <b>one</b> call to {@link Random#nextInt(int)}.</li>
     *              </li>
     *          </ol>
     *     </li>
     * </ol>
     * The random variable should be retrieved using {@link Scenario#getRandom()}.
     *
     * @given
     * @see Random#nextInt(int)
     * @see Scenario#getRandom()
     * @see Fauna#getPossibleMoves()
     */
    public void move() {
        Scenario scenario = ScenarioManager.getInstance().getScenario();
        if (animals.isEmpty()) {
            return;
        }
        Random rand = scenario.getRandom();
        int num1 = rand.nextInt(animals.size());
        for (int i = 0; i <= num1; i++) {
            Fauna animal = animals.get(rand.nextInt(animals.size()));
            List<Coordinate> possibleMoves = animal.getPossibleMoves();
            if (possibleMoves.isEmpty()) {
                continue;
            } else if (possibleMoves.size() == 1) {
                animal.move(possibleMoves.get(0));
            } else {
                animal.move(possibleMoves.get(rand.nextInt(possibleMoves.size())));
            }
        }
    }
}
