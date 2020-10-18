package agh.cs.lab2;

public enum MapDirection {
    NORTH,
    SOUTH,
    WEST,
    EAST;

    public MapDirection next(){
        switch(this){
            case NORTH: return MapDirection.EAST;
            case SOUTH: return MapDirection.WEST;
            case EAST: return MapDirection.SOUTH;
            case WEST: return MapDirection.NORTH;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public MapDirection previous(){
        switch(this){
            case NORTH: return MapDirection.WEST;
            case SOUTH: return MapDirection.EAST;
            case EAST: return MapDirection.NORTH;
            case WEST: return MapDirection.SOUTH;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public Vector2d toUnitVector(){
        switch(this){
            case NORTH: return new Vector2d(0,1);
            case SOUTH: return new Vector2d(0,-1);
            case EAST: return new Vector2d(1,0);
            case WEST: return new Vector2d(-1,0);
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    @Override
    public String toString() {
        switch(this){
            case NORTH: return "Północ";
            case SOUTH: return "Południe";
            case EAST: return "Wschód";
            case WEST: return "Zachód";
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
