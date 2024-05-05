package pane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import utils.Goto;

//This class for layout behind of all page
public class RootPane extends VBox {
    public RootPane() {
        setAlignment(Pos.TOP_CENTER);
        setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN, CornerRadii.EMPTY, Insets.EMPTY)));

        Goto.setRootPane(this);
        Goto.homePage();
    }
}