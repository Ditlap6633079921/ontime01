package Page;

import pane.RootPane;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Application;

public class HomePage extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        RootPane root = new RootPane();

        Scene scene = new Scene(root, 800, 500);

        stage.setTitle("To-do List App");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
