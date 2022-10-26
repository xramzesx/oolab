package agh.ics.oop;

public class Animal {

    private static Vector2d startBorder = new Vector2d(0,0);
    private static Vector2d endBorder = new Vector2d(4,4);

    private MapDirection orientation = MapDirection.NORTH;
    private Vector2d position = new Vector2d(2,2);

    public String toString() {
        return "[\"" + orientation.toString() + "\"," + position.toString() + "]";
    }

    public boolean isAt( Vector2d position ){
        return this.position.equals( position );
    }

    private boolean checkPosition (Vector2d position) {
        return position.precedes(endBorder) && position.follows(startBorder);
    }

    public void move(MoveDirection direction) {
        switch (direction) {
            case LEFT -> this.orientation = this.orientation.previous();
            case RIGHT -> this.orientation = this.orientation.next();
            case FORWARD, BACKWARD -> {
                Vector2d position = this.position.add(this.orientation.toUnitVector());
                if (checkPosition(position))
                    this.position = position;
            }
        }
    }

}
