package agh.po.movement;

import java.util.List;
import java.util.Random;

public class DirectionProcessor {
    private static List<MapDirection> mapDirections =
            List.of(MapDirection.NORTH, MapDirection.NORTHEAST, MapDirection.EAST, MapDirection.SOUTHEAST, MapDirection.SOUTH, MapDirection.SOUTHWEST, MapDirection.WEST, MapDirection.NORTHWEST);

    public static List<MapDirection> getMapDirections(){
        return mapDirections;
    }

    public static MapDirection changeDirection(MapDirection prevDirection, int change){
        return mapDirections.get((mapDirections.indexOf(prevDirection) + change) % mapDirections.size());
    }

    public static MapDirection generateDirection(){
        Random rand = new Random();
        return mapDirections.get(rand.nextInt(8));
    }
}
