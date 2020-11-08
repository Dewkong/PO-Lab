package agh.po.lab3;

import agh.po.lab01.MoveDirection;
import agh.po.lab2.MapDirection;
import agh.po.lab2.Vector2d;
import agh.po.lab4.RectangularMap;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class Lab3Test {
    @Test
    void testOrientation1(){
        //given
        Animal piesek = new Animal(new RectangularMap(4,4));
        String[] steps = {"r", "r", "l", "r"};
        OptionsParser parser = new OptionsParser();
        LinkedList<MoveDirection> stepsParsed = parser.parse(steps);

        for (MoveDirection step : stepsParsed){
            piesek.move(step);
        }
        //when
        MapDirection result = MapDirection.SOUTH;
        //then
        assertEquals(result, piesek.getOrientation());
    }

    @Test
    void testOrientation2(){
        //given
        Animal piesek = new Animal(new RectangularMap(4,4));
        String[] steps = {"f", "r", "f", "right", "forward", "left", "b"};
        OptionsParser parser = new OptionsParser();
        LinkedList<MoveDirection> stepsParsed = parser.parse(steps);

        for (MoveDirection step : stepsParsed){
            piesek.move(step);
        }
        //when
        MapDirection result = MapDirection.EAST;
        //then
        assertEquals(result, piesek.getOrientation());
    }

    @Test
    void testOrientation3(){
        //given
        Animal piesek = new Animal(new RectangularMap(4,4));
        String[] steps = {"f", "forward", "f", "r", "f", "right", "forward", "forward", "f", "left", "f", "b", "to nie jest kierunek"};
        OptionsParser parser = new OptionsParser();
        LinkedList<MoveDirection> stepsParsed = parser.parse(steps);

        for (MoveDirection step : stepsParsed){
            piesek.move(step);
        }
        //when
        MapDirection result = MapDirection.EAST;
        //then
        assertEquals(result, piesek.getOrientation());
    }

    @Test
    void testPosition1(){
        //given
        Animal piesek = new Animal(new RectangularMap(4,4));
        String[] steps = {"f", "f", "b", "f"};
        OptionsParser parser = new OptionsParser();
        LinkedList<MoveDirection> stepsParsed = parser.parse(steps);

        for (MoveDirection step : stepsParsed){
            piesek.move(step);
        }
        //when
        Vector2d result = new Vector2d(2,4);
        //then
        assertEquals(result, piesek.getPosition());
    }

    @Test
    void testPosition2(){
        //given
        Animal piesek = new Animal(new RectangularMap(4,4));
        String[] steps = {"f", "r", "f", "right", "forward", "f", "left", "b"};
        OptionsParser parser = new OptionsParser();
        LinkedList<MoveDirection> stepsParsed = parser.parse(steps);

        for (MoveDirection step : stepsParsed){
            piesek.move(step);
        }
        //when
        Vector2d result = new Vector2d(2,1);
        //then
        assertEquals(result, piesek.getPosition());
    }

    @Test
    void testPosition3(){
        //given
        Animal piesek = new Animal(new RectangularMap(4,4));
        String[] steps = {"f", "forward", "f", "r", "f", "right", "forward", "forward", "f", "left", "f", "b", "to nie jest kierunek"};
        OptionsParser parser = new OptionsParser();
        LinkedList<MoveDirection> stepsParsed = parser.parse(steps);

        for (MoveDirection step : stepsParsed){
            piesek.move(step);
        }
        //when
        Vector2d result = new Vector2d(3,1);
        //then
        assertEquals(result, piesek.getPosition());
    }

    @Test
    void testOutOfMapLower1(){
        //given
        Animal piesek = new Animal(new RectangularMap(4,4));
        String[] steps = {"b", "b", "l", "f", "f", "f"};
        OptionsParser parser = new OptionsParser();
        LinkedList<MoveDirection> stepsParsed = parser.parse(steps);

        for (MoveDirection step : stepsParsed){
            piesek.move(step);
        }
        //when
        boolean result = piesek.getPosition().precedes(new Vector2d(0,0));
        //then
        assertTrue(result);
    }

    @Test
    void testOutOfMapLower2(){
        //given
        Animal piesek = new Animal(new RectangularMap(4,4));
        String[] steps = {"right", "r", "f", "f", "f", "forward"};
        OptionsParser parser = new OptionsParser();
        LinkedList<MoveDirection> stepsParsed = parser.parse(steps);

        for (MoveDirection step : stepsParsed){
            piesek.move(step);
        }
        //when
        boolean result = piesek.getPosition().precedes(new Vector2d(0,0));
        //then
        assertTrue(result);
    }

    @Test
    void testOutOfMapUpper1(){
        //given
        Animal piesek = new Animal(new RectangularMap(4,4));
        String[] steps = {"f", "f", "r", "f", "f", "f"};
        OptionsParser parser = new OptionsParser();
        LinkedList<MoveDirection> stepsParsed = parser.parse(steps);

        for (MoveDirection step : stepsParsed){
            piesek.move(step);
        }
        //when
        boolean result = piesek.getPosition().follows(new Vector2d(4,4));
        //then
        assertTrue(result);
    }

    @Test
    void testOutOfMapUpper2(){
        //given
        Animal piesek = new Animal(new RectangularMap(4,4));
        String[] steps = {"f", "f", "f", "forward"};
        OptionsParser parser = new OptionsParser();
        LinkedList<MoveDirection> stepsParsed = parser.parse(steps);

        for (MoveDirection step : stepsParsed){
            piesek.move(step);
        }
        //when
        boolean result = piesek.getPosition().follows(new Vector2d(4,4));
        //then
        assertTrue(result);
    }

    @Test
    void testOutOfMapBoth(){
        //given
        Animal piesek = new Animal(new RectangularMap(4,4));
        String[] steps = {"f", "forward", "f", "r", "f", "right", "forward", "forward", "f", "left", "f", "f", "b", "to nie jest kierunek"};
        OptionsParser parser = new OptionsParser();
        LinkedList<MoveDirection> stepsParsed = parser.parse(steps);

        for (MoveDirection step : stepsParsed) {
            piesek.move(step);
        }
        //when
        boolean result = piesek.getPosition().follows(new Vector2d(4,4)) && piesek.getPosition().precedes(new Vector2d(0, 0));
        //then
        assertTrue(result);
    }

    @Test
    void testParserPosition(){
        //given
        Animal piesek = new Animal(new RectangularMap(4,4));
        String[] steps = {"zrob krok", "f", "do przodu!", "forward", "aport", "backward", "WidePeepoHappy"};
        OptionsParser parser = new OptionsParser();
        LinkedList<MoveDirection> stepsParsed = parser.parse(steps);

        for (MoveDirection step : stepsParsed) {
            piesek.move(step);
        }
        //when
        Vector2d result = new Vector2d(2,3);
        //then
        assertEquals(result, piesek.getPosition());
    }

    @Test
    void testParserOrientation(){
        //given
        Animal piesek = new Animal(new RectangularMap(4,4));
        String[] steps = {"dobry piesek", "r", "zly piesek", "right", "siad", "left", "pepeD"};
        OptionsParser parser = new OptionsParser();
        LinkedList<MoveDirection> stepsParsed = parser.parse(steps);

        for (MoveDirection step : stepsParsed) {
            piesek.move(step);
        }
        //when
        MapDirection result = MapDirection.EAST;
        //then
        assertEquals(result, piesek.getOrientation());
    }
}