package agh.po;

import agh.po.lab01.MoveDirection;
import agh.po.lab2.Vector2d;
import agh.po.lab3.Animal;
import agh.po.lab3.OptionsParser;
import agh.po.lab4.IWorldMap;
import agh.po.lab5.GrassField;

import java.util.List;

public class World {
    public static void main(String[] args) {
        String[] steps = {"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"};
        List<MoveDirection> directions = new OptionsParser().parse(steps);

        IWorldMap grassMap = new GrassField(10);
        Animal animal1 = new Animal(grassMap);
        grassMap.place(animal1);
        Animal animal2 = new Animal(grassMap, new Vector2d(3, 4));
        grassMap.place(animal2);
        System.out.println(grassMap);
        grassMap.run(directions);
        System.out.println(grassMap);
        try {
            String[] wrongSteps = {"f", "backward", "Kappa", "l"};
            List<MoveDirection> wrongDirections = new OptionsParser().parse(wrongSteps);

        } catch(IllegalArgumentException ex) {
            System.out.println(ex);
        }

        try {
            IWorldMap grassMapEx = new GrassField(10);
            Animal animal1Ex = new Animal(grassMapEx, new Vector2d(3,4));
            grassMapEx.place(animal1Ex);
            Animal animal2Ex = new Animal(grassMapEx, new Vector2d(3, 4));
            grassMapEx.place(animal2Ex);
        } catch(IllegalArgumentException ex) {
            System.out.println(ex);
        }
    }
}
