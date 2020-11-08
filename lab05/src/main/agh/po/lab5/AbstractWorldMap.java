package agh.po.lab5;

import agh.po.lab01.MoveDirection;
import agh.po.lab2.Vector2d;
import agh.po.lab3.Animal;
import agh.po.lab4.IWorldMap;
import agh.po.lab4.MapVisualiser;

import java.util.ArrayList;
import java.util.List;

abstract public class AbstractWorldMap implements IWorldMap {
    protected final ArrayList<AbstractWorldMapElement> elementsOnMap = new ArrayList<>();

    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
            elementsOnMap.add(animal);
            return true;
        }
        else return false;
    }

    @Override
    public void run(List<MoveDirection> directions) {
        ArrayList<Animal> animalsOnMap = new ArrayList<>();
        for (AbstractWorldMapElement element : elementsOnMap){
            if (element instanceof Animal) {
                animalsOnMap.add((Animal) element);
            }
        }

        int i = 0;
        int length = animalsOnMap.size();
        Animal currentAnimal = animalsOnMap.get(i);
        for (MoveDirection direction : directions){
            currentAnimal.move(direction);
            i = (i + 1) % length;
            currentAnimal = animalsOnMap.get(i);
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
