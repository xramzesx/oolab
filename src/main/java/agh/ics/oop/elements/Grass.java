package agh.ics.oop.elements;

import agh.ics.oop.Vector2d;

public class Grass extends AbstractWorldMapElement{

    public Grass(Vector2d position ) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "*";
    }
}
