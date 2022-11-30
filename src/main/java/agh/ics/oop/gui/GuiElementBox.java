package agh.ics.oop.gui;

import agh.ics.oop.interfaces.IMapElement;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GuiElementBox {
    private int gridSize = 20;
    private Image image;
    private ImageView view;
    private Label position;

    public VBox container;


    GuiElementBox(
        IMapElement element
    ) throws FileNotFoundException {
        String url = "";
        String label = "";

        if ( element != null ) {
            url = element.getImagePath();
            label = element.getImageLabel();
        }

        this.image = url.length() > 0 ? new Image( new FileInputStream(url)) : null;
        this.view = new ImageView(this.image);

        this.view.setFitHeight(this.gridSize);
        this.view.setFitWidth(this.gridSize);

        this.position = new Label( label );

        this.container = new VBox();

        this.container.getChildren().add(this.view);
        this.container.getChildren().add(this.position);

        this.container.setAlignment(Pos.CENTER);
    }

    void updateElement( IMapElement element ) {
        if (element == null) {
            this.image = null;
            this.view.setImage(this.image);
            this.position.setText("");
        } else {
            this.image = new Image(element.getImagePath());
            this.view.setImage(this.image);
            this.position.setText(element.getPosition().toString());
        }
    }
}
