package agh.cs.lab3;

import java.util.LinkedList;

public class World {
    public static void main(String[] args) {
        Animal piesek = new Animal();
        System.out.println(piesek);

        String[] steps = {"f", "forward", "f", "r", "f", "right", "forward", "forward", "f", "left", "f", "b", "to nie jest kierunek"};
        OptionsParser parser = new OptionsParser();
        LinkedList<MoveDirection> stepsParsed = parser.parse(steps);

        for (MoveDirection step : stepsParsed){
            piesek.move(step);
            System.out.println(piesek);
        }
    }
}
