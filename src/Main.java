import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import Page.MainPage;

public class Main extends Application {

    private Stage primaryStage;
    private StackPane root;
    private MainPage mainPage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("To-do List App");

        // Create the welcome scene
        root = new StackPane();
        Scene welcomeScene = new Scene(root, 300, 200);

        Button getStartedButton = new Button("Get Started");
        getStartedButton.setOnAction(e -> {
            // Launch the ToDoListApp
            mainPage = new MainPage(new VBox());
            goToDashboard();
        });

        root.getChildren().add(getStartedButton);

        // Show the welcome scene
        primaryStage.setScene(welcomeScene);
        primaryStage.show();
    }

    private void goToDashboard() {
        // Set the fixed size for the stage
        primaryStage.setResizable(false);

        // Create the dashboard scene
        Scene scene = new Scene(mainPage.getRoot(), 600, 300);

        // Set the scene
        primaryStage.setScene(scene);
        primaryStage.setTitle("To-do List");

        // Show the stage
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
