package agh.po.lab3;

import agh.po.lab01.MoveDirection;
import agh.po.lab2.MapDirection;
import agh.po.lab2.Vector2d;
import agh.po.lab4.IWorldMap;
import agh.po.lab5.AbstractWorldMapElement;


public class Animal extends AbstractWorldMapElement {
    private MapDirection orientation;
    private final IWorldMap map;

    public MapDirection getOrientation() {
        return orientation;
    }


    public Animal(IWorldMap map){
        this(map, new Vector2d(2,2));
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
