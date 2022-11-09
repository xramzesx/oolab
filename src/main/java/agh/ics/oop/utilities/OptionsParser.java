package agh.ics.oop.utilities;

import agh.ics.oop.enums.MoveDirection;

import java.util.Arrays;

public class OptionsParser {
    public static MoveDirection[] parse (String[] directions ) {
        return Arrays
                .stream(directions)
                .map(MoveDirection::toEnum)
                .filter(direction -> direction != MoveDirection.NONE)
                .toArray(MoveDirection[]::new);
    }
}
