package agh.ics.oop.interfaces;

import agh.ics.oop.Vector2d;

public interface IMapElement {
    boolean isAt( Vector2d position );
    Vector2d getPosition();
    String toString();
    String getImagePath ();
    String getImageLabel();
}
