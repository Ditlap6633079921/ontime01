package pane;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import utils.Goto;
import javafx.geometry.Pos;
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

        Text newText = new Text("On-Time App");
        newText.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        Button button = Goto.BarButton("Get Started", 200, Color.SLATEBLUE, Color.WHITE, 20);
        button.setPrefHeight(50);
        button.setFont(new Font(18));
        DropShadow dropShadow = new DropShadow();
        dropShadow.setRadius(10.0);
        dropShadow.setOffsetX(1.0);
        dropShadow.setOffsetY(1.0);
        dropShadow.setColor(Color.rgb(50, 50, 50, 0.5));
        button.setEffect(dropShadow);

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
        vBox.getChildren().addAll(image, newText, button);

        setPrefHeight(690);
        vBox.setPadding(new Insets(110, 0, 0, 0));
        getChildren().addAll(vBox);
    }
}