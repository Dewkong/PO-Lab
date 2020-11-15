package agh.po.lab5;

import agh.po.lab01.MoveDirection;
import agh.po.lab2.Vector2d;
import agh.po.lab3.Animal;
import agh.po.lab4.IWorldMap;
import agh.po.lab4.MapVisualiser;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

abstract public class AbstractWorldMap implements IWorldMap {
    protected final LinkedHashMap<Vector2d, Animal> animalsOnMap = new LinkedHashMap();

    @Override
    public void place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
            animalsOnMap.put(animal.getPosition(), animal);
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
            Vector2d prevPosition = currentAnimal.getPosition();
            currentAnimal.move(direction);
            if (!(prevPosition.equals(currentAnimal.getPosition()))){
                animalsOnMap.remove(prevPosition);
                animalsOnMap.put(currentAnimal.getPosition(), currentAnimal);
            }
            i = (i + 1) % length;
            currentAnimal = animalArrayList.get(i);
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position).isPresent();
    }

    protected abstract Vector2d getLowerLimit();
    protected abstract Vector2d getUpperLimit();

    @Override
    public String toString() {
        return new MapVisualiser(this).draw(getLowerLimit(), getUpperLimit());
    }
}
