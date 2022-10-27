package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {

    @Test
    void animalBaseTest () {
        Animal animal = new Animal();

        /// BASE POSITION ///
        assertEquals("[\"Północ\",(2,2)]", animal.toString());
        assert(animal.isAt(new Vector2d(2,2)));
    }

    @Test
    void animalPassLimitTest() {
        Animal animal = new Animal();

        World.run( OptionsParser.parse(new String[]{ "f", "f", "f", "f", "f" }), animal );

        /// PASSING THE LIMIT ///
        assertEquals("[\"Północ\",(2,4)]", animal.toString());
        assert(animal.isAt(new Vector2d(2,4)));
        World.printLine();
    }

    @Test
    void animalLimitRotateTest() {
        Animal animal = new Animal();
        World.run( OptionsParser.parse(new String[]{ "f", "f", "f", "f", "f", "r", "b", "b", "b", "b" }), animal );

        /// ROTATE AND PASSING THE LIMIT ///
        assertEquals("[\"Wschód\",(4,4)]", animal.toString());
        assert( animal.isAt(new Vector2d(4, 4)) );

        World.printLine();
        World.run( OptionsParser.parse(new String[]{ "r", "b", "r", "b" }), animal );

        assertEquals("[\"Zachód\",(3,3)]", animal.toString());
        assert( animal.isAt(new Vector2d(3, 3)) );
        World.printLine();
    }

    @Test
    void animalParseLimitRotateTest() {
        Animal animal = new Animal();

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
        assertEquals("[\"Południe\",(0,3)]", animal.toString());
        assert( animal.isAt(new Vector2d(0, 3)) );
        World.printLine();
    }
}
