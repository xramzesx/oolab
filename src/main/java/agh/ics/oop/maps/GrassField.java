package agh.ics.oop.maps;

import agh.ics.oop.elements.AbstractWorldMapElement;
import agh.ics.oop.elements.Animal;
import agh.ics.oop.elements.Grass;
import agh.ics.oop.utilities.Utils;
import agh.ics.oop.Vector2d;

import java.util.ArrayList;
import java.util.Map;

public class GrassField extends AbstractWorldMap {

    private final ArrayList<Grass> grasses =  new ArrayList<>();
    private final int fieldCount;

    private final Vector2d startGrassBorder;
    private final Vector2d endGrassBorder;

    public GrassField( int grassField, ArrayList<Grass> grasses ) {
        this(grassField);

        this.mapElements.clear();
        for ( Grass grass : grasses ) {
            this.mapElements.put(grass.getPosition(), grass);
        }
    }

    public GrassField( int grassFieldCount ) {
        this.startBorder = new Vector2d(0,0);
        this.fieldCount = grassFieldCount;

        this.resetBorders();

        this.startGrassBorder = new Vector2d(0,0);
        this.endGrassBorder = new Vector2d(
                (int) Math.sqrt(10 * this.fieldCount),
                (int) Math.sqrt(10 * this.fieldCount)
        );

        this.generateRandomGrasses();

    }

    private void generateRandomGrass() {
        Vector2d position;

        do {
            position = Utils.getRandomVector2d(
                    this.startGrassBorder,
                    this.endGrassBorder
            );
        } while ( this.objectAt(position) instanceof Grass || isOccupied(position) );

        this.mapElements.put(position, new Grass(position));
    }
    private void generateRandomGrasses () {
        this.grasses.clear();

        for (int i = 0; i < this.fieldCount; i++ ) {
            this.generateRandomGrass();
        }
    }

    protected void prepareBorders () {
        Vector2d endBorder, startBorder;

        Map.Entry<
            Vector2d,
            AbstractWorldMapElement
        > entry = this.mapElements
            .entrySet()
            .iterator()
            .next();

        if ( entry == null ) {
            this.endBorder = new Vector2d(0,0);
            this.startBorder = new Vector2d(0,0);
            return;
        }

        endBorder = entry.getValue().getPosition();
        startBorder = entry.getValue().getPosition();

        for ( Map.Entry<Vector2d, AbstractWorldMapElement > set : this.mapElements.entrySet() ) {
            AbstractWorldMapElement mapElement = set.getValue();

            startBorder = startBorder.lowerLeft( mapElement.getPosition() );
            endBorder = endBorder.upperRight( mapElement.getPosition() );
        }

        this.startBorder = startBorder;
        this.endBorder = endBorder;
    }

    protected void resetBorders() {
        this.startBorder= new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        this.endBorder = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public int getPoint(Vector2d position) {
        Object object = this.objectAt(position);

        if ( object instanceof Grass ) {
            positionChanged(
                ((Grass) object).getPosition(),
                null
            );
            this.generateRandomGrass();
            return 1;
        } else {
            return 0;
        }
    }
}
