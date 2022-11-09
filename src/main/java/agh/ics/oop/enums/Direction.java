package agh.ics.oop.enums;

public enum Direction {
    FORWARD,
    BACKWARD,
    RIGHT,
    LEFT,
    NONE;

    public String toString() {
        return switch (this) {
            case FORWARD -> "f";
            case BACKWARD -> "b";
            case RIGHT -> "r";
            case LEFT -> "l";
            default -> null;
        };
    }

    public static Direction toEnum( String direction ) {
        return switch (direction) {
            case "f" -> FORWARD;
            case "b" -> BACKWARD;
            case "r" -> RIGHT;
            case "l" -> LEFT;
            default -> NONE;
        };
    }
}