package agh.po.lab5;

import agh.po.lab01.MoveDirection;
import agh.po.lab2.Vector2d;
import agh.po.lab3.Animal;
import agh.po.lab4.IWorldMap;
import agh.po.lab4.MapVisualiser;
import agh.po.lab7.IPositionChangeObserver;
import agh.po.lab7.MapBoundary;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

abstract public class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected final LinkedHashMap<Vector2d, Animal> animalsOnMap = new LinkedHashMap();
    protected final MapBoundary boundary = new MapBoundary();


    @Override
    public void place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
            animalsOnMap.put(animal.getPosition(), animal);
            animal.addObserver(this);
            boundary.addElement(animal);
            animal.addObserver(boundary);
        }
        else throw new IllegalArgumentException("Object cannot be placed on " + animal.getPosition());
    }

    @Override
    public void run(List<MoveDirection> directions) {
        ArrayList<Animal> animalArrayList = new ArrayList<>(animalsOnMap.values());

        int i = 0;
        int length = animalArrayList.size();
        Animal currentAnimal = animalArrayList.get(i);
        for (MoveDirection direction : directions){
            currentAnimal.move(direction);
            i = (i + 1) % length;
            currentAnimal = animalArrayList.get(i);
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position).isPresent();
    }

    public abstract Vector2d getLowerLimit();
    public abstract Vector2d getUpperLimit();

    @Override
    public String toString() {
        return new MapVisualiser(this).draw(getLowerLimit(), getUpperLimit());
    }

    @Override
    public void positionChanged(IWorldElement movedElement, Vector2d oldPosition, Vector2d newPosition){
        animalsOnMap.remove(oldPosition);
        animalsOnMap.put(newPosition, (Animal) movedElement);
    }
}
