package researchsim.scenario;

import java.util.*;

/**
 * Singleton class ScenarioManager manages all scenarios
 */
public class ScenarioManager extends Object {

    /**
     * create the single instance of ScenarioManager
     */
    private static ScenarioManager instance = new ScenarioManager();

    /**
     * The current scenario running
     */
    private Scenario scenario;

    /**
     * map containing all the scenarios
     */
    private LinkedHashMap<String, Scenario> loadedScenarios = new LinkedHashMap();

    /**
     * Private constructor to stop other classes from instantiating
     */
    private ScenarioManager() {}
    
    /**
     * Makes sure no other instance of ScenarioManager can exist
     * @return the single instance of ScenarioManager
     */
    public static ScenarioManager getInstance() {
        return instance;
    }

    /**
     * returns the current scenario loaded
     * @return the current loaded scenario
     */
    public Scenario getScenario() {
        return scenario;
    }

    /**
     * sets scenario to given scenario name in LoadedScenarios
     * @param scenarioName the scenario to set as current scenario
     */
    public void setScenario(String scenarioName) {
        scenario = loadedScenarios.get(scenarioName);
    }

    /**
     * return the map of scenarios
     * @return LoadedScenarios
     */
    public Map<String, Scenario> getLoadedScenarios() {
        return loadedScenarios;
    }

    /**
     * adds new scenario to LoadedScenario and also sets it as current scenario
     * @param scenario new scenario to add to LoadedScenario and to set as current scenario
     */
    public void addScenario(Scenario scenario) {
        String scenarioName = scenario.getName();
        loadedScenarios.put(scenarioName, scenario);
        this.scenario = scenario;
    }

    /**
     * resets and clears LoadedScenario
     */
    public void reset() {
        loadedScenarios.clear();
    }
    
}
