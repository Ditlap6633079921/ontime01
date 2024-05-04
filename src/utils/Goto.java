package utils;

import Controller.MainPageController;
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
        MainPageController.getInstance().startApp();
        // allTasksListView
        ListView<Task> allTasksListView = new ListView<Task>();
        allTasksListView.setItems(MainPageController.getInstance().getAllTasks());
        allTasksListView.setPrefWidth(800); // Set width to accommodate all tasks
        allTasksListView.setPrefHeight(500); // Adjusted height
        allTasksListView.setStyle("-fx-background-radius: 10px; -fx-border-color: black; -fx-border-width: 1" +
                "px;");
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

        Button addButton = BarButton("Add", 0, Color.SPRINGGREEN, Color.BLACK, 4);
        onHoverButton(addButton, Color.LIMEGREEN, Color.SPRINGGREEN);

        addButton.setOnAction(e -> {
            // Add the task with the updated end date
            MainPageController.getInstance().addTask(taskInput.getText(), startDatePicker.getValue(), endDatePicker.getValue(), taskTypeComboBox.getValue());
            // Reset input fields
            taskInput.clear();
            startDatePicker.setValue(null);
            endDatePicker.setValue(null);
            taskTypeComboBox.setValue("Must Do"); // Reset task type to "Must Do"
        });

        Button removeButton = BarButton("Remove", 0, Color.ORANGERED, Color.BLACK, 4);
        onHoverButton(removeButton, Color.RED, Color.ORANGERED);
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
        Button filterButton = BarButton("Filter", 0, Color.LIGHTGREEN, Color.BLACK, 4);
        onHoverButton(filterButton, Color.LIMEGREEN, Color.LIGHTGREEN);
        filterButton.setOnAction(e -> {
            allTasksListView.setItems(MainPageController.getInstance().filterTasksByDate(filterDatePicker.getValue()));
        });

        Button clearButton = BarButton("Clear", 0, Color.ORANGE, Color.BLACK, 4);
        onHoverButton(clearButton, Color.ORANGERED, Color.ORANGE);
        clearButton.setOnAction(e -> {
            filterDatePicker.setValue(null);
            allTasksListView.setItems(MainPageController.getInstance().filterTasksByDate(null)); // Show all tasks
        });

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(new Label("Filter by Date:"), filterDatePicker, filterButton, clearButton);
        buttonBox.setPadding(new Insets(10));

        Label allTasksLabel = new Label("All Tasks");
        allTasksLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        // Add components to the root layout
        rootPane.getChildren().addAll(allTasksLabel, buttonBox, allTasksListView, inputBox);
        rootPane.setPadding(new Insets(10));
    }

    /* ---------------------- Button ------------------------- */

    public static Button BarButton(String string, int width, Color bgcolor, Color textcolor, int radius) {
        Button button = new Button(string);
        button.setTextFill(textcolor);
        if (width != 0) {
            button.setPrefWidth(width);
        }
        button.setBackground(new Background(new BackgroundFill(bgcolor, new CornerRadii(radius), Insets.EMPTY)));

        return button;
    }

    public static void onHoverButton(Button button, Color enter, Color exit) {
        button.setOnMouseEntered(e -> button.setBackground(new Background(new BackgroundFill(enter, new CornerRadii(4), Insets.EMPTY))));
        button.setOnMouseExited(e -> button.setBackground(new Background(new BackgroundFill(exit, new CornerRadii(4), Insets.EMPTY))));
    }

}