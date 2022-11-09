package agh.ics.oop.maps;

import agh.ics.oop.elements.Animal;
import agh.ics.oop.elements.Grass;
import agh.ics.oop.utilities.Utils;
import agh.ics.oop.Vector2d;

import java.util.ArrayList;

public class GrassField extends AbstractWorldMap {

    private final ArrayList<Grass> grasses =  new ArrayList<>();
    private final int fieldCount;

    private Vector2d startGrassBorder;
    private Vector2d endGrassBorder;

    public GrassField( int grassField, ArrayList<Grass> grasses ) {
        this(grassField);
        this.grasses.clear();

        for ( Grass grass: grasses )
            this.grasses.add(grass);
    }

    public GrassField(int grassFieldCount ) {
        this.startBorder = new Vector2d(0,0);
        this.fieldCount = grassFieldCount;

        this.openMap();

        this.startGrassBorder = new Vector2d(0,0);
        this.endGrassBorder = new Vector2d(
                (int) Math.sqrt(10 * this.fieldCount),
                (int) Math.sqrt(10 * this. fieldCount)
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

        this.grasses.add( new Grass(position));

    }
    private void generateRandomGrasses () {
        this.grasses.clear();

        for (int i = 0; i < this.fieldCount; i++ ) {
            this.generateRandomGrass();
        }
    }

    @Override
    public Object objectAt(Vector2d position) {
        Animal animal = (Animal) super.objectAt(position);

        if ( animal != null )
            return animal;

        for ( Grass grass: this.grasses ) {
            if ( grass.getPosition().equals( position ) )
                return grass;
        }

        return null;
    }

    protected void fitMap () {
        Vector2d endBorder, startBorder;
        if ( this.grasses.size() > 0)
            endBorder = this.grasses.get(0).getPosition();
        else if ( this.animals.size() > 0 )
            endBorder = this.animals.get(0).getPosition();
        else {
            this.startBorder = new Vector2d(0,0);
            this.endBorder = new Vector2d(0,0);
            return;
        }

        startBorder = new Vector2d(endBorder.x, endBorder.y);

        for ( Animal animal: this.animals ) {
            startBorder = startBorder.lowerLeft(animal.getPosition());
            endBorder = endBorder.upperRight(animal.getPosition());
        }

        for ( Grass grass: this.grasses ) {
            startBorder = startBorder.lowerLeft(grass.getPosition());
            endBorder = endBorder.upperRight(grass.getPosition());
        }

        this.startBorder = startBorder;
        this.endBorder = endBorder;
    }

    protected void openMap() {
        this.startBorder= new Vector2d(Integer.MIN_VALUE, Integer.MIN_VALUE);
        this.endBorder = new Vector2d(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public int getPoint(Vector2d position) {
        Object object = this.objectAt(position);

        if ( object instanceof Grass ) {
            this.grasses.remove(object);
            this.generateRandomGrass();
            return 1;
        } else {
            return 0;
        }
    }
}
