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
        try {
            Application.launch(App.class, args);
        } catch ( Exception e ) {
            out.println("\nAn exception occur : " + e.getMessage() + "\n" );
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
