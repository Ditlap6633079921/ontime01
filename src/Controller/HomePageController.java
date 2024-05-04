package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageController {
    public Button startBtn;

    public void handleGoToMainPage(ActionEvent event) {
        try {
            // Load the FXML file
            Parent root = FXMLLoader.load(getClass().getResource("../resources/FXML/main.fxml"));

            Scene mainScene = new Scene(root);

            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

            window.setScene(mainScene);
            window.show();

        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + e.getMessage());
        }
    }
}
