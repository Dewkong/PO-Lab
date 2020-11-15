package agh.po.lab5;

import agh.po.lab2.Vector2d;

import java.util.*;

public class GrassField extends AbstractWorldMap {
    protected final LinkedHashMap<Vector2d, Grass> grassOnMap = new LinkedHashMap();

    public GrassField(int amountOfGrass) {
        Random rand = new Random();
        int limit = (int) Math.sqrt(amountOfGrass * 10) + 1;
        int currentAmountOfGrass = 0;
        while (currentAmountOfGrass < amountOfGrass) {
            Grass grass = new Grass(new Vector2d(rand.nextInt(limit), rand.nextInt(limit)));
            if (!grassOnMap.containsKey(grass.getPosition())){
                grassOnMap.put(grass.getPosition(), grass);
                currentAmountOfGrass += 1;
            }
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(isOccupied(position) && !((AbstractWorldMapElement) objectAt(position).get()).isPassable());
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
    protected Vector2d getLowerLimit(){
        HashSet<Vector2d> setOfPositions = new HashSet<>(animalsOnMap.keySet());
        setOfPositions.addAll(grassOnMap.keySet());
        ArrayList<Vector2d> positions = new ArrayList<>(setOfPositions);

        Vector2d lowerLimit = positions.get(0);
        for (Vector2d position : positions){
            lowerLimit = lowerLimit.lowerLeft(position);
        }

        return lowerLimit;
    }

    @Override
    protected Vector2d getUpperLimit(){
        HashSet<Vector2d> setOfPositions = new HashSet<>(grassOnMap.keySet());
        setOfPositions.addAll(grassOnMap.keySet());
        ArrayList<Vector2d> positions = new ArrayList<>(setOfPositions);

        Vector2d upperLimit = positions.get(0);
        for (Vector2d position : positions){
            upperLimit = upperLimit.upperRight(position);
        }

        return upperLimit;
    }
}
