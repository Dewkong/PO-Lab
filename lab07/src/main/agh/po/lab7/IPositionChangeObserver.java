package agh.po.lab7;

import agh.po.lab2.Vector2d;
import agh.po.lab5.IWorldElement;

public interface IPositionChangeObserver {
    void positionChanged(IWorldElement movedElement, Vector2d oldPosition, Vector2d newPosition);
}
