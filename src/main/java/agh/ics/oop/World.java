package agh.ics.oop;
import agh.ics.oop.elements.Animal;
import agh.ics.oop.enums.Direction;
import agh.ics.oop.enums.MoveDirection;
import agh.ics.oop.gui.App;
import agh.ics.oop.interfaces.IEngine;
import agh.ics.oop.interfaces.IWorldMap;
import agh.ics.oop.maps.GrassField;
import agh.ics.oop.maps.RectangularMap;
import agh.ics.oop.utilities.OptionsParser;
import javafx.application.Application;

import java.util.Arrays;

import static java.lang.System.out;

public class World {

    public static void main ( String[] args ) {
        Application.launch(App.class, args);
        try {
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
            IWorldMap map = new GrassField(10);
            Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
            IEngine engine = new SimulationEngine(directions, map, positions, true);

            out.println(engine);
            engine.run();
            out.println(engine);

        } catch (IllegalArgumentException e) {
            printLine();
            out.println("\nIllegal argument: " + e.getMessage() + "\n" );
            printLine();
        }

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
                default -> {}
            }
        }

        out.println("zwierzak zastopował");
    }

    private static Direction[] convertToDirections(String[] directions ) {
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
