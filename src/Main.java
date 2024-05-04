import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

//    private Stage primaryStage;
//    private MainPage mainPage;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file
            Parent root = FXMLLoader.load(getClass().getResource("resources/FXML/home.fxml"));

            // Set up the stage
            primaryStage.setTitle("To-do List App");
            primaryStage.setScene(new Scene(root, 800, 500));
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + e.getMessage());
        }
    }

//    private void goToDashboard() {
//        // Set the fixed size for the stage
//        primaryStage.setResizable(false);
//
//        // Create the dashboard scene
//        Scene scene = new Scene(mainPage.getRoot(), 600, 300);
//
//        // Set the scene
//        primaryStage.setScene(scene);
//        primaryStage.setTitle("To-do List");
//
//        // Show the stage
//        primaryStage.show();
//    }
}
