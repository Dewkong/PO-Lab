package agh.cs.lab3;

public class Animal {
    private MapDirection orientation;
    private Vector2d position;

    private static final Vector2d lowerLimit = new Vector2d(0,0);
    private static final Vector2d upperLimit = new Vector2d(4,4);

    public MapDirection getOrientation() {
        return orientation;
    }

    public void setOrientation(MapDirection orientation) {
        this.orientation = orientation;
    }

    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    public Animal() {
        this.orientation = MapDirection.NORTH;
        this.position = new Vector2d(2,2);
    }

    public void move(MoveDirection direction){
        switch(direction){
            case RIGHT -> orientation = orientation.next();
            case LEFT -> orientation = orientation.previous();
            case FORWARD -> {
                Vector2d stepForward = orientation.toUnitVector();
                if ((position.add(stepForward).precedes(lowerLimit)) && (position.add(stepForward).follows(upperLimit))){
                    position = position.add(stepForward);
                }
            }
            case BACKWARD -> {
                Vector2d stepBackward = orientation.toUnitVector();
                if ((position.subtract(stepBackward).precedes(lowerLimit)) && (position.subtract(stepBackward).follows(upperLimit))){
                    position = position.subtract(stepBackward);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "pozycja = " + position + ", orientacja = " + orientation;
    }
}
