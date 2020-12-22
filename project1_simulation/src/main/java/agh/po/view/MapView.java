package agh.po.view;

import agh.po.element.Animal;
import agh.po.element.EnergyComparator;
import agh.po.map.WorldMap;
import agh.po.movement.Vector2d;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;

public class MapView {
    public final WorldMap map;
    StackPane[][] mapStack;
    private int fieldSize;
    public final LinkedHashMap<Vector2d, Rectangle> animalViews = new LinkedHashMap<>();
    public final LinkedHashMap<Vector2d, Rectangle> plantViews = new LinkedHashMap<>();
    public MapView(WorldMap map){
        this.map = map;
        mapStack = new StackPane[map.getWidth() + 1][map.getHeight() + 1];
    }

    public void addStackToGrid(GridPane grid) {
        for (int x = 0; x <= map.getWidth(); x++) {
            for (int y = 0; y <= map.getHeight(); y++) {
                grid.add(mapStack[x][y], x, y);
            }
        }
    }

    public void buildMapStack(int sceneWidth, int sceneHeight){
        int fieldWidth =  sceneWidth / map.getWidth();
        int fieldHeight =  sceneHeight / map.getHeight();
        fieldSize = Math.min(fieldHeight, fieldWidth);
        for (int x = 0; x <= map.getWidth(); x++){
            for (int y = 0; y <= map.getHeight(); y++){
                StackPane stack = new StackPane();
                Rectangle field = new Rectangle(fieldSize, fieldSize, setFieldColor(x,y));
                field.setStyle("-fx-stroke: black; -fx-stroke-width: 1;");
                stack.getChildren().add(field);
                if (map.plantsOnMap.containsKey(new Vector2d(x,y))){
                    Rectangle plant = new Rectangle(fieldSize, fieldSize, new Color(0, 0.4, 0, 1.0));
                    plant.setArcHeight(fieldHeight);
                    plant.setArcWidth(fieldWidth);
                    stack.getChildren().add(plant);
                    plantViews.put(new Vector2d(x,y), plant);
                }
                if (map.animalsOnMap.containsKey(new Vector2d(x,y)) && map.animalsOnMap.get(new Vector2d(x,y)).size() > 0){
                    Rectangle animal = new Rectangle(fieldSize, fieldSize);
                    animal.setFill(setAnimalColor(x,y));
                    animal.setArcHeight(fieldHeight);
                    animal.setArcWidth(fieldWidth);
                    stack.getChildren().add(animal);
                    animalViews.put(new Vector2d(x,y), animal);
                }
                mapStack[x][y] = stack;
            }
        }
    }

    private Paint setFieldColor(int x, int y) {
        Vector2d field = new Vector2d(x,y);
        if (field.follows(map.jungle.getLowerLimit()) && field.precedes(map.jungle.getUpperLimit())){
            return new Color(0, 0.65, 0, 1.0);
        }
        else if (field.follows(map.getLowerLimit()) && field.precedes(map.getUpperLimit())){
            return new Color(0,1.0,0,1.0);
        }
        else return new Color(0,0,0,1.0);
    }

    private Paint setAnimalColor(int x, int y) {
        Vector2d field = new Vector2d(x,y);
        ArrayList<Animal> animalsOnField = map.animalsOnMap.get(field);
        Collections.sort(animalsOnField, new EnergyComparator());
        Animal animalToShow = animalsOnField.get(0);
        if (animalToShow.getEnergy() <= 0){
            return new Color(1.0, 1.0, 1.0, 1.0);
        }
        else if (animalToShow.getEnergy() <= 3 * map.moveEnergy){
            return new Color(0.6,0.6,1.0,1.0);
        }
        else if (animalToShow.getEnergy() <= 6 * map.moveEnergy){
            return new Color(0.2,0.2,1.0,1.0);
        }
        else return new Color(0, 0, 0.6, 1.0);
    }

    public void removeAnimalView(Vector2d position) {
        if (animalViews.containsKey(position)){
            mapStack[position.x][position.y].getChildren().remove(animalViews.get(position));
            animalViews.remove(position);
            if (map.animalsOnMap.containsKey(position) && map.animalsOnMap.get(position).size() > 0){
                Rectangle animal = new Rectangle(fieldSize, fieldSize);
                animal.setFill(setAnimalColor(position.x,position.y));
                animal.setArcHeight(fieldSize);
                animal.setArcWidth(fieldSize);
                mapStack[position.x][position.y].getChildren().add(animal);
                animalViews.put(position, animal);
            }
        }
    }

    public void addAnimalView(Vector2d position) {
        if (!animalViews.containsKey(position)){
            if (map.animalsOnMap.containsKey(position) && map.animalsOnMap.get(position).size() > 0){
                Rectangle animal = new Rectangle(fieldSize, fieldSize);
                animal.setFill(setAnimalColor(position.x,position.y));
                animal.setArcHeight(fieldSize);
                animal.setArcWidth(fieldSize);
                mapStack[position.x][position.y].getChildren().add(animal);
                animalViews.put(position, animal);
            }
        }
    }

    public void changeAnimalEnergyColor(Animal animal) {
        if (animalViews.containsKey(animal.getPosition())){
            animalViews.get(animal.getPosition()).setFill(setAnimalColor(animal.getPosition().x, animal.getPosition().y));
        }

    }

    public void addPlantView(Vector2d position){
        if (!plantViews.containsKey(position)){
            if (map.plantsOnMap.containsKey(position) && mapStack[position.x][position.y] != null){
                Rectangle plant = new Rectangle(fieldSize, fieldSize, new Color(0, 0.4, 0, 1.0));
                plant.setArcHeight(fieldSize);
                plant.setArcWidth(fieldSize);
                mapStack[position.x][position.y].getChildren().add(plant);
                plantViews.put(position, plant);
            }
        }
    }

    public void removePlantView(Vector2d position){
        if (plantViews.containsKey(position) && mapStack[position.x][position.y] != null){
            mapStack[position.x][position.y].getChildren().remove(plantViews.get(position));
            plantViews.remove(position);
        }
    }
}
