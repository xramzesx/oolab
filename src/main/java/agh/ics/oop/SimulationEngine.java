package agh.ics.oop;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

public class SimulationEngine implements IEngine {

    private final IWorldMap map;
    private final ArrayList<Animal> animals = new ArrayList<>();
    private final MoveDirection[] directions;

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

    SimulationEngine(
            MoveDirection[] directions,
            IWorldMap map,
            Vector2d[] positions
    ){
        this.map = map;
        this.directions = directions;

        for( Vector2d position: positions ) {
            Animal animal = new Animal( this.map, position );

            if ( this.map.canMoveTo(animal.getPosition()) ){
                this.map.place(animal);
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

    public void run() {
        int n = this.animals.size();

        for (int i = 0; i < this.directions.length; i++ ) {
            this.animals.get(i % n).move(this.directions[i]);
            if ( this.isWindowActive ) {
                this.sleep(sleepTime);
                updateWindow(this.map.toString());
            }
        }
        if ( this.isWindowActive )
            this.closeWindow();
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    @Override
    public String toString() {
        return this.map.toString();
    }
}
