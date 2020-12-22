package agh.po.map;

import agh.po.element.Animal;
import agh.po.movement.Vector2d;

public interface IPositionChangeObserver {
    void positionChanged(Animal movedElement, Vector2d oldPosition, Vector2d newPosition);
}
