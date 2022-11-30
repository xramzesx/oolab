package agh.ics.oop;

import agh.ics.oop.elements.Animal;
import agh.ics.oop.enums.MoveDirection;
import agh.ics.oop.interfaces.IEngine;
import agh.ics.oop.interfaces.IPositionChangeObserver;
import agh.ics.oop.interfaces.IWorldMap;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class SimulationEngine implements IEngine, Runnable {
    private int currentAnimal = 0;
    private IPositionChangeObserver observer;
    private final IWorldMap map;
    private final ArrayList<Animal> animals = new ArrayList<>();
    private MoveDirection[] directions;

    private boolean isWindowActive = false;
    private JFrame frame;
    private JTextArea content;

    private static final int sleepTime = 500;

    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            System.out.println("Sleep failure");
        }
    }

    /// WINDOW UTILITIES ///

    private void setupWindow() {
        this.frame = new JFrame("Zwierzaczki");

        this.frame.setMinimumSize(new Dimension(400, 400));
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Font font = new Font("Consolas", Font.BOLD, 23);

        this.content = new JTextArea();
        this.content.setText(this.map.toString());
        this.content.setFont(font);

        this.frame.getContentPane().add(this.content);
        this.frame.pack();
        this.frame.setVisible(true);
    }

    private void updateWindow(String text) {
        this.content.setText(text);
    }

    private void closeWindow() {
        this.frame.setVisible(false);
        this.frame.dispose();
    }

    public SimulationEngine(
            MoveDirection[] directions,
            IWorldMap map,
            Vector2d[] positions,
            IPositionChangeObserver observer
    ) {
        this(directions, map, positions);
        this.observer = observer;
    }

    public SimulationEngine(
            MoveDirection[] directions,
            IWorldMap map,
            Vector2d[] positions
    ){
        this.map = map;
        this.directions = directions;

        for( Vector2d position: positions ) {
            Animal animal = new Animal( this.map, position );

            if ( this.map.place(animal) ){
                this.animals.add(animal);
            }
        }
    }

    public SimulationEngine(
            MoveDirection[] directions,
            IWorldMap map,
            Vector2d[] positions,
            boolean activateWindow
    ) {
        this(directions, map, positions);
        this.isWindowActive = activateWindow;

        if (this.isWindowActive)
            this.setupWindow();
    }

    @Override
    public void run() {
        int n = this.animals.size();
        if ( this.observer != null ) {
            this.observer.positionChanged(null, null);
        }
        for (int i = 0; i < this.directions.length; i++ ) {
            this.animals.get(this.currentAnimal).move(this.directions[i]);
            if ( this.observer != null ) {
                this.observer.positionChanged(null, null);
            }

            if ( this.isWindowActive ) {
                this.sleep(sleepTime);
                updateWindow(this.map.toString());
            }
            this.currentAnimal = (this.currentAnimal + 1) % n;
        }
        if ( this.isWindowActive )
            this.closeWindow();
    }


    public void setDirections( MoveDirection[] moveDirections ){
        this.directions = moveDirections;
    }
    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    @Override
    public String toString() {
        return this.map.toString();
    }
}
