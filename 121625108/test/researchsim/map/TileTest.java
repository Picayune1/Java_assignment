package researchsim.map;
import junit.framework.*;
import researchsim.entities.*;
import researchsim.util.NoSuchEntityException;


public class TileTest {
    Coordinate coordinate = new Coordinate(4,3);
    Entity entity1 = new Fauna(Size.MEDIUM, coordinate,TileType.LAND);
    Tile tile1 = new Tile(TileType.LAND);

    @org.junit.Test
    public void testgetType(){
        Assert.assertEquals(TileType.LAND, tile1.getType());
    }

    @org.junit.Test
    public void testgetContents() throws NoSuchEntityException {
        tile1.setContents(entity1);
        Assert.assertEquals(entity1, tile1.getContents());
    }

    @org.junit.Test
    public void testErrorgetContents() throws NoSuchEntityException {
        try {
            Assert.assertEquals(entity1, tile1.getContents());
        }
        catch(Exception e){
            Assert.assertTrue(true);
        }
    }

    @org.junit.Test
    public void testsetContents() throws NoSuchEntityException {
        tile1.setContents(entity1);
        Assert.assertEquals(entity1, tile1.getContents());
    }

    @org.junit.Test
    public void TestFalsehasContents(){
        Assert.assertFalse(tile1.hasContents());
    }

    @org.junit.Test
    public void TestTruehasContents(){
        tile1.setContents(entity1);
        Assert.assertTrue(tile1.hasContents());
    }
}