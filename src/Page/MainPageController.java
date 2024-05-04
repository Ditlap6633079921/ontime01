package Page;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class MainPageController {

    @FXML
    private AnchorPane root;

    @FXML
    private Button doneButton;

    @FXML
    private Button addButton;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private DatePicker filterDatePicker;

    @FXML
    private TextField taskDescriptionTextField;

    @FXML
    private TextField taskDescriptionFilterTextField;

    @FXML
    private ComboBox<String> taskTypeComboBox;

    @FXML
    private Button filterButton;

    @FXML
    private Button clearButton;

    @FXML
    private Button filterByDateButton;

    public AnchorPane getRoot() {
        return root;
    }

    public Button getDoneButton() {
        return doneButton;
    }

    public Button getAddButton() {
        return addButton;
    }

    public DatePicker getStartDatePicker() {
        return startDatePicker;
    }

    public DatePicker getEndDatePicker() {
        return endDatePicker;
    }

    public DatePicker getFilterDatePicker() {
        return filterDatePicker;
    }

    public TextField getTaskDescriptionTextField() {
        return taskDescriptionTextField;
    }

    public TextField getTaskDescriptionFilterTextField() {
        return taskDescriptionFilterTextField;
    }

    public ComboBox<String> getTaskTypeComboBox() {
        return taskTypeComboBox;
    }

    public Button getFilterButton() {
        return filterButton;
    }

    public Button getClearButton() {
        return clearButton;
    }

    public Button getFilterByDateButton() {
        return filterByDateButton;
    }
}