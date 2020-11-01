package agh.po.lab4;

import agh.po.lab01.MoveDirection;
import agh.po.lab2.Vector2d;
import agh.po.lab3.Animal;
import agh.po.lab3.OptionsParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RectangularMapTest {
    @Test
    void placeTestRegular(){
        //given
        String[] steps = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);
        IWorldMap map = new RectangularMap(10, 5);
        //when
        boolean partialResult1 = map.place(new Animal(map));
        boolean partialResult2 = map.place(new Animal(map,new Vector2d(3,4)));
        boolean result = partialResult1 && partialResult2;
        //then
        assertTrue(result);
    }
    @Test
    void placeTestIfOccupied(){
        //given
        String[] steps = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);
        IWorldMap map = new RectangularMap(10, 5);
        //when
        boolean partialResult1 = map.place(new Animal(map));
        boolean partialResult2 = map.place(new Animal(map,new Vector2d(2,2)));
        boolean result = partialResult1 && partialResult2;
        //then
        assertFalse(result);
    }
    @Test
    void placeTestOutOfBoundaries(){
        //given
        String[] steps = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);
        IWorldMap map = new RectangularMap(10, 5);
        //when
        boolean result = map.place(new Animal(map,new Vector2d(12,8)));
        //then
        assertFalse(result);
    }
    @Test
    void isOccupiedTestRegular(){
        //given
        String[] steps = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);
        IWorldMap map = new RectangularMap(10, 5);
        //when
        map.place(new Animal(map,new Vector2d(3,4)));
        boolean result = map.isOccupied(new Vector2d(3,4));
        //then
        assertTrue(result);
    }
    @Test
    void isOccupiedTestOutOfBoundaries(){
        //given
        String[] steps = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);
        IWorldMap map = new RectangularMap(10, 5);
        //when
        map.place(new Animal(map,new Vector2d(12,8)));
        boolean result = map.isOccupied(new Vector2d(12,8));
        //then
        assertFalse(result);
    }
    @Test
    void canMoveToTestRegular(){
        //given
        String[] steps = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);
        IWorldMap map = new RectangularMap(10, 5);
        //when
        map.place(new Animal(map,new Vector2d(3,4)));
        boolean result = map.canMoveTo(new Vector2d(3,5));
        //then
        assertTrue(result);
    }
    @Test
    void canMoveToTestIfOccupied(){
        //given
        String[] steps = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);
        IWorldMap map = new RectangularMap(10, 5);
        //when
        map.place(new Animal(map));
        map.place(new Animal(map,new Vector2d(3,4)));
        boolean result = map.canMoveTo(new Vector2d(2,2));
        //then
        assertFalse(result);
    }
    @Test
    void canMoveToTestOutOfBoundariesUpperLimit(){
        //given
        String[] steps = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);
        IWorldMap map = new RectangularMap(10, 5);
        //when
        map.place(new Animal(map,new Vector2d(3,4)));
        boolean result = map.canMoveTo(new Vector2d(11,6));
        //then
        assertFalse(result);
    }
    @Test
    void canMoveToTestOutOfBoundariesLowerLimit(){
        //given
        String[] steps = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);
        IWorldMap map = new RectangularMap(10, 5);
        //when
        map.place(new Animal(map,new Vector2d(3,4)));
        boolean result = map.canMoveTo(new Vector2d(-1,-2));
        //then
        assertFalse(result);
    }
    @Test
    void runTestRegular(){
        //given
        String[] steps = {"f", "b", "r", "l"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);
        IWorldMap map = new RectangularMap(10, 5);
        Animal animal1 = new Animal(map);
        map.place(animal1);
        Animal animal2 = new Animal(map,new Vector2d(3,4));
        map.place(animal2);
        //when
        map.run(directions);
        Vector2d result1 = animal1.getPosition();
        Vector2d result2 = animal2.getPosition();
        //then
        assertEquals(new Vector2d(2,3), result1);
        assertEquals(new Vector2d(3,3), result2);
    }
    @Test
    void runTestWithConflicts(){
        //given
        String[] steps = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);
        IWorldMap map = new RectangularMap(10, 5);
        Animal animal1 = new Animal(map);
        map.place(animal1);
        Animal animal2 = new Animal(map,new Vector2d(3,4));
        map.place(animal2);
        //when
        map.run(directions);
        Vector2d result1 = animal1.getPosition();
        Vector2d result2 = animal2.getPosition();
        //then
        assertEquals(new Vector2d(2,0), result1);
        assertEquals(new Vector2d(3,5), result2);
    }
    @Test
    void runTestOneAnimal(){
        //given
        String[] steps = {"f", "f", "f", "b", "r", "f", "f", "f", "r", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);
        IWorldMap map = new RectangularMap(10, 5);
        Animal animal = new Animal(map, new Vector2d(3,3));
        map.place(animal);
        //when
        map.run(directions);
        Vector2d result = animal.getPosition();
        //then
        assertEquals(new Vector2d(6,0), result);
    }
    @Test
    void runTestThreeAnimals(){
        //given
        String[] steps = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);
        IWorldMap map = new RectangularMap(10, 5);
        Animal animal1 = new Animal(map);
        map.place(animal1);
        Animal animal2 = new Animal(map,new Vector2d(3,4));
        map.place(animal2);
        map.run(directions);

        Animal animal3 = new Animal(map, new Vector2d(3,3));
        map.place(animal3);
        String[] stepsMore = {"r", "r", "r", "r", "r", "r", "f", "f", "r", "f", "f", "r", "f", "f",
        "f", "r", "f", "f", "f", "f", "f"};
        List<MoveDirection> directionsMore = new OptionsParser().parse(stepsMore);
        map.run(directionsMore);
        //when
        Vector2d result1 = animal1.getPosition();
        Vector2d result2 = animal2.getPosition();
        Vector2d result3 = animal3.getPosition();
        //then
        assertEquals(new Vector2d(2,3), result1);
        assertEquals(new Vector2d(3,4), result2);
        assertEquals(new Vector2d(3,3), result3);
    }
}
