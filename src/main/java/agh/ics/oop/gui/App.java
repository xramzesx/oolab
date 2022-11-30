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
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.util.Arrays;

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
        //// PREPARE GUI ////

        Button startButton = new Button("Start");
        TextField textField = new TextField();
        TextField delayField = new TextField();

        delayField.setText(String.valueOf(moveDelay));
        textField.setText(
            String.join(" ", getParameters().getRaw())
        );

        //// SETUP FORM ////

        HBox hBox = new HBox();
        hBox.getChildren().add(textField);
        hBox.getChildren().add(delayField);
        hBox.getChildren().add(startButton);
        hBox.setAlignment(Pos.CENTER);

        //// SETUP ERROR MESSAGE BOX ////

        Label errorMessage = new Label("");

        errorMessage.setPadding(new Insets(10));
        errorMessage.setTextFill(Color.color(1,0,0));

        HBox errorMessageBox = new HBox();

        errorMessageBox.getChildren().add(errorMessage);
        errorMessageBox.setAlignment(Pos.CENTER);

        //// SETUP GRID-BOARD ////

        HBox gridContainer = new HBox();

        this.grid = new GridPane();
        this.grid.setGridLinesVisible(true);

        gridContainer.getChildren().add(this.grid);
        gridContainer.setAlignment(Pos.CENTER);

        //// SETUP SCENE STRUCTURE ////

        VBox vBox = new VBox();
        vBox.getChildren().add(hBox);
        vBox.getChildren().add(errorMessageBox);
        vBox.getChildren().add(gridContainer);

        //// SETUP SCENE ////

        this.scene = new Scene(
            vBox,
            windowWidth,
            windowHeight
        );

        //// START SIMULATION ////

        try {
            MoveDirection[] directions = {};
            this.map = new GrassField(10);
            Vector2d[] positions = { new Vector2d(2, 2), new Vector2d(3, 4)};
            this.engine = new SimulationEngine(directions, this.map, positions, this);

            //// DRAW INITIAL GRID ////

            Platform.runLater(() -> {
                try {
                    this.draw( this.map.getLowerLeft(), this.map.getUpperRight() );
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            });

            this.engineThread = new Thread((Runnable) this.engine);

            System.out.println(this.engine);

            primaryStage.setScene(this.scene);
            primaryStage.show();

            this.stage = primaryStage;
            this.stage.setMaximized(true);

            //// SETUP LISTENER ////

            startButton.setOnAction( (e) -> {
                if ( !engineThread.isAlive() ) {
                    errorMessage.setText("");

                    try {
                        this.moveDelay = Integer.parseInt( delayField.getText() );

                        this.engineThread = new Thread((Runnable) this.engine);
                        this.engine.setDirections(
                                OptionsParser.parse(textField.getText().trim().split("\\s+"))
                        );

                        this.engineThread.start();

                    } catch (NumberFormatException ex){
                        errorMessage.setText("Error: \""+ delayField.getText() + "\" is not an integer");
                    } catch (IllegalArgumentException ex) {
                        errorMessage.setText("Error: "+ ex.getMessage());
                    }
                }
            });
        } catch (IllegalArgumentException e) {
            out.println("\nIllegal argument: " + e.getMessage() + "\n" );
            primaryStage.close();
            Platform.exit();
        }
    }

    public GridPane draw(Vector2d lowerLeft, Vector2d upperRight) throws FileNotFoundException {

        //// RESET GRID OPTIONS ////

        this.grid.setGridLinesVisible(false);
        this.grid.getRowConstraints().clear();
        this.grid.getColumnConstraints().clear();
        this.grid.getChildren().clear();

        //// SETTING UP GRIDLINES ////

        this.grid.setGridLinesVisible(true);

        int maxWidth = upperRight.x - lowerLeft.x + 1;
        int maxHeight = upperRight.y - lowerLeft.y + 1;

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

                        System.out.println(
                            content + " " +
                            String.valueOf(j) + " " +
                            String.valueOf(i) + " " +
                            ((AbstractWorldMapElement) object).getPosition()
                        );
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
            }
        }

        this.windowWidth = gridDimension * (maxWidth + 1);
        this.windowHeight = gridDimension * (maxHeight + 1);

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
