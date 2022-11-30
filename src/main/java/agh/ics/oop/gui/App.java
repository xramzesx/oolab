package agh.ics.oop.gui;

import agh.ics.oop.SimulationEngine;
import agh.ics.oop.Vector2d;
import agh.ics.oop.elements.AbstractWorldMapElement;
import agh.ics.oop.enums.MoveDirection;
import agh.ics.oop.interfaces.IEngine;
import agh.ics.oop.interfaces.IMapElement;
import agh.ics.oop.interfaces.IPositionChangeObserver;
import agh.ics.oop.interfaces.IWorldMap;
import agh.ics.oop.maps.GrassField;
import agh.ics.oop.utilities.OptionsParser;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

import static java.lang.System.out;


public class App extends Application implements IPositionChangeObserver {
    public static int gridDimension = 40;

    public int windowWidth = 400;
    public int windowHeight = 300;

    public GridPane grid;
    public Scene scene;
    private Stage stage;
    public IWorldMap map;
    public IEngine engine;
    public Thread engineThread;
    private int moveDelay = 1000;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            MoveDirection[] directions = OptionsParser
                .parse(
                    getParameters()
                        .getRaw()
                        .toArray(new String[0])
                );

            this.map = new GrassField(10);

            Vector2d[] positions = { new Vector2d(2, 2), new Vector2d(3, 4)};

            this.engine = new SimulationEngine(directions, this.map, positions, this::positionChanged);

            this.grid = new GridPane();
            this.grid.setGridLinesVisible(true);

            Platform.runLater(()->{
                try {
                    this.draw( this.map.getLowerLeft(), this.map.getUpperRight() );
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });

            this.engineThread = new Thread((Runnable) this.engine);


            this.engineThread.start();
            this.scene = new Scene(
                    grid,
                    windowWidth,
                    windowHeight
            );

            System.out.println(this.engine);

            primaryStage.setScene(this.scene);
            primaryStage.show();

            this.stage = primaryStage;
            this.stage.setMaximized(true);
        } catch (IllegalArgumentException e) {
            out.println("\nIllegal argument: " + e.getMessage() + "\n" );
            primaryStage.close();
            Platform.exit();
        }
    }

    public GridPane draw(Vector2d lowerLeft, Vector2d upperRight) throws FileNotFoundException {


//        Node node = grid.getChildren().get(0);
        this.grid.setGridLinesVisible(false);
        this.grid.getRowConstraints().clear();
        this.grid.getColumnConstraints().clear();
        this.grid.getChildren().clear();

        this.grid.setGridLinesVisible(true);

        int maxWidth = upperRight.x - lowerLeft.x + 1;
        int maxHeight = upperRight.y - lowerLeft.y + 1;

        System.out.println(lowerLeft + " " + upperRight + " " + maxWidth + " " + maxHeight);

        //// SETUP GRID SIZE ////

        for ( int i = 0; i <= maxWidth; i++ )
            this.grid.getColumnConstraints().add(new ColumnConstraints( gridDimension ));

        for ( int i = 0; i <= maxHeight; i++ )
            this.grid.getRowConstraints().add(new RowConstraints( gridDimension ));

        //// SETUP HEADERS ////

        Label xy = new Label("x\\y");
        GridPane.setHalignment(xy, HPos.CENTER);
        this.grid.add(xy, 0, 0);

        for ( int i = 1; i <= maxWidth; i++ ) {
            Label label = new Label(String.valueOf( lowerLeft.x + i - 1));
            GridPane.setHalignment( label, HPos.CENTER );
            this.grid.add(label, i, 0);
        }

        for ( int i = 1; i <= maxHeight; i++ ) {
            Label label = new Label(String.valueOf( upperRight.y - i + 1));
            GridPane.setHalignment( label, HPos.CENTER );
            this.grid.add(label, 0, i);
        }

        //// SETUP MAP ELEMENTS ////

        for ( int i = 1; i <= maxHeight; i++ ) {
            int y = upperRight.y - i + 1;
            for ( int j = 1; j <= maxWidth; j++ ) {
                int x = lowerLeft.x + j - 1;

                String content = " ";

                Vector2d currentPosition = new Vector2d(x, y);

                if ( this.map.isOccupied( currentPosition ) ) {
                    Object object = this.map.objectAt(currentPosition);

                    if ( object != null ) {
                        content = object.toString();
                        System.out.println(content + " " + String.valueOf(j) + " " + String.valueOf(i) + ((AbstractWorldMapElement) object).getPosition());
                    }
                    else
                        content = " ";
                } else {
                    content = " ";
                }
                GuiElementBox guiElementBox = new GuiElementBox((IMapElement) this.map.objectAt(currentPosition));

                Label label = new Label(content);
                GridPane.setHalignment( label, HPos.CENTER );

                this.grid.add(guiElementBox.container, j, i, 1, 1);
//                this.grid.add(label, j, i, 1, 1);
            }
        }

        this.windowWidth = gridDimension * (maxWidth + 1 + 1 / 2);
        this.windowHeight = gridDimension * (maxHeight + 1 + 1);

//        this.stage.setWidth(this.windowWidth);
//        this.stage.setHeight(this.windowHeight);
        System.out.println("Stage: " + this.windowWidth + " " + this.windowHeight);


        return this.grid;
    }

    @Override
    public boolean positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        try {
            Thread.sleep(moveDelay);
        } catch ( InterruptedException ex ) {
            System.out.println("InterruptedException: " + ex.getMessage());
        }
        Platform.runLater(() -> {
            try {
                this.draw(this.map.getLowerLeft(), this.map.getUpperRight());
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        return true;
    }
}
