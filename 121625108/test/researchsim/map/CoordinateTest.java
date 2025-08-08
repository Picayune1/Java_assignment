package researchsim.map;
import junit.framework.*;
import researchsim.scenario.*;

public class CoordinateTest {
    Scenario scenario = new Scenario("scenario1", 5,5);
    ScenarioManager scenariomanager = ScenarioManager.getInstance();

    public CoordinateTest(){
        scenariomanager.addScenario(scenario);
    }

    @org.junit.Test
    public void testgetx(){
        Coordinate coordinate1 = new Coordinate(1,2);
        Assert.assertEquals(1, coordinate1.getX());
    }

    @org.junit.Test
    public void testgety(){
        Coordinate coordinate1 = new Coordinate(1,2);
        Assert.assertEquals(2, coordinate1.getY());
    }

    @org.junit.Test
    public void testEmptyCoordinate(){
        Coordinate coordinate3 = new Coordinate();
        Assert.assertEquals(0,coordinate3.getX());
        Assert.assertEquals(0,coordinate3.getY());
    }

    @org.junit.Test
    public void testIndex(){
        Coordinate coordinate2 = new Coordinate(7);
        Assert.assertEquals(2,coordinate2.getX());
        Assert.assertEquals(1,coordinate2.getY());
    }

    @org.junit.Test
    public void testisInBoundsFalse(){
        Coordinate coordinate4 = new Coordinate(10,10);
        Assert.assertFalse(coordinate4.isInBounds());
    }

    @org.junit.Test
    public void testisInBoundsTrue(){
        Coordinate coordinate1 = new Coordinate(1,2);
        Assert.assertTrue(coordinate1.isInBounds());
    }

    @org.junit.Test
    public void testgetIndex(){
        Coordinate coordinate1 = new Coordinate(1,2);
        Assert.assertEquals(11,coordinate1.getIndex());
    }

    @org.junit.Test
    public void testtoString(){
        Coordinate coordinate1 = new Coordinate(1,2);
        Assert.assertEquals("(1,2)",coordinate1.toString());
    } 
}