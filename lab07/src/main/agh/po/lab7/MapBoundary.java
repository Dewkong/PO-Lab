package agh.po.lab7;

import agh.po.lab2.Vector2d;
import agh.po.lab5.IWorldElement;

import java.util.Collections;
import java.util.LinkedList;

public class MapBoundary implements IPositionChangeObserver{
    protected final LinkedList<IWorldElement> sortedX = new LinkedList<>();
    protected final LinkedList<IWorldElement> sortedY = new LinkedList<>();

    public Vector2d getLower(){
        return new Vector2d(sortedX.getFirst().getPosition().getX(), sortedY.getFirst().getPosition().getY());
    }

    public Vector2d getUpper(){
        return new Vector2d(sortedX.getLast().getPosition().getX(), sortedY.getLast().getPosition().getY());
    }

    public void addElement(IWorldElement element){
        sortedX.add(element);
        Collections.sort(sortedX, new Sortbyx());
        sortedY.add(element);
        Collections.sort(sortedY, new Sortbyy());
    }

    @Override
    public void positionChanged(IWorldElement movedElement, Vector2d oldPosition, Vector2d newPosition) {
        if (oldPosition.getX() == sortedX.getFirst().getPosition().getX() || oldPosition.getX() == sortedX.getLast().getPosition().getX()){
            Collections.sort(sortedX, new Sortbyx());
        }
        if (oldPosition.getY() == sortedY.getFirst().getPosition().getY() || oldPosition.getY() == sortedY.getLast().getPosition().getY()){
            Collections.sort(sortedY, new Sortbyy());
        }
    }
}
