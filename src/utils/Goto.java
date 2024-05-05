package utils;

import Controller.MainPageController;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import pane.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import pane.RootPane;
import Task.*;

import java.time.LocalDate;

//This class for link the page and have all button
public class Goto {
    private static RootPane rootPane;
    public static void setRootPane(RootPane rootPane) { Goto.rootPane = rootPane; }
    public static void clear() {
        if (!rootPane.getChildren().isEmpty()) {
            rootPane.getChildren().remove(0, rootPane.getChildren().size());
        }
    }


    /* ---------------------- Main - Page ------------------------- */
    public static void homePage() {
        clear();
        StackPane stackPane = new StackPane();
        HomePane homePane = new HomePane();
        rootPane.getChildren().addAll(homePane, stackPane);
    }
    public static void mainPage() {
        clear();
        MainPane mainPane = new MainPane();
        rootPane.getChildren().addAll(mainPane);
    }

    /* ---------------------- Button ------------------------- */

    public static Button BarButton(String string, int width, Color bgcolor, Color textcolor, int radius) {
        Button button = new Button(string);
        button.setTextFill(textcolor);
        if (width != 0) {
            button.setPrefWidth(width);
        }
        button.setBackground(new Background(new BackgroundFill(bgcolor, new CornerRadii(radius), Insets.EMPTY)));
        button.setStyle("-fx-border-color: lightgrey; -fx-border-width: 1px; -fx-border-radius: " + radius);

        return button;
    }

    public static void onHoverButton(Button button, Color enter, Color exit, Color entertextcolor, Color exittextcolor) {
        button.setOnMouseEntered(e -> {
            button.setBackground(new Background(new BackgroundFill(enter, new CornerRadii(4), Insets.EMPTY)));
            button.setTextFill(entertextcolor);
        });
        button.setOnMouseExited(e -> {
                button.setBackground(new Background(new BackgroundFill(exit, new CornerRadii(4), Insets.EMPTY)));
                button.setTextFill(exittextcolor);
        });
    }

}