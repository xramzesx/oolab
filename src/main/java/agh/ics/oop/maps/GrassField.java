package agh.ics.oop.maps;

import agh.ics.oop.elements.Animal;
import agh.ics.oop.elements.Grass;
import agh.ics.oop.utilities.Utils;
import agh.ics.oop.Vector2d;

import java.util.ArrayList;

public class GrassField extends AbstractWorldMap {

    private final ArrayList<Grass> grasses =  new ArrayList<>();
    private final int fieldCount;

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

        this.generateRandomGrass();
    }

    private void generateRandomGrass () {
        this.grasses.clear();

        Vector2d startB = new Vector2d(0,0);
        Vector2d endB = new Vector2d(
                (int) Math.sqrt(10 * this.fieldCount),
                (int) Math.sqrt(10 * this. fieldCount)
        );


        for (int i = 0; i < this.fieldCount; i++ ) {
            Vector2d position;

            do {
                position = Utils.getRandomVector2d(
                        startB,
                        endB
                );
            } while ( this.objectAt(position) instanceof Grass );

            this.grasses.add( new Grass(position));
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

}
