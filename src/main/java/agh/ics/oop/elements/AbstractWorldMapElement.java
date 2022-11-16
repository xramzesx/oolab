package agh.ics.oop.elements;

import agh.ics.oop.Vector2d;
import agh.ics.oop.interfaces.IMapElement;

abstract public class AbstractWorldMapElement implements IMapElement {
    protected Vector2d position;
    public boolean isAt( Vector2d position ){
        return this.position.equals( position );
    }

    public Vector2d getPosition() {
        return position;
    }
}
