package agh.po.lab5;

import agh.po.lab2.Vector2d;

import java.util.*;

public class GrassField extends AbstractWorldMap {
    protected final LinkedHashMap<Vector2d, Grass> grassOnMap = new LinkedHashMap<>();

    public GrassField(int amountOfGrass) {
        Random rand = new Random();
        int limit = (int) Math.sqrt(amountOfGrass * 10) + 1;
        int currentAmountOfGrass = 0;
        while (currentAmountOfGrass < amountOfGrass) {
            Grass grass = new Grass(new Vector2d(rand.nextInt(limit), rand.nextInt(limit)));
            if (!grassOnMap.containsKey(grass.getPosition())){
                grassOnMap.put(grass.getPosition(), grass);
                boundary.addElement(grass);
                currentAmountOfGrass += 1;
            }
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(isOccupied(position) && !((IWorldElement) objectAt(position).get()).isPassable());
    }


    @Override
    public Optional<Object> objectAt(Vector2d position) {
        if (animalsOnMap.containsKey(position)){
            return Optional.of(animalsOnMap.get(position));
        }

        if (grassOnMap.containsKey(position)){
            return Optional.of(grassOnMap.get(position));
        }

        return Optional.empty();
    }

    @Override
    public Vector2d getLowerLimit(){
        return boundary.getLower();
    }

    @Override
    public Vector2d getUpperLimit() {
        return boundary.getUpper();
    }
}
