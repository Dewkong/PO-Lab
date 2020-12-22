package agh.po.element;

import java.util.Comparator;

public class EnergyComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal animal1, Animal animal2) {
        if (animal1.getEnergy() > animal2.getEnergy()){
            return -1;
        }
        else if (animal1.getEnergy() < animal2.getEnergy()){
            return 1;
        }
        else{
            return 0;
        }
    }
}
