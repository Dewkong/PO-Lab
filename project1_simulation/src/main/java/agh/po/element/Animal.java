package agh.po.element;

import agh.po.map.IPositionChangeObserver;
import agh.po.map.WorldMap;
import agh.po.movement.DirectionProcessor;
import agh.po.movement.MapDirection;
import agh.po.movement.Vector2d;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class Animal extends AbstractElement implements IPositionChangedPublisher{
    private MapDirection orientation;
    private int currentEnergy;
    private int childCounter = 0;
    private int daysAlive = 0;
    private final ArrayList<Integer> genes;
    private final WorldMap map;
    private final ArrayList<IPositionChangeObserver> observerList = new ArrayList<>();

    public int getDaysAlive() {
        return daysAlive;
    }

    public void childBorn(){
        childCounter += 1;
    }

    public int getChildren(){
        return childCounter;
    }

    public ArrayList<Integer> getGenes() {
        return genes;
    }

    public int getEnergy() {
        return currentEnergy;
    }

    public void changeEnergy(int energyDifference){
        currentEnergy += energyDifference;
        map.mapView.changeAnimalEnergyColor(this);
        this.map.mapStats.countEnergy(energyDifference);
    }

    public Animal(WorldMap map, int startEnergy){ //kontruktor pierwszych zwierząt
        currentEnergy = startEnergy;
        orientation = DirectionProcessor.generateDirection();
        genes = generateGenes();
        this.map = map;
        setInitialPosition();
        map.place(this);
    }

    public Animal(WorldMap map, int startEnergy, ArrayList<Integer> genes, Vector2d position){ //konstruktor dzieci
        currentEnergy = startEnergy;
        orientation = DirectionProcessor.generateDirection();
        this.genes = genes;
        this.map = map;
        this.position = position;
        map.place(this);
    }


    private void setInitialPosition() {
        Random rand = new Random();
        Vector2d randPosition = new Vector2d(rand.nextInt(map.getWidth()), rand.nextInt(map.getHeight()));
        while (map.isOccupiedByAnimal(randPosition)){
            randPosition = new Vector2d(rand.nextInt(map.getWidth()), rand.nextInt(map.getHeight()));
        }
        position = randPosition;
    }

    private ArrayList<Integer> generateGenes() {
        ArrayList<Integer> randomGenes = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 32; i++){
            randomGenes.add(rand.nextInt(8));
        }
        boolean wasChanged = true;
        while (wasChanged){
            for (int i = 0; i < 7; i ++){
                if (!randomGenes.contains(i)){
                    randomGenes.add(rand.nextInt(32), i);
                    wasChanged = true;
                    break;
                }
                wasChanged = false;
            }
        }
        Collections.sort(randomGenes);
        return randomGenes;
    }


    public void move(){
        orientation = DirectionProcessor.changeDirection(orientation, drawChange());
        Vector2d step = orientation.toUnitVector();
        Vector2d newPosition = position.add(step).fitInLimits(map.getWidth(), map.getHeight());
        Vector2d oldPosition = position;
        position = newPosition;
        positionChanged(oldPosition, newPosition);
        daysAlive += 1;
    }

    private int drawChange() {
        Random rand = new Random();
        return genes.get(rand.nextInt(32));
    }

    @Override
    public String toString() {
        return "x";
    }


    @Override
    public void addObserver(IPositionChangeObserver observer) {
        observerList.add(observer);
    }

    @Override
    public void removeObserver(IPositionChangeObserver observer) {
        observerList.remove(observer);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition){
        for (IPositionChangeObserver observer : observerList){
            observer.positionChanged(this, oldPosition, newPosition);
        }
    }
}