package agh.cs.lab3;

import agh.cs.lab3.MoveDirection;

import java.util.LinkedList;

public class OptionsParser {
    public LinkedList<MoveDirection> parse(String[] args){
        LinkedList<MoveDirection> result = new LinkedList<>();
        for (String arg : args){
            switch(arg){
                case "f", "forward" -> {
                    result.add(MoveDirection.FORWARD);
                }
                case "b", "backward" -> {
                    result.add(MoveDirection.BACKWARD);
                }
                case "l", "left" -> {
                    result.add(MoveDirection.LEFT);
                }
                case "r", "right" -> {
                    result.add(MoveDirection.RIGHT);
                }
            }
        }
        return result;
    }
}
