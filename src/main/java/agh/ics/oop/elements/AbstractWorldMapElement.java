package agh.ics.oop.elements;

import agh.ics.oop.Vector2d;
import agh.ics.oop.interfaces.IMapElement;
import agh.ics.oop.interfaces.IPositionChangeObserver;

import java.util.ArrayList;

abstract public class AbstractWorldMapElement implements IMapElement {

    protected Vector2d position;
    private final ArrayList<IPositionChangeObserver> observers = new ArrayList<>();

    public boolean isAt( Vector2d position ){
        return this.position.equals( position );
    }

    public Vector2d getPosition() {
        return position;
    }

    public void addObserver(IPositionChangeObserver observer) {
        observers.add( observer );
    }

    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove( observer );
    }

    public void positionChanged( Vector2d oldPosition, Vector2d newPosition ) {
        for (IPositionChangeObserver observer : observers ) {
            observer.positionChanged( oldPosition, newPosition );
        }
    }
}
