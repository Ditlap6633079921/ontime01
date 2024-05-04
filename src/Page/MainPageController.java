package Page;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class MainPageController {

    @FXML
    private TextField taskInput;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private DatePicker filterDatePicker;

    @FXML
    private Button addButton;

    @FXML
    private Button filterButton;

    @FXML
    private Button clearButton;

    @FXML
    protected void initialize() {
        // You can initialize anything here if needed
    }

    @FXML
    private void handleAddButtonClicked(ActionEvent event) {
        // Add your logic here for handling the add button click
    }

    @FXML
    private void handleFilterButtonClicked(ActionEvent event) {
        // Add your logic here for handling the filter button click
    }

    @FXML
    private void handleClearButtonClicked(ActionEvent event) {
        // Add your logic here for handling the clear button click
    }
}
