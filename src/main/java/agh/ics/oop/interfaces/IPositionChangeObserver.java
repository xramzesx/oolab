package agh.ics.oop.interfaces;

import agh.ics.oop.Vector2d;

public interface IPositionChangeObserver {
    boolean positionChanged(Vector2d oldPosition, Vector2d newPosition);
}
