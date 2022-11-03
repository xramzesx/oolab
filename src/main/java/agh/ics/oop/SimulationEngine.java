package agh.ics.oop;

import java.util.ArrayList;

public class SimulationEngine implements IEngine {

    private IWorldMap map;
    private int currentAnimal = 0;
    private ArrayList<Animal> animals = new ArrayList<>();
    private MoveDirection[] directions;

    SimulationEngine(
            MoveDirection[] directions,
            IWorldMap map,
            Vector2d[] positions
    ){
        this.map = map;
        this.directions = directions;

        for( Vector2d position: positions ) {
            Animal animal = new Animal( this.map, position );

            if ( this.map.canMoveTo(animal.getPosition()) ){
                this.map.place(animal);
                this.animals.add(animal);
            }
        }
    }

    public void run() {
        int n = this.animals.size();
        for (int i = 0; i < this.directions.length; i++ ) {
            this.animals.get(i % n).move(this.directions[i]);
        }
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    @Override
    public String toString() {
        return this.map.toString();
    }
}
