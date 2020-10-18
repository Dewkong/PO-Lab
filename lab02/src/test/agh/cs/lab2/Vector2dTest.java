package agh.cs.lab2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2dTest {
    @Test
    void equalsTest(){
        //given
        Vector2d vector1 = new Vector2d(1,2);
        String nonVector = new String("Nie jestem wektorem");
        //when
        boolean result = vector1.equals(nonVector);
        //then
        assertFalse(result);

        //given
        Vector2d vector2 = new Vector2d(12,-6);
        Vector2d vector3 = new Vector2d(10,-9);
        //when
        result = vector2.equals(vector3);
        //then
        assertFalse(result);

        //given
        Vector2d vector4 = new Vector2d(-8,21);
        Vector2d vector5 = new Vector2d(-8,21);
        //when
        result = vector4.equals(vector5);
        //then
        assertTrue(result);

        //given
        Vector2d vector6 = new Vector2d(6,6);
        //when
        result = vector6.equals(vector6);
        //then
        assertTrue(result);
    }

    @Test
    void toStringTest(){
        //given
        Vector2d vector = new Vector2d(1,2);
        //when
        String result = ("(1,2)");
        //then
        assertEquals(result, vector.toString());
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
        assertTrue(result1);
        assertTrue(result2);
        assertTrue(result3);
        assertFalse(result4);
        assertFalse(result5);
        assertFalse(result6);
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

    @Test
    void oppositeTest(){
        //given
        Vector2d vector = new Vector2d(5,-5);
        //when
        Vector2d result = new Vector2d(-5,5);
        //then
        assertEquals(result, vector.opposite());
    }
}
