package agh.ics.oop;

import java.util.Objects;

public class Vector2d {
    public final int x;
    public final int y;

    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    public static Vector2d lowerLeft ( Vector2d first, Vector2d second ) {
        return first.lowerLeft(second);
    }

    public static Vector2d upperRight ( Vector2d first, Vector2d second ) {
        return first.upperRight(second);
    }

    public boolean precedes( Vector2d other ) {
        return this.x <= other.x && this.y <= other.y;
    }

    public boolean follows(Vector2d other) {
        return this.x >= other.x && this.y >= other.y;
    }

    public Vector2d add(Vector2d other) {
        return new Vector2d(
            this.x + other.x,
            this.y + other.y
        );
    }

    public Vector2d subtract(Vector2d other) {
        return new Vector2d(
            this.x - other.x,
            this.y - other.y
        );
    }

    public Vector2d upperRight(Vector2d other) {
        return new Vector2d(
            Math.max(this.x, other.x),
            Math.max(this.y, other.y)
        );
    }

    public Vector2d lowerLeft(Vector2d other) {
        return new Vector2d(
            Math.min(this.x, other.x),
            Math.min(this.y, other.y)
        );
    }

    public Vector2d opposite() {
        return new Vector2d(
            -this.x,
            -this.y
        );
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Vector2d))
            return false;

        Vector2d that = (Vector2d) other;

        return this.x == that.x && this.y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public static void main(String[] args){

        Vector2d v1 = new Vector2d(1,1);
        Vector2d v2 = new Vector2d(2,2);
        Vector2d v3 = new Vector2d(2,3);

        System.out.println( String.format("%s precedes %s: %b", v1, v2, v1.precedes(v2)) );
        System.out.println( String.format("%s precedes %s: %b", v2, v3, v2.precedes(v3)) );
        System.out.println( String.format("%s precedes %s: %b", v3, v1, v3.precedes(v1)) );


        World.printLine();

        System.out.println( String.format("%s follows %s: %b", v3, v2, v3.follows(v2)) );
        System.out.println( String.format("%s follows %s: %b", v2, v1, v2.follows(v1)) );
        System.out.println( String.format("%s follows %s: %b", v1, v3, v1.follows(v3)) );

        World.printLine();

        Vector2d u1 = new Vector2d(2, 1);
        Vector2d u2 = new Vector2d(1, 2);

        System.out.println( String.format("%s upperRight %s is %s", u1, u2, u1.upperRight(u2) ) );
        System.out.println( String.format("%s lowerLeft %s is %s", u1, u2, u1.lowerLeft(u2) ) );

        World.printLine();


        Vector2d position1 = new Vector2d(1, 2);
        System.out.println(position1);
        Vector2d position2 = new Vector2d(-2, 1);
        System.out.println(position2);
        System.out.println(position1.add(position2));
    }
    @Override
    public String toString() {
        return String.format("(%d,%d)", this.x, this.y);
    }
}
