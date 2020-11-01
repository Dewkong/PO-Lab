package agh.po.lab3;

import agh.po.lab01.MoveDirection;
import agh.po.lab2.MapDirection;
import agh.po.lab2.Vector2d;
import agh.po.lab4.IWorldMap;


public class Animal {
    private MapDirection orientation;
    private Vector2d position;
    private IWorldMap map;

    public MapDirection getOrientation() {
        return orientation;
    }

    public Vector2d getPosition() {
        return position;
    }


    public Animal(IWorldMap map){
        this.map = map;
        this.position = new Vector2d(2,2);
        this.orientation = MapDirection.NORTH;
    }

    public Animal(IWorldMap map, Vector2d initialPosition){
        this.map = map;
        this.position = initialPosition;
        this.orientation = MapDirection.NORTH;
    }

    protected void moveIfPossible(Vector2d step){
        Vector2d newPosition = position.add(step);
        if (map.canMoveTo(newPosition)){
            position = newPosition;
        }
    }
    public void move(MoveDirection direction){
        switch(direction){
            case RIGHT -> orientation = orientation.next();
            case LEFT -> orientation = orientation.previous();
            case FORWARD -> moveIfPossible(orientation.toUnitVector());
            case BACKWARD -> moveIfPossible(orientation.toUnitVector().opposite());
        }
    }

    @Override
    public String toString() {
        return switch(orientation){
            case NORTH -> "^";
            case EAST -> ">";
            case SOUTH -> "v";
            case WEST -> "<";
        };
    }
}
