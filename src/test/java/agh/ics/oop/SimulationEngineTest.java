package agh.ics.oop;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SimulationEngineTest {
    @Test
    void baseEngineTest() {
        String[] args = {"f","b","r","l","f","f","r","r","f","f","f","ff","f","f","f"};

        MoveDirection[] directions = OptionsParser.parse(args);
        IWorldMap map = new RectangularMap(10, 5);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        SimulationEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

        assertEquals(new Vector2d(2,0), engine.getAnimals().get(0).getPosition());
        assertEquals(new Vector2d(3,4), engine.getAnimals().get(1).getPosition());
    }

    @Test
    void complicatedBasicTest() {
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
                "l","f","b","l","b","b","b","f","b","f","b"
        };

        // prepare
        MoveDirection[] directions = OptionsParser.parse(args);
        IWorldMap map = new RectangularMap(10, 10);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4) };
        SimulationEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

        // test
        assertEquals(new Vector2d(3,6), engine.getAnimals().get(0).getPosition());
        assertEquals(new Vector2d(2,5), engine.getAnimals().get(1).getPosition());
    }

    @Test
    void parseAndComplicatedMovesTest() {
        // data
        String[] args = {
                "r","b","l","f","r","r","b","f","l","b","f",
                "b","l","b","l","b","f","f","f","r","l","r",
                "ba","sda","ab","adsl","hgb","fhrth","fda",
                "b","f","f","f","r","f","f","l","right","f","b",
                "b","l","f","b","b","b","l","f","b","f","b",
                "f","l","b","f","b","r","l","left","f","b","b",
                "r","r","l","r","f","l","l","f","b","f","b",
                "l","f","b","l","forward","f","f","b","l","f","l",
                "b","r","f","f","f","f","backward","b","b","l","b",
                "l","f","b","l","b","b","b","f","b","f","b"
        };

        // prepare
        MoveDirection[] directions = OptionsParser.parse(args);
        IWorldMap map = new RectangularMap(10, 10);
        Vector2d[] positions = { new Vector2d(2,2), new Vector2d(3,4), new Vector2d(6,6) };
        SimulationEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

        // test
        assertEquals(new Vector2d(2,0), engine.getAnimals().get(0).getPosition());
        assertEquals(new Vector2d(1,1), engine.getAnimals().get(1).getPosition());
        assertEquals(new Vector2d(8,3), engine.getAnimals().get(2).getPosition());
    }

    @Test
    void placeAndComplicatedMovesTest() {
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
                new Vector2d(3,4),
                new Vector2d(6,6),
                new Vector2d(6,6),
                new Vector2d(6,6),
                new Vector2d(6,6)
        };
        SimulationEngine engine = new SimulationEngine(directions, map, positions);
        engine.run();

        // test
        assertEquals(new Vector2d(1,2), engine.getAnimals().get(0).getPosition());
        assertEquals(new Vector2d(3,8), engine.getAnimals().get(1).getPosition());
        assertEquals(new Vector2d(9,9), engine.getAnimals().get(2).getPosition());
    }
}