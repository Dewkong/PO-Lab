package agh.po.lab5;

import agh.po.lab2.Vector2d;

public abstract class AbstractWorldMapElement implements IWorldElement{
    protected Vector2d position;

    public Vector2d getPosition() {
        return position;
    }
}
