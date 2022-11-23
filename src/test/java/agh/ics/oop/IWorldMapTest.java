package agh.ics.oop;

import agh.ics.oop.elements.Animal;
import agh.ics.oop.elements.Grass;
import agh.ics.oop.enums.MoveDirection;
import agh.ics.oop.interfaces.IMapElement;
import agh.ics.oop.interfaces.IWorldMap;
import agh.ics.oop.maps.GrassField;
import agh.ics.oop.maps.RectangularMap;
import agh.ics.oop.utilities.OptionsParser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class IWorldMapTest {
    @Test
    void placeTest() {
        IWorldMap grassMap = new GrassField(10);
        IWorldMap recMap = new RectangularMap(10,10);

        Animal animalGrass1 = new Animal(recMap, new Vector2d(9,9));
        Animal animalRec1 = new Animal(grassMap, new Vector2d(9,9));

        assert ( grassMap.place(animalGrass1) );
        assert ( recMap.place(animalRec1) );

        Animal animalGrass2 = new Animal(recMap, new Vector2d(100,100));
        Animal animalRec2 = new Animal(grassMap, new Vector2d(100,100));

        assert ( grassMap.place(animalGrass2) );
        assertThrows( IllegalArgumentException.class, () -> recMap.place(animalRec2) );
    }

    @Test
    void canMoveToTest() {
        IWorldMap grassMap = new GrassField(11, new ArrayList<Grass>(List.of(new Grass[]{
                new Grass(new Vector2d(0, 0)),
                new Grass(new Vector2d(2, 0)),
                new Grass(new Vector2d(5, 5)),
                new Grass(new Vector2d(9, 10)),
        })));
        IWorldMap recMap = new RectangularMap(10,10);

        /// GRASS ///

        Animal animalGrass1 = new Animal(recMap, new Vector2d(9,9));
        Animal animalGrass2 = new Animal(recMap, new Vector2d(9,8));

        grassMap.place(animalGrass1);
        grassMap.place(animalGrass2);

        /// RECTANGULAR ///

        Animal animalRec1 = new Animal(grassMap, new Vector2d(9,9));
        Animal animalRec2 = new Animal(grassMap, new Vector2d(9,8));

        recMap.place(animalRec1);
        recMap.place(animalRec2);


        /// TESTS ///
        assert (grassMap.canMoveTo(new Vector2d( 0, 0 )));
        assert (!grassMap.canMoveTo(new Vector2d( 9, 9 )));
        assert (!grassMap.canMoveTo(new Vector2d( 9, 8 )));
        assert (grassMap.canMoveTo(new Vector2d( 9, 10 )));

        assert (recMap.canMoveTo(new Vector2d( 0, 0 )));
        assert (!recMap.canMoveTo(new Vector2d( 9, 9 )));
        assert (!recMap.canMoveTo(new Vector2d( 9, 8 )));
        assert (!recMap.canMoveTo(new Vector2d( 9, 10 )));
    }

    @Test
    void isOccupiedTest() {
        IWorldMap grassMap = new GrassField(11, new ArrayList<Grass>(List.of(new Grass[]{
                new Grass(new Vector2d(0, 0)),
                new Grass(new Vector2d(2, 0)),
                new Grass(new Vector2d(5, 5)),
                new Grass(new Vector2d(9, 10)),
        })));
        IWorldMap recMap = new RectangularMap(10,10);

        /// GRASS ///

        Animal animalGrass1 = new Animal(recMap, new Vector2d(9,9));
        Animal animalGrass2 = new Animal(recMap, new Vector2d(9,8));

        grassMap.place(animalGrass1);
        grassMap.place(animalGrass2);

        /// RECTANGULAR ///

        Animal animalRec1 = new Animal(grassMap, new Vector2d(9,9));
        Animal animalRec2 = new Animal(grassMap, new Vector2d(9,8));

        recMap.place(animalRec1);
        recMap.place(animalRec2);


        /// TESTS ///
        assert (grassMap.isOccupied(new Vector2d( 0, 0 )));
        assert (grassMap.isOccupied(new Vector2d( 9, 9 )));
        assert (grassMap.isOccupied(new Vector2d( 9, 8 )));
        assert (grassMap.isOccupied(new Vector2d( 9, 10 )));
        assert (!grassMap.isOccupied(new Vector2d( 10,10 )));
        assert (grassMap.isOccupied(new Vector2d( 5, 5 )));
        assert (!grassMap.isOccupied(new Vector2d( 5, 6 )));

        assert (!recMap.isOccupied(new Vector2d( 0, 0 )));
        assert (recMap.isOccupied(new Vector2d( 9, 9 )));
        assert (recMap.isOccupied(new Vector2d( 9, 8 )));
        assert (!recMap.isOccupied(new Vector2d( 9, 10 )));
        assert (!recMap.isOccupied(new Vector2d( 10,10 )));
        assert (!recMap.isOccupied(new Vector2d( 5,5 )));

    }

    @Test
    void objectAtTest() {
        IWorldMap grassMap = new GrassField(11, new ArrayList<Grass>(List.of(new Grass[]{
                new Grass(new Vector2d(0, 0)),
                new Grass(new Vector2d(2, 0)),
                new Grass(new Vector2d(5, 5)),
                new Grass(new Vector2d(9, 10)),
        })));
        IWorldMap recMap = new RectangularMap(10,10);

        /// GRASS ///

        Animal animalGrass1 = new Animal(recMap, new Vector2d(9,9));
        Animal animalGrass2 = new Animal(recMap, new Vector2d(9,8));

        grassMap.place(animalGrass1);
        grassMap.place(animalGrass2);

        /// RECTANGULAR ///

        Animal animalRec1 = new Animal(grassMap, new Vector2d(9,9));
        Animal animalRec2 = new Animal(grassMap, new Vector2d(9,8));

        recMap.place(animalRec1);
        recMap.place(animalRec2);


        /// TESTS ///
        assert (!(grassMap.objectAt(new Vector2d( 0, 0 )) instanceof Animal));
        assert (grassMap.objectAt(new Vector2d( 9, 9 )) instanceof IMapElement);
        assert (grassMap.objectAt(new Vector2d( 9, 8 )) instanceof Animal);
        assert (grassMap.objectAt(new Vector2d( 9, 10 )) instanceof IMapElement);
        assert (!(grassMap.objectAt(new Vector2d( 10,10 )) instanceof IMapElement));
        assert (grassMap.objectAt(new Vector2d( 5, 5 )) instanceof Grass );
        assert (!(grassMap.objectAt(new Vector2d( 5, 6 )) instanceof IMapElement));

        assert (!(recMap.objectAt(new Vector2d( 0, 0 )) instanceof Animal));
        assert (recMap.objectAt(new Vector2d( 9, 9 )) instanceof Animal);
        assert (recMap.objectAt(new Vector2d( 9, 8 )) instanceof Animal);
        assert (!(recMap.objectAt(new Vector2d( 9, 10 )) instanceof Animal));
        assert (!(recMap.objectAt(new Vector2d( 10,10 )) instanceof IMapElement));
        assert (!(recMap.objectAt(new Vector2d( 5,5 )) instanceof IMapElement));
    }

    @Test
    void integrationRectTest() {
        // data
        String[] args = {
            "r","b","l","f","r","r","b","f","l","b","f",
            "b","l","b","l","b","f","f","f","r","l","r",
            "b","f","f","f","r","f","f","l","r","f","b",
            "b","l","f","b","b","b","l","f","b","f","b",
            "f","l","b","f","b","r","l","l","f","b","b",
            "r","r","l","r","f","l","l","f","b","f","b",
            "l","f","b","l","f","f","f","b","l","f","l",
            "b","r","f","f","f","f","b","b","b","l","b",
            "l","f","b","l","b","b","b","f","b","f","b",
            "b","l","f","b","b","b","l","f","b","f","b",
            "f","l","b","f","b","r","l","l","f","b","b",
            "r","r","l","r","f","l","l","f","b","f","b",
            "l","f","b","l","f","f","f","b","l","f","l",
            "b","r","f","f","f","f","b","b","b","l","b",
            "f","l","b","f","b","r","l","l","f","b","b",
            "r","r","l","r","f","l","l","f","b","f","b",
            "l","f","b","l","f","f","f","b","l","f","l",
            "r","b","l","f","r","r","b","f","l","b","f",
            "b","l","b","l","b","f","f","f","r","l","r",
            "b","f","f","f","r","f","f","l","r","f","b",
            "b","l","f","b","b","b","l","f","b","f","b",
            "b","l","b","l","b","f","f","f","r","l","r",
            "b","f","f","f","r","f","f","l","r","f","b",
            "b","r","f","f","f","f","b","b","b","l","b",
            "l","f","b","l","b","b","b","f","b","f","b",
            "r","b","l","f","r","r","b","f","l","b","f",
            "l","f","b","l","b","b","b","f","b","f","b",
        };

        // prepare
        MoveDirection[] directions = OptionsParser.parse(args);
        IWorldMap map = new RectangularMap(10, 10);
        Vector2d[] positions = {
            new Vector2d(2,2),
            new Vector2d(3,4),
            new Vector2d(6,6)
        };
        SimulationEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

        // test
        assertEquals(new Vector2d(1,2), engine.getAnimals().get(0).getPosition());
        assertEquals(new Vector2d(3,8), engine.getAnimals().get(1).getPosition());
        assertEquals(new Vector2d(9,9), engine.getAnimals().get(2).getPosition());
    }

    @Test
    void integrationRectExceptionTest() {
        // data
        String[] args = {
            "r","b","l","f","r","r","b","f","l","b","f",
            "b","l","b","l","b","f","f","f","r","l","r",
            "b","f","f","f","r","f","f","l","r","f","b",
            "b","l","f","b","b","b","l","f","b","f","b",
            "f","l","b","f","b","r","l","l","f","b","b",
            "r","r","l","r","f","l","l","f","b","f","b",
            "l","f","b","l","f","f","f","b","l","f","l",
            "b","r","f","f","f","f","b","b","b","l","b",
            "l","f","b","l","b","b","b","f","b","f","b",
            "b","l","f","b","b","b","l","f","b","f","b",
            "f","l","b","f","b","r","l","l","f","b","b",
            "r","r","l","r","f","l","l","f","b","f","b",
            "l","f","b","l","f","f","f","b","l","f","l",
            "b","r","f","f","f","f","b","b","b","l","b",
            "f","l","b","f","b","r","l","l","f","b","b",
            "r","r","l","r","f","l","l","f","b","f","b",
            "l","f","b","l","f","f","f","b","l","f","l",
            "r","b","l","f","r","r","b","f","l","b","f",
            "b","l","b","l","b","f","f","f","r","l","r",
            "b","f","f","f","r","f","f","l","r","f","b",
            "b","l","f","b","b","b","l","f","b","f","b",
            "b","l","b","l","b","f","f","f","r","l","r",
            "b","f","f","f","r","f","f","l","r","f","b",
            "b","r","f","f","f","f","b","b","b","l","b",
            "l","f","b","l","b","b","b","f","b","f","b",
            "r","b","l","f","r","r","b","f","l","b","f",
            "l","f","b","l","b","b","b","f","b","f","b",
        };

        // prepare
        assertThrows(IllegalArgumentException.class, () -> {
            MoveDirection[] directions = OptionsParser.parse(args);
            IWorldMap map = new RectangularMap(10, 10);
            Vector2d[] positions = {
                    new Vector2d(2,2),
                    new Vector2d(3,4),
                    new Vector2d(3,4),
                    new Vector2d(6,6),
                    new Vector2d(6,6),
                    new Vector2d(6,6),
                    new Vector2d(6,6)
            };
            SimulationEngine engine = new SimulationEngine(directions, map, positions);
            engine.run();
        });
    }

    @Test
    void integrationGrassTest() {
// data
        String[] args = {
                "r","b","l","f","r","r","b","f","l","b","f",
                "b","l","b","l","b","f","f","f","r","l","r",
                "b","f","f","f","r","f","f","l","r","f","b",
                "b","l","f","b","b","b","l","f","b","f","b",
                "f","l","b","f","b","r","l","l","f","b","b",
                "r","r","l","r","f","l","l","f","b","f","b",
                "l","f","b","l","f","f","f","b","l","f","l",
                "b","r","f","f","f","f","b","b","b","l","b",
                "l","f","b","l","b","b","b","f","b","f","b",
                "b","l","f","b","b","b","l","f","b","f","b",
                "f","l","b","f","b","r","l","l","f","b","b",
                "r","r","l","r","f","l","l","f","b","f","b",
                "l","f","b","l","f","f","f","b","l","f","l",
                "b","r","f","f","f","f","b","b","b","l","b",
                "f","l","b","f","b","r","l","l","f","b","b",
                "r","r","l","r","f","l","l","f","b","f","b",
                "l","f","b","l","f","f","f","b","l","f","l",
                "r","b","l","f","r","r","b","f","l","b","f",
                "b","l","b","l","b","f","f","f","r","l","r",
                "b","f","f","f","r","f","f","l","r","f","b",
                "b","l","f","b","b","b","l","f","b","f","b",
                "b","l","b","l","b","f","f","f","r","l","r",
                "b","f","f","f","r","f","f","l","r","f","b",
                "b","r","f","f","f","f","b","b","b","l","b",
                "l","f","b","l","b","b","b","f","b","f","b",
                "r","b","l","f","r","r","b","f","l","b","f",
                "l","f","b","l","b","b","b","f","b","f","b",
        };

        // prepare
        MoveDirection[] directions = OptionsParser.parse(args);
        IWorldMap map = new GrassField(10);
        Vector2d[] positions = {
                new Vector2d(2,2),
                new Vector2d(3,4),
                new Vector2d(6,6)
        };
        SimulationEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

        // test
        assertEquals(new Vector2d(1,-1), engine.getAnimals().get(0).getPosition());
        assertEquals(new Vector2d(3,8), engine.getAnimals().get(1).getPosition());
        assertEquals(new Vector2d(13,9), engine.getAnimals().get(2).getPosition());
    }
    @Test
    void integrationGrassExceptionTest() {
        // data
        String[] args = {
            "r","b","l","f","r","r","b","f","l","b","f",
            "b","l","b","l","b","f","f","f","r","l","r",
            "b","f","f","f","r","f","f","l","r","f","b",
            "b","l","f","b","b","b","l","f","b","f","b",
            "f","l","b","f","b","r","l","l","f","b","b",
            "r","r","l","r","f","l","l","f","b","f","b",
            "l","f","b","l","f","f","f","b","l","f","l",
            "b","r","f","f","f","f","b","b","b","l","b",
            "l","f","b","l","b","b","b","f","b","f","b",
            "b","l","f","b","b","b","l","f","b","f","b",
            "f","l","b","f","b","r","l","l","f","b","b",
            "r","r","l","r","f","l","l","f","b","f","b",
            "l","f","b","l","f","f","f","b","l","f","l",
            "b","r","f","f","f","f","b","b","b","l","b",
            "f","l","b","f","b","r","l","l","f","b","b",
            "r","r","l","r","f","l","l","f","b","f","b",
            "l","f","b","l","f","f","f","b","l","f","l",
            "r","b","l","f","r","r","b","f","l","b","f",
            "b","l","b","l","b","f","f","f","r","l","r",
            "b","f","f","f","r","f","f","l","r","f","b",
            "b","l","f","b","b","b","l","f","b","f","b",
            "b","l","b","l","b","f","f","f","r","l","r",
            "b","f","f","f","r","f","f","l","r","f","b",
            "b","r","f","f","f","f","b","b","b","l","b",
            "l","f","b","l","b","b","b","f","b","f","b",
            "r","b","l","f","r","r","b","f","l","b","f",
            "l","f","b","l","b","b","b","f","b","f","b",
        };

        // prepare

        assertThrows(IllegalArgumentException.class, () -> {
            MoveDirection[] directions = OptionsParser.parse(args);
            IWorldMap map = new GrassField(10);
            Vector2d[] positions = {
                    new Vector2d(2,2),
                    new Vector2d(3,4),
                    new Vector2d(3,4),
                    new Vector2d(6,6),
                    new Vector2d(6,6),
                    new Vector2d(6,6),
                    new Vector2d(6,6)
            };
            SimulationEngine engine = new SimulationEngine(directions, map, positions);
            engine.run();
        });

    }
}
