package agh.po.element;

import agh.po.movement.Vector2d;

import java.util.Objects;

public class Plant extends AbstractElement{
    public Plant(Vector2d position){
        this.position = position;
    }

    @Override
    public String toString() {
        return "*";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plant plant = (Plant) o;
        return position.equals(plant.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position);
    }
}
