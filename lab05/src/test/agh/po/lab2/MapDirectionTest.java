package agh.po.lab2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MapDirectionTest {
    @Test
    void nextTest(){
        //given
        MapDirection direction = MapDirection.WEST;
        //when
        MapDirection result = MapDirection.NORTH;
        //then
        assertEquals(result, direction.next());

        //given
        direction = MapDirection.NORTH;
        //when
        result = MapDirection.EAST;
        //then
        assertEquals(result, direction.next());

        //given
        direction = MapDirection.EAST;
        //when
        result = MapDirection.SOUTH;
        //then
        assertEquals(result, direction.next());

        //given
        direction = MapDirection.SOUTH;
        //when
        result = MapDirection.WEST;
        //then
        assertEquals(result, direction.next());
    }

    @Test
    void previousTest(){
        //given
        MapDirection direction = MapDirection.WEST;
        //when
        MapDirection result = MapDirection.SOUTH;
        //then
        assertEquals(result, direction.previous());

        //given
        direction = MapDirection.NORTH;
        //when
        result = MapDirection.WEST;
        //then
        assertEquals(result, direction.previous());

        //given
        direction = MapDirection.EAST;
        //when
        result = MapDirection.NORTH;
        //then
        assertEquals(result, direction.previous());

        //given
        direction = MapDirection.SOUTH;
        //when
        result = MapDirection.EAST;
        //then
        assertEquals(result, direction.previous());
    }
}