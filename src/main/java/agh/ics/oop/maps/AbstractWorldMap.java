package agh.ics.oop.maps;

import agh.ics.oop.elements.Animal;
import agh.ics.oop.utilities.MapVisualizer;
import agh.ics.oop.Vector2d;
import agh.ics.oop.interfaces.IWorldMap;

import java.util.ArrayList;

abstract public class AbstractWorldMap implements IWorldMap {
    protected final ArrayList<Animal> animals = new ArrayList<>();

    protected Vector2d startBorder = new Vector2d(0,0);
    protected Vector2d endBorder;

    public Animal getAnimal(int index) {
        return animals.get(index);
    }


    @Override
    public boolean canMoveTo(Vector2d position) {
        return     position.precedes(endBorder)
                && position.follows(startBorder)
                && !(objectAt(position) instanceof Animal);
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
    public Object objectAt(Vector2d position) {
        for (Animal animal: this.animals ){
            if (animal.isAt(position))
                return animal;
        }

        return null;
    }

    abstract protected void openMap();
    abstract protected void fitMap();

    abstract public int getPoint(Vector2d position);

    @Override
    public String toString() {
        this.fitMap();
        MapVisualizer visualizer = new MapVisualizer(this);
        String result = visualizer.draw(startBorder, endBorder);
        this.openMap();

        return result;
    }
}
