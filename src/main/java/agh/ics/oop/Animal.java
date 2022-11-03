package agh.ics.oop;

public class Animal {
    private IWorldMap map;
    public Animal( IWorldMap map ){
        this.map = map;
    }

    public Animal( IWorldMap map, Vector2d initialPosition ) {
        this(map);
        this.position = initialPosition;
    }

    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position = new Vector2d(2,2);

    public String toString() {
        return switch (this.orientation) {
            case NORTH -> "^";
            case EAST -> ">";
            case WEST -> "<";
            case SOUTH -> "v";
        };
    }

    public boolean isAt( Vector2d position ){
        return this.position.equals( position );
    }

    public Vector2d getPosition() {
        return position;
    }

    public void move(MoveDirection direction) {
        switch (direction) {
            case LEFT -> this.orientation = this.orientation.previous();
            case RIGHT -> this.orientation = this.orientation.next();
            case FORWARD, BACKWARD -> {
                Vector2d position = MoveDirection.FORWARD == direction
                        ? this.position.add(this.orientation.toUnitVector())
                        : this.position.subtract(this.orientation.toUnitVector());
                if (this.map.canMoveTo(position))
                    this.position = position;
            }
        }
    }

}
