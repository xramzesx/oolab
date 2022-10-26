package agh.ics.oop;

import java.util.Arrays;

public class OptionsParser {
    public static MoveDirection[] parse ( String[] directions ) {
        return Arrays
                .stream(directions)
                .map(direction -> MoveDirection.toEnum(direction))
                .filter(direction -> direction != MoveDirection.NONE)
                .toArray(MoveDirection[]::new);
    }
}
