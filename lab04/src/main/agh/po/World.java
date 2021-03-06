package agh.po;

import agh.po.lab01.MoveDirection;
import agh.po.lab2.MapDirection;
import agh.po.lab2.Vector2d;
import agh.po.lab3.Animal;
import agh.po.lab3.OptionsParser;
import agh.po.lab4.IWorldMap;
import agh.po.lab4.RectangularMap;

import java.util.LinkedList;
import java.util.List;

public class World {
    public static void main(String[] args) {
        String[] steps = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);
        IWorldMap map = new RectangularMap(10, 5);
        Animal animal1 = new Animal(map);
        map.place(animal1);
        Animal animal2 = new Animal(map,new Vector2d(3,4));
        map.place(animal2);
        System.out.println(map);
        map.run(directions);
        System.out.println(map);
    }
}
