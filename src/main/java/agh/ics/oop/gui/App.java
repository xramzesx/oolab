package agh.ics.oop.gui;

import agh.ics.oop.SimulationEngine;
import agh.ics.oop.Vector2d;
import agh.ics.oop.elements.AbstractWorldMapElement;
import agh.ics.oop.enums.MoveDirection;
import agh.ics.oop.interfaces.IEngine;
import agh.ics.oop.interfaces.IMapElement;
import agh.ics.oop.interfaces.IWorldMap;
import agh.ics.oop.maps.GrassField;
import agh.ics.oop.utilities.OptionsParser;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;


public class App extends Application {
    public static int gridDimension = 20;

    public int windowWidth = 400;
    public int windowHeight = 300;

    public IWorldMap map;
    public IEngine engine;

    @Override
    public void start(Stage primaryStage) throws Exception {

        MoveDirection[] directions = OptionsParser
            .parse(
                getParameters()
                    .getRaw()
                    .toArray(new String[0])
            );

        this.map = new GrassField(10);

        Vector2d[] positions = { new Vector2d(2, 2), new Vector2d(3, 4)};

        this.engine = new SimulationEngine(directions, this.map, positions);
        this.engine.run();

        Scene scene = new Scene(
            draw(
                this.map.getLowerLeft(),
                this.map.getUpperRight()
            ),
            windowWidth,
            windowHeight
        );

        System.out.println(this.engine);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public GridPane draw(Vector2d lowerLeft, Vector2d upperRight) {

        GridPane gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

        int maxWidth = upperRight.x - lowerLeft.x + 1;
        int maxHeight = upperRight.y - lowerLeft.y + 1;

        System.out.println(lowerLeft + " " + upperRight + " " + maxWidth + " " + maxHeight);

        //// SETUP GRID SIZE ////

        for ( int i = 0; i <= maxWidth; i++ )
            gridPane.getColumnConstraints().add(new ColumnConstraints( gridDimension ));

        for ( int i = 0; i <= maxHeight; i++ )
            gridPane.getRowConstraints().add(new RowConstraints( gridDimension ));

        //// SETUP HEADERS ////

        Label xy = new Label("x\\y");
        gridPane.add(xy, 0, 0);

        for ( int i = 1; i <= maxWidth; i++ ) {
            Label label = new Label(String.valueOf( lowerLeft.x + i - 1));
            GridPane.setHalignment( label, HPos.CENTER );
            gridPane.add(label, i, 0);
        }

        for ( int i = 1; i <= maxHeight; i++ ) {
            Label label = new Label(String.valueOf( upperRight.y - i + 1));
            GridPane.setHalignment( label, HPos.CENTER );
            gridPane.add(label, 0, i);
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

                Label label = new Label(content);
                GridPane.setHalignment( label, HPos.CENTER );

                gridPane.add(label, j, i, 1, 1);
            }
        }

        this.windowWidth = gridDimension * (maxWidth + 1);
        this.windowHeight = gridDimension * (maxHeight + 1);

        return gridPane;
    }

}
