package pane;

import javafx.scene.input.MouseDragEvent;
import utils.Goto;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

//This class for home page
public class HomePane extends VBox {
    public HomePane() {

        ImageView image = new ImageView("/resources/images/calendar.png");
        image.setFitHeight(160);
        image.setPreserveRatio(true);

        Button button = Goto.BarButton("On-Time App", 200, Color.SLATEBLUE, Color.WHITE, 20);
        button.setPrefHeight(50);
        button.setFont(new Font(18));

        button.setOnMouseEntered(e -> button.setBackground(new Background(new BackgroundFill(Color.DEEPSKYBLUE, new CornerRadii(20), Insets.EMPTY))));
        button.setOnMouseExited(e -> button.setBackground(new Background(new BackgroundFill(Color.SLATEBLUE, new CornerRadii(20), Insets.EMPTY))));
        button.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Goto.mainPage();
            }
        });

        VBox vBox = new VBox();
        vBox.setSpacing(50);
        vBox.setPrefHeight(690);
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setPadding(new Insets(32, 0, 0, 0));
        vBox.setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN, CornerRadii.EMPTY, Insets.EMPTY)));
        vBox.getChildren().addAll(image, button);

        setPrefHeight(690);
        setPadding(new Insets(32, 0, 0, 0));
        getChildren().addAll(vBox);
    }
}