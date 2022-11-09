package agh.ics.oop.enums;

import agh.ics.oop.Vector2d;
import agh.ics.oop.World;

public enum MapDirection {
    NORTH, EAST, SOUTH, WEST;
    private static MapDirection[] vals = values();

    public static void main( String[] args ) {

        System.out.println(String.format("%s %s %s %s", NORTH, EAST, SOUTH, WEST));

        World.printLine();

        for ( MapDirection direction: vals ){
            System.out.println(String.format("%s %d", direction, direction.ordinal()));
        }

        World.printLine();

        for (MapDirection direction: vals ) {
            System.out.println(String.format("%s | next | %s", direction, direction.next()));
        }

        World.printLine();

        for (MapDirection direction: vals ) {
            System.out.println(String.format("%s | previous | %s", direction, direction.previous()));
        }

        World.printLine();

        for (MapDirection direction: vals ) {
            System.out.println(String.format("%s %s", direction, direction.toUnitVector()));
        }
    }

    public String toString() {
        return switch (this) {
            case NORTH -> "Północ";
            case EAST -> "Wschód";
            case SOUTH -> "Południe";
            case WEST -> "Zachód";
        };
    }

    public MapDirection next() {
        return vals[ ( this.ordinal() + 1 ) % vals.length ];
    }

    public MapDirection previous() {
        return vals[ ( this.ordinal() - 1 + vals.length ) % vals.length ];
    }

    public Vector2d toUnitVector() {
        if ( this.ordinal() % 2 == 0 )
            return new Vector2d(0, - (this.ordinal() - 1));
        else
            return new Vector2d( - (this.ordinal() - 2), 0);
    }
}
