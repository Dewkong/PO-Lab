package agh.po.map;

import agh.po.element.Plant;
import agh.po.movement.Vector2d;

import java.util.LinkedHashMap;
import java.util.Random;

public class Jungle extends AbstractWorldMap {

    public Jungle(WorldMap map, double jungleRatio){
        Vector2d middleMap = new Vector2d(map.getWidth()/2, map.getHeight()/2);
        Vector2d radius = new Vector2d((int) ((jungleRatio * map.getWidth())/2), (int) ((jungleRatio * map.getHeight())/2));
        lowerLimit = middleMap.subtract(radius);
        upperLimit = middleMap.add(radius);
        generatePlantPositions();
    }

    @Override
    protected void generatePlantPositions(){
        for (int i = getLowerLimit().x; i <= getUpperLimit().x; i++){
            for (int j = getLowerLimit().y; j <= getUpperLimit().y; j++){
                plantPositions.add(new Vector2d(i,j));
            }
        }
    }


    protected void growPlant(WorldMap map) {
        LinkedHashMap<Vector2d, Plant> plantsOnMap = map.plantsOnMap;
        Random rand = new Random();
        if (plantPositions.size() > 0){
            Vector2d plantPosition = plantPositions.get(rand.nextInt(plantPositions.size()));
            plantPositions.remove(plantPosition);
            plantsOnMap.put(plantPosition, new Plant(plantPosition));
            map.mapView.addPlantView(plantPosition);
            map.mapStats.countPlants(1);
        }
    }
}
