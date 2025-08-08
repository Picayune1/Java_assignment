package researchsim.entities;
import researchsim.map.*;
import org.junit.Assert;

public class FaunaTest {
    Coordinate coordinate = new Coordinate(4,3);
    Fauna fauna1 = new Fauna(Size.MEDIUM, coordinate ,TileType.LAND);

    @org.junit.Test
    public void testerror() throws IllegalArgumentException {
        try {
            Fauna fauna2 = new Fauna(Size.GIANT, coordinate, TileType.SAND);
        }
        catch (Exception e){
            Assert.assertTrue(true);
        }
    }

    @org.junit.Test
    public void testgetSize(){
        Assert.assertEquals(Size.MEDIUM,fauna1.getSize());
    }

    @org.junit.Test
    public void testgetCoordinate(){
        Assert.assertEquals(coordinate, fauna1.getCoordinate());
    }

    @org.junit.Test
    public void testsetCoordinate(){
        Coordinate coordinate2 = new Coordinate(2,1);
        fauna1.setCoordinate(coordinate2);
        Assert.assertEquals(coordinate2,fauna1.getCoordinate());
    }

    @org.junit.Test
    public void testtoString(){
        Assert.assertEquals("Dog [Fauna] at (4,3)", fauna1.toString());
    }

    @org.junit.Test
    public void testgetHabitat(){
        Assert.assertEquals(TileType.LAND, fauna1.getHabitat());
    }

    @org.junit.Test
    public void testgetName(){
        Assert.assertEquals("Dog", fauna1.getName());
    }
}