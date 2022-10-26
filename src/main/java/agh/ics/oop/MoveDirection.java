package agh.ics.oop;

public enum MoveDirection {
    FORWARD, BACKWARD, RIGHT, LEFT, NONE;
    public static MoveDirection toEnum( String direction ) {
        return switch (direction.toLowerCase()) {
            case "f", "forward" -> FORWARD;
            case "b", "backward" -> BACKWARD;
            case "r", "right" -> RIGHT;
            case "l", "left" -> LEFT;
            default -> NONE;
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case FORWARD -> "f";
            case BACKWARD -> "b";
            case RIGHT -> "r";
            case LEFT -> "l";
            default -> null;
        };
    }
}
