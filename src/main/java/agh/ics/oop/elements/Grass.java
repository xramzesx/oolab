package agh.ics.oop.elements;

import agh.ics.oop.Vector2d;
import agh.ics.oop.interfaces.IMapElement;

public class Grass implements IMapElement {

    private final Vector2d position;

    public Grass(Vector2d position ) {
        this.position = position;
    }

    public boolean isAt( Vector2d position ){
        return this.position.equals( position );
    }

    public Vector2d getPosition() {
        return position;
    }


    @Override
    public String toString() {
        return "*";
    }
}
