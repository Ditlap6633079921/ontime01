package Page;

import Controller.MainPageController;
import GUI.TaskListCell;
import Task.Task;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;


public class MainPage {
    public MainPage(VBox root) {

        MainPageController.getInstance().startApp();
        // allTasksListView
        ListView<Task> allTasksListView = new ListView<Task>();
        allTasksListView.setItems(MainPageController.getInstance().getAllTasks());
        allTasksListView.setPrefWidth(600); // Set width to accommodate all tasks
        allTasksListView.setPrefHeight(200); // Adjusted height
        allTasksListView.setCellFactory(param -> new ListCell<Task>(){
            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null) {
                    setText(null);
                } else {
                    setText(task.toString());
                    // If the task is overdue, set text to red
                    if (task.getDeadline().isBefore(LocalDate.now())) {
                        setStyle("-fx-text-fill: red;");
                    } else {
                        setStyle(""); // Reset style
                    }
                }
            }
        });

        // Create the input fields and buttons
        TextField taskInput = new TextField();
        taskInput.setPromptText("Enter a task");
        taskInput.setPrefWidth(160);

        DatePicker startDatePicker = new DatePicker(); // Start date picker for recurring tasks
        startDatePicker.setPrefWidth(80);
        startDatePicker.setDisable(true); // Initially disabled
        DatePicker endDatePicker = new DatePicker(); // End date picker for all tasks
        endDatePicker.setPrefWidth(80);

        ComboBox<String>taskTypeComboBox = new ComboBox<>();
        taskTypeComboBox.getItems().addAll("Must Do", "Side Task", "Recurring Task");
        taskTypeComboBox.setOnAction(e -> {
            startDatePicker.setValue(null);
            endDatePicker.setValue(null);
        });
        taskTypeComboBox.setValue("Must Do");
        taskTypeComboBox.setPrefWidth(100);

        Button addButton = new Button("Add");

        addButton.setOnAction(e -> {
            // Add the task with the updated end date
            MainPageController.getInstance().addTask(taskInput.getText(), startDatePicker.getValue(), endDatePicker.getValue(), taskTypeComboBox.getValue());
            // Reset input fields
            taskInput.clear();
            startDatePicker.setValue(null);
            endDatePicker.setValue(null);
            taskTypeComboBox.setValue("Must Do"); // Reset task type to "Must Do"
        });

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> {
            int index = allTasksListView.getSelectionModel().getSelectedIndex();
            if(index != -1)
                MainPageController.getInstance().removeTask(index);
        });

        HBox inputBox = new HBox(10);
        inputBox.getChildren().addAll(taskInput, new Label("Start Date:"), startDatePicker, new Label("End Date:"), endDatePicker, new Label("Task Type:"), taskTypeComboBox, addButton, removeButton);
        inputBox.setPadding(new Insets(10));

        // Add event listener to taskTypeComboBox to enable/disable startDatePicker
        taskTypeComboBox.valueProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue.equals("Recurring Task")) {
                startDatePicker.setDisable(false);
            } else {
                startDatePicker.setDisable(true);
            }
        });

        // Create filter controls
        DatePicker filterDatePicker = new DatePicker();
        Button filterButton = new Button("Filter");
        filterButton.setOnAction(e -> {
            allTasksListView.setItems(MainPageController.getInstance().filterTasksByDate(filterDatePicker.getValue()));
        });

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(e -> {
            filterDatePicker.setValue(null);
            allTasksListView.setItems(MainPageController.getInstance().filterTasksByDate(null)); // Show all tasks
        });

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(new Label("Filter by Date:"), filterDatePicker, filterButton, clearButton);
        buttonBox.setPadding(new Insets(10));

        // Add components to the root layout
        root.getChildren().addAll(new Label("All Tasks"), buttonBox, allTasksListView, inputBox);
        root.setPadding(new Insets(10));
    }
}
