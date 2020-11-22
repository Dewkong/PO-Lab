package agh.po.lab4;

import agh.po.lab2.Vector2d;
import agh.po.lab5.AbstractWorldMap;

import java.util.Optional;

public class RectangularMap extends AbstractWorldMap {
    private final Vector2d lowerLimit;
    private final Vector2d upperLimit;


    public RectangularMap(int width, int height){
        lowerLimit = new Vector2d(0,0);
        upperLimit = new Vector2d(width, height);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return ((position.precedes(lowerLimit)) && (position.follows(upperLimit)) && (!isOccupied(position)));
    }

    @Override
    public Optional<Object> objectAt(Vector2d position) {
        if (animalsOnMap.containsKey(position)){
            return Optional.of(animalsOnMap.get(position));
        }
        return Optional.empty();
    }

    @Override
    public Vector2d getLowerLimit(){
        return lowerLimit;
    }
    @Override
    public Vector2d getUpperLimit(){
        return upperLimit;
    }
}
