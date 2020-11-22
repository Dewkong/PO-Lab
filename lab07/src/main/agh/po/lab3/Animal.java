package agh.po.lab3;

import agh.po.lab01.MoveDirection;
import agh.po.lab2.MapDirection;
import agh.po.lab2.Vector2d;
import agh.po.lab4.IWorldMap;
import agh.po.lab5.AbstractWorldMapElement;
import agh.po.lab7.IPositionChangeObserver;
import agh.po.lab7.IPositionChangedPublisher;

import java.util.ArrayList;


public class Animal extends AbstractWorldMapElement implements IPositionChangedPublisher {
    private MapDirection orientation;
    private final IWorldMap map;
    private final ArrayList<IPositionChangeObserver> observerList = new ArrayList<>();

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
        Vector2d oldPosition = position;
        if (map.canMoveTo(newPosition)){
            position = newPosition;
            positionChanged(oldPosition, newPosition);
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

    @Override
    public boolean isPassable() {
        return false;
    }

    @Override
    public void addObserver(IPositionChangeObserver observer) {
        observerList.add(observer);
    }

    @Override
    public void removeObserver(IPositionChangeObserver observer) {
        observerList.remove(observer);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for (IPositionChangeObserver observer : observerList){
            observer.positionChanged(this, oldPosition, newPosition);
        }
    }
}
