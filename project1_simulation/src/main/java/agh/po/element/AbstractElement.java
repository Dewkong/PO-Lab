package agh.po.element;

import agh.po.movement.Vector2d;

public abstract class AbstractElement {
    protected Vector2d position;

    public Vector2d getPosition() {
        return position;
    }
}
