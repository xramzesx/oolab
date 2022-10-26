package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {
    @Test
    void animalTests () {
        Animal animal = new Animal();

        /// BASE POSITION ///
        assertEquals("[\"Północ\",(2,2)]", animal.toString());
        assert(animal.isAt(new Vector2d(2,2)));

        /// PASSING THE LIMIT ///

        World.printLine();
        World.run( OptionsParser.parse(new String[]{ "f", "f", "f", "f", "f" }), animal );

        assertEquals("[\"Północ\",(2,4)]", animal.toString());
        assert(animal.isAt(new Vector2d(2,4)));

        /// ROTATE AND PASSING THE LIMIT ///

        World.printLine();
        World.run( OptionsParser.parse(new String[]{ "r", "b", "b", "b", "b" }), animal );

        assertEquals("[\"Wschód\",(4,4)]", animal.toString());
        assert( animal.isAt(new Vector2d(4, 4)) );

        World.printLine();
        World.run( OptionsParser.parse(new String[]{ "r", "b", "r", "b" }), animal );

        assertEquals("[\"Zachód\",(3,3)]", animal.toString());
        assert( animal.isAt(new Vector2d(3, 3)) );

        /// PARSE, ROTATE AND PASSING THE LIMIT ///

        World.printLine();
        World.run(OptionsParser.parse(
                new String[]{
                        "right", "ri", "b", "backward", "left",
                        "forward", "forw", "asd", "dasd", "bababab",
                        "", "f", "f", "f", "left", "f"
                }),
                animal
        );

        assertEquals("[\"Południe\",(0,3)]", animal.toString());
        assert( animal.isAt(new Vector2d(0, 3)) );

        World.printLine();
    }
}
