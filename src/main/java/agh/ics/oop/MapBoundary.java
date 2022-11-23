package agh.ics.oop;

import agh.ics.oop.interfaces.IPositionChangeObserver;

import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver {

    public TreeSet<Vector2d> axisX = new TreeSet<>(
            (v1, v2) -> v1.x == v2.x
                ? v1.y - v2.y
                : v1.x - v2.x
    );

    public TreeSet<Vector2d> axisY = new TreeSet<>(
            (v1, v2) -> v1.y == v2.y
                ? v1.x - v2.x
                : v1.y - v2.y
    );

    @Override
    public boolean positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        if ( oldPosition != null )
            removeVector(oldPosition);
        if ( newPosition != null )
            addVector(newPosition);

        return true;
    }

    private void removeVector ( Vector2d vector ) {
        axisX.remove(vector);
        axisY.remove(vector);
    }

    private void addVector ( Vector2d vector ) {
        axisX.add(vector);
        axisY.add(vector);
    }

    public Vector2d lowerLeft () {
        return new Vector2d(
            axisX.first().x,
            axisY.first().y
        );
    }

    public Vector2d upperRight () {
        return new Vector2d(
            axisX.last().x,
            axisY.last().y
        );
    }
}
