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

    @Override
    public String getImagePath() {
        return resourcePrefix + "grass.png";
    }

    @Override
    public String getImageLabel() {
        return "grass";
    }
}
