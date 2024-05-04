package pane;

import utils.Goto;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.Background;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.BackgroundFill;

//This class for layout behind of all page
public class RootPane extends VBox {
    public RootPane() {
        setAlignment(Pos.TOP_CENTER);
        setPadding(new Insets(32, 0, 0, 0));
        setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN, CornerRadii.EMPTY, Insets.EMPTY)));

        Goto.setRootPane(this);
        Goto.homePage();
    }
}