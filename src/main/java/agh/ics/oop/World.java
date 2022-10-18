package agh.ics.oop;
import java.util.Arrays;

import static java.lang.System.out;

public class World {

    public static void main ( String[] args ) {
        Direction[] requests = convertToDirections(args);
        out.println("system wystartowal");

        printLine();
        run(requests);

        printLine();
        out.println("Komunikaty: " + String.join(
                ",",
                Arrays
                        .stream(args)
                        .filter(
                                direction -> Direction.toEnum(direction) != Direction.NONE
                        )
                        .toArray(String[]::new)
        ));

        printLine();
        out.println("system zakonczyl dzialanie");
    }

    private static void run (Direction[] requests) {
        // zad8: tu drobna modyfikacja, żeby nie wpływała
        // na dalszą część programu/wyświetlanego tekstu
        out.println("zwierzak wystartowal");

        for (Direction request : requests ) {
            switch (request) {
                case FORWARD:
                    out.println("Zwierzak idzie do przodu");
                    break;
                case BACKWARD:
                    out.println("Zwierzak idzie do tyłu");
                    break;
                case RIGHT:
                    out.println("Zwierzak skręca w prawo");
                    break;
                case LEFT:
                    out.println("Zwierzak skręca w lewo");
                    break;
                default:
                    break;
            }
        }

        out.println("zwierzak zastopował");
    }

    private static Direction[] convertToDirections( String[] directions ) {
        return Arrays
                .stream(directions)
                .map( direction -> switch (direction) {
                    case "f" -> Direction.FORWARD;
                    case "b" -> Direction.BACKWARD;
                    case "r" -> Direction.RIGHT;
                    case "l" -> Direction.LEFT;
                    default -> Direction.NONE;
                })
                .toArray(Direction[]::new);
    }
    public static void printLine() {
        out.println("-------------------------");
    }
}
