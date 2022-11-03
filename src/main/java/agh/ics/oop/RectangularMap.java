package agh.ics.oop;

import java.util.ArrayList;

public class RectangularMap implements IWorldMap{
    private int width;
    private int height;

    private final ArrayList<Animal> animals = new ArrayList<>();
    private final Vector2d startBorder = new Vector2d(0,0);
    private final Vector2d endBorder;

    public RectangularMap(int width, int height) {
        this.width = width;
        this.height = height;

        // zakładamy, że dane są poprawne
        this.endBorder = new Vector2d( width - 1, height - 1 );
    }

    public Animal getAnimal(int index) {
        return animals.get(index);
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return
                position.precedes(endBorder) && position.follows(startBorder)
                && !isOccupied(position);
    }

    @Override
    public boolean place(Animal animal) {
        if (canMoveTo(animal.getPosition())) {
            this.animals.add(animal);
            return true;
        }
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public Animal objectAt(Vector2d position) {
        for (Animal animal: this.animals ){
            if (animal.isAt(position))
                return animal;
        }

        return null;
    }

    @Override
    public String toString() {
        MapVisualizer visualizer = new MapVisualizer(this);
        return visualizer.draw(startBorder, endBorder);
    }
}
