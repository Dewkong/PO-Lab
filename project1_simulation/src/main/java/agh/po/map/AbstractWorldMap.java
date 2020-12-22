package agh.po.map;


import agh.po.movement.Vector2d;
import java.util.ArrayList;



public abstract class AbstractWorldMap{
    protected Vector2d lowerLimit;
    protected Vector2d upperLimit;
    public final ArrayList<Vector2d> plantPositions = new ArrayList<>();

    protected abstract void generatePlantPositions();

    public int getWidth(){
        return upperLimit.x - lowerLimit.x;
    }

    public int getHeight(){
        return upperLimit.y - lowerLimit.y;
    }

    public Vector2d getLowerLimit(){
        return lowerLimit;
    }

    public Vector2d getUpperLimit(){
        return upperLimit;
    }
}
