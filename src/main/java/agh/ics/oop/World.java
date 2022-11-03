package agh.ics.oop;
import java.util.Arrays;

import static java.lang.System.out;

public class World {

    public static void main ( String[] args ) {

        /// FROM PREVIOUS LABS (UP TO 3RD) ///
        Animal animal = new Animal( new RectangularMap(5,5) );
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

        /// CURRENT LABS (FROM 4TH) ///

        MoveDirection[] directions = OptionsParser.parse(args);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        IEngine engine = new SimulationEngine(directions, map, positions);

        out.println(engine);
        engine.run();
        out.println(engine);
    }

    public static void run (MoveDirection[] requests, Animal animal) {
        out.println("zwierzak wystartowal");

        for (MoveDirection request : requests ) {
            animal.move(request);

            switch (request) {
                case FORWARD -> out.println("Zwierzak idzie do przodu");
                case BACKWARD -> out.println("Zwierzak idzie do tyłu");
                case RIGHT -> out.println("Zwierzak skręca w prawo");
                case LEFT -> out.println("Zwierzak skręca w lewo");
                default -> {
                }
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
