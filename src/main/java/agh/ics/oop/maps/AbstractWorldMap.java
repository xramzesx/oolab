package agh.ics.oop.maps;

import agh.ics.oop.MapBoundary;
import agh.ics.oop.elements.AbstractWorldMapElement;
import agh.ics.oop.elements.Animal;
import agh.ics.oop.interfaces.IMapElement;
import agh.ics.oop.interfaces.IPositionChangeObserver;
import agh.ics.oop.utilities.MapVisualizer;
import agh.ics.oop.Vector2d;
import agh.ics.oop.interfaces.IWorldMap;

import java.util.ArrayList;
import java.util.HashMap;

abstract public class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected MapBoundary mapBoundary = new MapBoundary();

    protected HashMap< Vector2d, AbstractWorldMapElement > mapElements = new HashMap<>();

    protected Vector2d startBorder = new Vector2d(0,0);
    protected Vector2d endBorder;

    @Override
    public boolean canMoveTo(Vector2d position) {
        return     position.precedes(endBorder)
                && position.follows(startBorder)
                && !(objectAt(position) instanceof Animal);
    }
    @Override
    public boolean place(Animal animal) throws IllegalArgumentException {
        if (canMoveTo(animal.getPosition())) {
            this.mapElements.put(animal.getPosition(), animal);
            animal.addObserver(this::positionChanged);
            animal.addObserver(this.mapBoundary);
            return true;
        } else {
            throw new IllegalArgumentException(
                "Cannot place animal at: " +
                animal.getPosition() +
                ". Field is currently busy"
            );
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return this.mapElements.get(position);
    }

    abstract protected void resetBorders();
    abstract protected void prepareBorders();

    abstract public int getPoint(Vector2d position);

    @Override
    public String toString() {
        this.prepareBorders();
        MapVisualizer visualizer = new MapVisualizer(this);
        String result = visualizer.draw(startBorder, endBorder);
        this.resetBorders();

        return result;
    }

    @Override
    public boolean positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        if (newPosition == null) {
            this.mapElements.remove(oldPosition);
            return true;
        }

        if (canMoveTo(newPosition)) {
            AbstractWorldMapElement mapElement = (AbstractWorldMapElement) objectAt(oldPosition);
            this.mapElements.put(newPosition, mapElement);
            if ( oldPosition != null)
                this.mapElements.remove(oldPosition);
            return true;
        } else {
            return false;
        }

    }
}
