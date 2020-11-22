package agh.po.lab5;

import agh.po.lab01.MoveDirection;
import agh.po.lab2.Vector2d;
import agh.po.lab3.Animal;
import agh.po.lab3.OptionsParser;
import agh.po.lab4.IWorldMap;
import agh.po.lab4.RectangularMap;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GrassFieldTest {
    @Test
    void placeTestRegular(){
        //given
        String[] steps = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);
        IWorldMap map = new GrassField(10);
        //when
        map.place(new Animal(map));
        //then
        assertDoesNotThrow(() -> {
            map.place(new Animal(map,new Vector2d(3,4)));
        });
    }
    @Test
    void placeTestIfOccupiedByAnimal(){
        //given
        String[] steps = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);
        IWorldMap map = new GrassField(10);
        //when
        map.place(new Animal(map));
        //then
        assertThrows(IllegalArgumentException.class, () -> {
            map.place(new Animal(map,new Vector2d(2,2)));
        });
    }
    @Test
    void placeTestIfOccupiedByGrass(){
        //given
        String[] steps = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);
        IWorldMap map = new GrassField(1);
        //then
        assertDoesNotThrow(() -> {
            for (int i = 0; i < 4; i++){
                for (int j = 0; j < 4; j++){
                    //when
                    map.place(new Animal(map, new Vector2d(i,j)));
                }
            }
        });
    }
    @Test
    void isPassableTestWithGrass(){
        //given
        String[] steps = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);
        IWorldMap map = new GrassField(1);
        //when
        boolean result = true;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                map.place(new Animal(map, new Vector2d(i, j)));
                    if (((IWorldElement) map.objectAt(new Vector2d(i, j)).get()).isPassable()) {
                        result = false;
                    }
                }
            }
        //then
        assertTrue(result);
    }
    @Test
    void isOccupiedTestByAnimal(){
        //given
        String[] steps = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);
        IWorldMap map = new GrassField(10);
        //when
        map.place(new Animal(map,new Vector2d(3,4)));
        boolean result = map.isOccupied(new Vector2d(3,4));
        //then
        assertTrue(result);
    }
    @Test
    void isOccupiedTestByGrass(){
        //given
        String[] steps = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);
        IWorldMap map = new GrassField(1);
        //when
        boolean result = false;
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (map.isOccupied(new Vector2d(i,j))){
                    result = true;
                }
            }
        }
        //then
        assertTrue(result);
    }
    @Test
    void canMoveToTestRegular(){
        //given
        String[] steps = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);
        IWorldMap map = new GrassField(10);
        //when
        map.place(new Animal(map,new Vector2d(3,4)));
        boolean result = map.canMoveTo(new Vector2d(3,5));
        //then
        assertTrue(result);
    }
    @Test
    void canMoveToTestIfOccupiedByAnimal(){
        //given
        String[] steps = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);
        IWorldMap map = new GrassField(10);
        //when
        map.place(new Animal(map));
        map.place(new Animal(map,new Vector2d(3,4)));
        boolean result = map.canMoveTo(new Vector2d(2,2));
        //then
        assertFalse(result);
    }
    @Test
    void canMoveToTestIfOccupiedByGrass(){
        //given
        String[] steps = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);
        IWorldMap map = new GrassField(1);
        //when
        boolean result = true;
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                if (!map.canMoveTo(new Vector2d(i,j))){
                    result = false;
                }
            }
        }
        //then
        assertTrue(result);
    }
    @Test
    void runTestRegular(){
        //given
        String[] steps = {"f", "b", "r", "l"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);
        IWorldMap map = new GrassField(10);
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
        IWorldMap map = new GrassField(10);
        Animal animal1 = new Animal(map);
        map.place(animal1);
        Animal animal2 = new Animal(map,new Vector2d(3,4));
        map.place(animal2);
        //when
        map.run(directions);
        Vector2d result1 = animal1.getPosition();
        Vector2d result2 = animal2.getPosition();
        //then
        assertEquals(new Vector2d(2,-1), result1);
        assertEquals(new Vector2d(3,7), result2);
    }
    @Test
    void runTestOneAnimal(){
        //given
        String[] steps = {"f", "f", "f", "b", "r", "f", "f", "f", "r", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);
        IWorldMap map = new GrassField(10);
        Animal animal = new Animal(map, new Vector2d(3,3));
        map.place(animal);
        //when
        map.run(directions);
        Vector2d result = animal.getPosition();
        //then
        assertEquals(new Vector2d(6,-1), result);
    }
    @Test
    void observerTest(){
        //given
        String[] steps = {"f"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);
        IWorldMap map = new GrassField(10);
        Animal animal = new Animal(map, new Vector2d(3,3));
        map.place(animal);
        //when
        animal.move(MoveDirection.FORWARD);
        map.run(directions);
        Vector2d result = animal.getPosition();
        //then
        assertEquals(new Vector2d(3,5), result);
    }
    @Test
    void lowerLimitTest(){
        //given
        AbstractWorldMap map = new GrassField(0);
        Animal animal1 = new Animal(map);
        map.place(animal1);
        Animal animal2 = new Animal(map, new Vector2d(2, 0));
        map.place(animal2);
        Animal animal3 = new Animal(map, new Vector2d(0, 2));
        map.place(animal3);
        //when
        Vector2d result = map.getLowerLimit();
        //then
        assertEquals(new Vector2d(0 , 0), result);
    }
    @Test
    void upperLimitTest(){
        //given
        AbstractWorldMap map = new GrassField(0);
        Animal animal1 = new Animal(map, new Vector2d(0,0));
        map.place(animal1);
        Animal animal2 = new Animal(map, new Vector2d(2, 0));
        map.place(animal2);
        Animal animal3 = new Animal(map, new Vector2d(0, 2));
        map.place(animal3);
        //when
        Vector2d result = map.getUpperLimit();
        //then
        assertEquals(new Vector2d(2 , 2), result);
    }
}
