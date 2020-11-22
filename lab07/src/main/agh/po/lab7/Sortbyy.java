package agh.po.lab7;

import agh.po.lab5.IWorldElement;

import java.util.Comparator;

public class Sortbyy implements Comparator<IWorldElement> {

    @Override
    public int compare(IWorldElement element1, IWorldElement element2) {
        if (element1.getPosition().getY() != element2.getPosition().getY()){
            return (element1.getPosition().getY() - element2.getPosition().getY());
        }
        else if (element1.getPosition().getX() != element2.getPosition().getX()){
            return (element1.getPosition().getX() - element2.getPosition().getX());
        }
        else{
            if (!element1.isPassable() && element2.isPassable()){
                return 1;
            }
            else if (element1.isPassable() && !element2.isPassable()){
                return -1;
            }
            else{
                return 0;
            }
        }
    }
}
