import agh.po.movement.MapDirection;
import agh.po.movement.DirectionProcessor;
import agh.po.movement.Vector2d;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MovementTest {
    @Test
    void changeDirectionTestRegular(){
        //given
        MapDirection direction = MapDirection.NORTH;
        //when
        MapDirection result = DirectionProcessor.changeDirection(direction, 3);
        //then
        assertEquals(MapDirection.SOUTHEAST, result);
    }

    @Test
    void changeDirectionTestChangeOverSize(){
        //given
        MapDirection direction = MapDirection.WEST;
        //when
        MapDirection result = DirectionProcessor.changeDirection(direction, 4);
        //then
        assertEquals(MapDirection.EAST, result);
    }

    @Test
    void generateDirectionTest(){
        //given
        MapDirection direction = DirectionProcessor.generateDirection();
        //when
        boolean result = DirectionProcessor.getMapDirections().contains(direction);
        //then
        assertTrue(result);
    }

    @Test
    void followsTest(){
        //given
        Vector2d vector1 = new Vector2d(2,1);
        Vector2d vector2 = new Vector2d(3,2);
        Vector2d vector3 = new Vector2d(3,3);
        //when
        boolean result1 = vector2.follows(vector1);
        boolean result2 = vector3.follows(vector1);
        boolean result3 = vector3.follows(vector2);
        boolean result4 = vector1.follows(vector2);
        boolean result5 = vector1.follows(vector3);
        boolean result6 = vector2.follows(vector3);
        //then
        assertTrue(result1);
        assertTrue(result2);
        assertTrue(result3);
        assertFalse(result4);
        assertFalse(result5);
        assertFalse(result6);
    }

    @Test
    void precedesTest(){
        //given
        Vector2d vector1 = new Vector2d(2,1);
        Vector2d vector2 = new Vector2d(3,2);
        Vector2d vector3 = new Vector2d(3,3);
        //when
        boolean result1 = vector2.precedes(vector1);
        boolean result2 = vector3.precedes(vector1);
        boolean result3 = vector3.precedes(vector2);
        boolean result4 = vector1.precedes(vector2);
        boolean result5 = vector1.precedes(vector3);
        boolean result6 = vector2.precedes(vector3);
        //then
        assertFalse(result1);
        assertFalse(result2);
        assertFalse(result3);
        assertTrue(result4);
        assertTrue(result5);
        assertTrue(result6);
    }

    @Test
    void lowerLeftTest(){
        //given
        Vector2d vector1 = new Vector2d(3,1);
        Vector2d vector2 = new Vector2d(2,2);
        //when
        Vector2d result = new Vector2d(2,1);
        //then
        assertEquals(result, vector1.lowerLeft(vector2));
    }

    @Test
    void upperRightTest(){
        //given
        Vector2d vector1 = new Vector2d(3,1);
        Vector2d vector2 = new Vector2d(2,2);
        //when
        Vector2d result = new Vector2d(3,2);
        //then
        assertEquals(result, vector1.upperRight(vector2));
    }

    @Test
    void addTest(){
        //given
        Vector2d vector1 = new Vector2d(1,2);
        Vector2d vector2 = new Vector2d(2,3);
        //when
        Vector2d result = new Vector2d(3,5);
        //then
        assertEquals(result, vector1.add(vector2));
    }

    @Test
    void subtractTest(){
        //given
        Vector2d vector1 = new Vector2d(1,2);
        Vector2d vector2 = new Vector2d(2,3);
        //when
        Vector2d result = new Vector2d(-1,-1);
        //then
        assertEquals(result, vector1.subtract(vector2));
    }

}
