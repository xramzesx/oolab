package agh.ics.oop;

import agh.ics.oop.elements.Animal;
import agh.ics.oop.interfaces.IWorldMap;
import agh.ics.oop.maps.RectangularMap;
import agh.ics.oop.utilities.OptionsParser;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {
    @Test
    void animalBaseTest () {
        IWorldMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(map);

        /// BASE POSITION ///
        assertEquals("^", animal.toString());
        assert(animal.isAt(new Vector2d(2,2)));
    }

    @Test
    void animalPassLimitTest() {
        IWorldMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(map);

        World.run( OptionsParser.parse(new String[]{ "f", "f", "f", "f", "f" }), animal );

        /// PASSING THE LIMIT ///
        assertEquals("^", animal.toString());
        assert(animal.isAt(new Vector2d(2,4)));
        World.printLine();
    }

    @Test
    void animalLimitRotateTest() {
        IWorldMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(map);
        World.run( OptionsParser.parse(new String[]{ "f", "f", "f", "f", "f", "r", "b", "b", "b", "b" }), animal );

        /// ROTATE AND PASSING THE LIMIT ///
        assertEquals(">", animal.toString());
        assert( animal.isAt(new Vector2d(0, 4)) );

        World.printLine();
        World.run( OptionsParser.parse(new String[]{ "r", "b", "r", "b" }), animal );

        assertEquals("<", animal.toString());
        assert( animal.isAt(new Vector2d(1, 4)) );
        World.printLine();
    }

    @Test
    void animalParseLimitRotateTest() {
        IWorldMap map = new RectangularMap(5, 5);
        Animal animal = new Animal(map);

        World.run(OptionsParser.parse(
            new String[]{
                "f", "f", "f", "f", "f", "r", "b", "b", "b", "b",
                "r", "b", "r", "b",
                "right", "ri", "b", "backward", "left",
                "forward", "forw", "asd", "dasd", "bababab",
                "", "f", "f", "f", "left", "f"
            }),
            animal
        );

        /// PARSE, ROTATE AND PASSING THE LIMIT ///
        assertEquals("v", animal.toString());
        assert( animal.isAt(new Vector2d(0, 1)) );
        World.printLine();
    }
}
