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
    private Image image;
    private final ImageView view;
    private final Label label;

    public VBox container;


    GuiElementBox(
        IMapElement element
    ) throws FileNotFoundException {
        String url = "";
        String labelText = "";

        if ( element != null ) {
            url = element.getImagePath();
            labelText = element.getImageLabel();
        }

        this.image = url.length() > 0 ? new Image( new FileInputStream(url)) : null;
        this.view = new ImageView(this.image);

        int gridSize = 20;
        this.view.setFitHeight(gridSize);
        this.view.setFitWidth(gridSize);

        this.label = new Label( labelText );

        this.container = new VBox();

        this.container.getChildren().add(this.view);
        this.container.getChildren().add(this.label);

        this.container.setAlignment(Pos.CENTER);
    }

    void updateElement( IMapElement element ) {
        if (element == null) {
            this.image = null;
            this.view.setImage(this.image);
            this.label.setText("");
        } else {
            this.image = new Image(element.getImagePath());
            this.view.setImage(this.image);
            this.label.setText(element.getPosition().toString());
        }
    }
}
