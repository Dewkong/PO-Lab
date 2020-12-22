package agh.po.map;

import agh.po.element.Animal;
import agh.po.element.EnergyComparator;
import agh.po.element.Plant;
import agh.po.movement.MapDirection;
import agh.po.movement.Vector2d;
import agh.po.view.MapView;

import java.util.*;

public class WorldMap extends AbstractWorldMap implements IPositionChangeObserver {
    public final LinkedHashMap<Vector2d, ArrayList<Animal>> animalsOnMap = new LinkedHashMap<>();
    public final LinkedHashMap<Vector2d, Plant> plantsOnMap = new LinkedHashMap<>();
    public final Jungle jungle;
    public final int moveEnergy;
    public final int plantEnergy;
    public final int startEnergy;
    public final int startAnimals;
    public final MapView mapView;
    public final MapStats mapStats;

    public WorldMap(int width, int height, double jungleRatio, int moveEnergy, int plantEnergy, int startEnergy, int startAnimals) {
        lowerLimit = new Vector2d(0,0);
        upperLimit = new Vector2d(width - 1, height - 1);
        jungle = new Jungle(this, jungleRatio);
        generatePlantPositions();
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.startEnergy = startEnergy;
        this.startAnimals = startAnimals;
        this.mapView = new MapView(this);
        this.mapStats = new MapStats(this);
    }

    @Override
    protected void generatePlantPositions(){
        for (int i = getLowerLimit().x; i <= getUpperLimit().x; i++){
            for (int j = getLowerLimit().y; j <= getUpperLimit().y; j++){
                Vector2d possible = new Vector2d(i,j);
                if (possible.precedesOnOneAxis(jungle.getLowerLimit()) || possible.followsOnOneAxis(jungle.getUpperLimit())){
                    plantPositions.add(possible);
                }
            }
        }
    }

    protected void growPlant() {
        Random rand = new Random();
        if (plantPositions.size() > 0){
            Vector2d plantPosition = plantPositions.get(rand.nextInt(plantPositions.size()));
            plantPositions.remove(plantPosition);
            plantsOnMap.put(plantPosition, new Plant(plantPosition));
            this.mapView.addPlantView(plantPosition);
            this.mapStats.countPlants(1);
        }
    }

    public void growPlants(){
        this.growPlant();
        jungle.growPlant(this);
    }

    private void removePlantPosition(Vector2d position){
        if (position.follows(jungle.getLowerLimit()) && position.precedes(jungle.getUpperLimit())){
            jungle.plantPositions.remove(position);
        }
        else{
            plantPositions.remove(position);
        }
    }

    private void addPlantPosition(Vector2d position){
        if (position.follows(jungle.getLowerLimit()) && position.precedes(jungle.getUpperLimit())){
            jungle.plantPositions.add(position);
        }
        else{
            plantPositions.add(position);
        }
    }

    private void addAnimalToMap(Animal animal, Vector2d newPosition){
        if (animalsOnMap.containsKey(newPosition)){
            animalsOnMap.get(newPosition).add(animal);
        }
        else{
            ArrayList<Animal> animalsList = new ArrayList<>();
            animalsList.add(animal);
            animalsOnMap.put(newPosition, animalsList);
        }
    }

    private void removeAnimalFromMap(Animal animal){
        if (animalsOnMap.get(animal.getPosition()).size() == 1){
            animalsOnMap.remove(animal.getPosition());
        }
        else if (animalsOnMap.get(animal.getPosition()).size() > 1){
            animalsOnMap.get(animal.getPosition()).remove(animal);
        }
        animal.removeObserver(this);
        this.mapStats.countAnimal(-1);
    }

    public void place(Animal animal) {
        if (animal.getPosition().follows(lowerLimit) && animal.getPosition().precedes(upperLimit)){
            addAnimalToMap(animal, animal.getPosition());
            animal.addObserver(this);
            removePlantPosition(animal.getPosition());
            this.mapStats.countAnimal(1);
        }
    }

    public void run() {
        ArrayList<ArrayList<Animal>> animalArrayLists = new ArrayList<>(animalsOnMap.values());
        for (ArrayList<Animal> animals : animalArrayLists) {
            ArrayList<Animal> animalArrayList = new ArrayList<>(animals);
            for (Animal animal : animalArrayList) {
                if (animal.getEnergy() > 0){
                    animal.move();
                    animal.changeEnergy(-moveEnergy);
                }
            }
        }
    }

    public void eatPlants(){
        List<Vector2d> positions = new ArrayList<>(animalsOnMap.keySet());
        for (Vector2d position : positions){
            eat(position);
        }
    }

    private void eat(Vector2d position){
        if (plantsOnMap.containsKey(position) && animalsOnMap.containsKey(position)){
            ArrayList<Animal> animalsWhoEat = animalsWhoEat(animalsOnMap.get(position));
            int energyPortion = plantEnergy / animalsWhoEat.size();
            for (Animal animal : animalsWhoEat){
                animal.changeEnergy(energyPortion);
            }
            plantsOnMap.remove(position);
            addPlantPosition(position);
            this.mapView.removePlantView(position);
            this.mapStats.countPlants(-1);
        }
    }

    private ArrayList<Animal> animalsWhoEat(ArrayList<Animal> animalsOnPosition){
        Collections.sort(animalsOnPosition, new EnergyComparator());
        ArrayList<Animal> animalsWhoEat = new ArrayList<>();
        int maxEnergy = animalsOnPosition.get(0).getEnergy();
        for (Animal animal : animalsOnPosition){
            if (animal.getEnergy() == maxEnergy){
                animalsWhoEat.add(animal);
            }
            else if (animal.getEnergy() < maxEnergy){
                break;
            }
        }
        return animalsWhoEat;
    }

    public void breedAnimals(){
        List<Vector2d> positions = new ArrayList<>(animalsOnMap.keySet());
        for (Vector2d position : positions){
            breed(position);
        }
    }

    private void breed(Vector2d position){
        if (animalsOnMap.containsKey(position) && animalsOnMap.get(position).size() >= 2){
            ArrayList<Animal> animalsWhoBreed = animalsWhoBreed(animalsOnMap.get(position));
            Animal parent1 = animalsWhoBreed.get(0);
            Animal parent2 = animalsWhoBreed.get(1);

            Random rand = new Random();
            int index1 = rand.nextInt(30) + 1;
            int index2 = rand.nextInt(31 - index1) + index1 + 1;

            ArrayList<Integer> genesForChild = new ArrayList<>();
            genesForChild.addAll(parent1.getGenes().subList(0, index1));
            genesForChild.addAll(parent2.getGenes().subList(index1, index2));
            genesForChild.addAll(parent1.getGenes().subList(index2,32));

            parent1.changeEnergy((int) (-0.25 * parent1.getEnergy()));
            parent2.changeEnergy((int) (-0.25 * parent2.getEnergy()));
            int energyForChild = (int) (0.25 * parent1.getEnergy()) + (int) (0.25 * parent2.getEnergy());
            this.mapStats.countEnergy(energyForChild);

            parent1.childBorn();
            parent2.childBorn();
            this.mapStats.countChild(2);

            Vector2d positionForChild;
            List<MapDirection> possibleDirections =
                    new ArrayList<>(List.of(MapDirection.NORTH, MapDirection.NORTHEAST, MapDirection.EAST, MapDirection.SOUTHEAST, MapDirection.SOUTH, MapDirection.SOUTHWEST, MapDirection.WEST, MapDirection.NORTHWEST));
            List<MapDirection> notOccupiedDirections =
                    new ArrayList<>(List.of(MapDirection.NORTH, MapDirection.NORTHEAST, MapDirection.EAST, MapDirection.SOUTHEAST, MapDirection.SOUTH, MapDirection.SOUTHWEST, MapDirection.WEST, MapDirection.NORTHWEST));
            for (MapDirection direction : possibleDirections){
                if (isOccupiedByAnimal(position.add(direction.toUnitVector()).fitInLimits(this.getWidth(), this.getHeight()))){
                    notOccupiedDirections.remove(direction);
                }
            }
            if (notOccupiedDirections.size() > 0){
                positionForChild = position.add(notOccupiedDirections.get(rand.nextInt(notOccupiedDirections.size())).toUnitVector());
            }
            else{
                positionForChild = position.add(possibleDirections.get(rand.nextInt(possibleDirections.size())).toUnitVector());
            }
            new Animal(this, energyForChild, genesForChild, positionForChild);
            this.mapView.addAnimalView(positionForChild);
        }
    }

    private ArrayList<Animal> animalsWhoBreed(ArrayList<Animal> animalsOnPosition) {
        Collections.sort(animalsOnPosition, new EnergyComparator());
        ArrayList<Animal> animalsWhoBreed = new ArrayList<>();
        animalsWhoBreed.add(animalsOnPosition.get(0));
        animalsWhoBreed.add(animalsOnPosition.get(1));
        return animalsWhoBreed;
    }

    public void removeDeadAnimals(){
        ArrayList<ArrayList<Animal>> animalArrayLists = new ArrayList<>(animalsOnMap.values());
        for (ArrayList<Animal> animals : animalArrayLists) {
            ArrayList<Animal> animalArrayList = new ArrayList<>(animals);
            for (Animal animal : animalArrayList) {
                if (animal.getEnergy() <= 0){
                    removeAnimalFromMap(animal);
                    this.mapStats.countDead(1);
                    this.mapStats.countDays(animal.getDaysAlive());
                    this.mapStats.countChild(-animal.getChildren());
                    this.mapView.removeAnimalView(animal.getPosition());
                }
            }
        }
    }

    public boolean isOccupiedByAnimal(Vector2d position) {
        return animalsOnMap.containsKey(position);
    }


    @Override
    public void positionChanged(Animal movedElement, Vector2d oldPosition, Vector2d newPosition) {
        if (animalsOnMap.get(oldPosition).size() == 1)
        {
            animalsOnMap.remove(oldPosition);
            addAnimalToMap(movedElement, newPosition);
            addPlantPosition(oldPosition);
        }
        else if (animalsOnMap.get(oldPosition).size() > 1){
            animalsOnMap.get(oldPosition).remove(movedElement);
            addAnimalToMap(movedElement, newPosition);

        }
        this.mapView.removeAnimalView(oldPosition);
        this.mapView.addAnimalView(newPosition);
        removePlantPosition(newPosition);
    }
}
