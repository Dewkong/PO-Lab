package agh.po.lab5;

import agh.po.lab2.Vector2d;
import agh.po.lab3.Animal;

import java.util.*;

public class GrassField extends AbstractWorldMap {
    public GrassField(int amountOfGrass) {
        Random rand = new Random();
        int limit = (int) Math.sqrt(amountOfGrass * 10) + 1;
        int currentAmountOfGrass = 0;
        while (currentAmountOfGrass < amountOfGrass) {
            Grass grass = new Grass(new Vector2d(rand.nextInt(limit), rand.nextInt(limit)));
            if (!elementsOnMap.contains(grass)){
                elementsOnMap.add(grass);
                currentAmountOfGrass += 1;
            }
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(isOccupied(position) && objectAt(position).get() instanceof Animal);
    }


    @Override
    public Optional<Object> objectAt(Vector2d position) {
        Optional<Object> optionalGrass = Optional.empty();
        for (AbstractWorldMapElement element : elementsOnMap){
            if (element.getPosition().equals(position) && element instanceof Animal){
                return Optional.of(element);
            }
            else if (element.getPosition().equals(position)){
                optionalGrass = Optional.of(element);
            }
        }
        return optionalGrass;
    }

    @Override
    protected Vector2d getLowerLimit(){
        Vector2d lowerLimit = new Vector2d(0,0);

        for (AbstractWorldMapElement element : elementsOnMap){
            lowerLimit = lowerLimit.lowerLeft(element.getPosition());
        }

        return lowerLimit;
    }

    @Override
    protected Vector2d getUpperLimit(){
        Vector2d upperLimit = new Vector2d(0,0);

        for (AbstractWorldMapElement element : elementsOnMap){
            upperLimit = upperLimit.upperRight(element.getPosition());
        }

        return upperLimit;
    }
}
