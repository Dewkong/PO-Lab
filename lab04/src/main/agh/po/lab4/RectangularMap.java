package agh.po.lab4;

import agh.po.lab01.MoveDirection;
import agh.po.lab2.Vector2d;
import agh.po.lab3.Animal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RectangularMap implements IWorldMap {
    private ArrayList<Animal> animalsOnMap = new ArrayList<>();
    private final Vector2d lowerLimit;
    private final Vector2d upperLimit;


    public RectangularMap(int width, int height){
        lowerLimit = new Vector2d(0,0);
        upperLimit = new Vector2d(width, height);
    }

    public boolean canMoveTo(Vector2d position) {
        return ((position.precedes(lowerLimit)) && (position.follows(upperLimit)) && (!isOccupied(position)));
    }

    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())){
            animalsOnMap.add(animal);
            return true;
        }
        else return false;
    }

    public void run(List<MoveDirection> directions) {
        int i = 0;
        int length = animalsOnMap.size();
        Animal currentAnimal = animalsOnMap.get(i);
        for (MoveDirection direction : directions){
            currentAnimal.move(direction);
            i = (i + 1) % length;
            currentAnimal = animalsOnMap.get(i);
        }
    }

    public boolean isOccupied(Vector2d position) {
        return objectAt(position).isPresent();
    }

    public Optional<Object> objectAt(Vector2d position) {
        for (Animal animal : animalsOnMap){
            if (animal.getPosition().equals(position)){
                return Optional.of(animal);
            }
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        return new MapVisualiser(this).draw(lowerLimit, upperLimit);
    }
}
