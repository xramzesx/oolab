package agh.ics.oop.maps;

import agh.ics.oop.Vector2d;

public class RectangularMap extends AbstractWorldMap{
    private final int width;
    private final int height;

    public RectangularMap(int width, int height) {
        this.width = width;
        this.height = height;

        // zakładamy, że dane są poprawne
        this.endBorder = new Vector2d(
                this.width - 1,
                this.height - 1
        );
    }

    @Override
    protected void openMap() {

    }

    @Override
    protected void fitMap() {

    }
}
