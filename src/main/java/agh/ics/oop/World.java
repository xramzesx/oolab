package agh.ics.oop;
import java.util.Arrays;

import static java.lang.System.out;

public class World {

    public static void main ( String[] args ) {
        Animal animal = new Animal();

        out.println("Pozycja zwierzaka:");
        out.println(animal);

        MoveDirection[] directions = {
                MoveDirection.RIGHT,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD,
                MoveDirection.FORWARD
        };

        for ( MoveDirection direction: directions ){
            animal.move(direction);
        }

        printLine();
        out.println("Pozycja po ciągu wywołań testowych");
        out.println(animal);

        printLine();
        MoveDirection[] requests = OptionsParser.parse(args);
        out.println("\nsystem wystartowal");

        printLine();
        run(requests, animal);

        printLine();
        out.println("Komunikaty: " + String.join(
                ",",
                Arrays
                        .stream(args)
                        .filter(
                                direction -> MoveDirection.toEnum(direction) != MoveDirection.NONE)
                        .toArray(String[]::new)
        ));

        printLine();
        out.println("Pozycja końcowa: " + animal);

        printLine();
        out.println("system zakonczyl dzialanie");
    }

    public static void run (MoveDirection[] requests, Animal animal) {
        // zad8: tu drobna modyfikacja, żeby nie wpływała
        // na dalszą część programu/wyświetlanego tekstu
        out.println("zwierzak wystartowal");

        for (MoveDirection request : requests ) {
            animal.move(request);

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
