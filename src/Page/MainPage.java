package Page;

import Task.MustDoTask;
import Task.RecurringTask;
import Task.SideTask;
import Task.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MainPage {

    // Task
    private ObservableList<Task> allTasks;
    private ListView<Task> allTasksListView;
    //Date
    private DatePicker startDatePicker;
    private DatePicker endDatePicker;
    private DatePicker filterDatePicker;
    // Layout
    private Button filterButton;
    private VBox root;
    private TextField taskInput;
    private ComboBox<String> taskTypeComboBox;

    public MainPage(VBox root) {
        this.root = root;
        allTasks = FXCollections.observableArrayList();
        allTasksListView = new ListView<>();
        allTasksListView.setItems(allTasks);
        allTasksListView.setPrefWidth(600); // Set width to accommodate all tasks
        allTasksListView.setPrefHeight(200); // Adjusted height
        allTasksListView.setCellFactory(param -> new TaskListCell());

        // Create the input fields and buttons
        taskInput = new TextField();
        taskInput.setPromptText("Enter a task");
        taskInput.setPrefWidth(160);
        startDatePicker = new DatePicker(); // Start date picker for recurring tasks
        startDatePicker.setPrefWidth(80);
        startDatePicker.setDisable(true); // Initially disabled
        endDatePicker = new DatePicker(); // End date picker for all tasks
        endDatePicker.setPrefWidth(80);
        taskTypeComboBox = new ComboBox<>();
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
            addTask(taskInput.getText(), startDatePicker.getValue(), endDatePicker.getValue(), taskTypeComboBox.getValue());

        });

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> removeTask());

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
        filterDatePicker = new DatePicker();
        filterButton = new Button("Filter");
        filterButton.setOnAction(e -> filterTasksByDate(filterDatePicker.getValue()));

        Button clearButton = new Button("Clear");
        clearButton.setOnAction(e -> {
            filterDatePicker.setValue(null);
            filterTasksByDate(null); // Show all tasks
        });

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(new Label("Filter by Date:"), filterDatePicker, filterButton, clearButton);
        buttonBox.setPadding(new Insets(10));

        // Add components to the root layout
        root.getChildren().addAll(new Label("All Tasks"), buttonBox, allTasksListView, inputBox);
        root.setPadding(new Insets(10));

        fetchTasksData();
    }

    private void addTask(String taskDescription, LocalDate startDate, LocalDate endDate, String taskType) {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            // If so, set the end date equal to the start date
            endDate = startDate;
        }
        if (!taskDescription.isEmpty() && endDate != null) {
            Task newTask = null;
            switch (taskType) {
                case "Must Do":
                    newTask = new MustDoTask(taskDescription, endDate); // For Must Do, use end date as deadline
                    break;
                case "Side Task":
                    newTask = new SideTask(taskDescription, endDate); // For Side Task, use end date as deadline
                    break;
                case "Recurring Task":
                    if (startDate != null && endDate != null) {
                        newTask = new RecurringTask(taskDescription, startDate, endDate); // For Recurring, use both start and end date
                    }
                    break;
            }
            if (newTask != null) {
                allTasks.add(newTask);
                // Sort tasks by date after adding new task
                allTasks.sort(Comparator.comparing(Task::getDeadline));
            }
        }
        // Reset input fields
        this.taskInput.clear();
        this.startDatePicker.setValue(null);
        this.endDatePicker.setValue(null);
        this.taskTypeComboBox.setValue("Must Do"); // Reset task type to "Must Do"
        storeTasksData();
    }

    private void fetchTasksData() {
        try (FileReader file = new FileReader("./src/Tasks.json")) {
            String stringTasks = (new Scanner(file).nextLine()).toString();
            JSONArray arrTasks = new JSONArray(stringTasks);

            for(int i=0;i<arrTasks.length();i++) {
                System.out.println(arrTasks.getJSONObject(i).getString("End Date"));
                String taskType = arrTasks.getJSONObject(i).getString("Task Type");
                String taskDescription = arrTasks.getJSONObject(i).getString("Task Description");
                String endDate = arrTasks.getJSONObject(i).getString("End Date");
                LocalDate lcStartDate = null;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-d");
                if(taskType.equals("Recurring Task")){
                    String startDate = arrTasks.getJSONObject(i).getString("Start Date");
                    lcStartDate = LocalDate.parse(startDate, formatter);
                }
                LocalDate lcEndDate = LocalDate.parse(endDate, formatter);
                this.addTask(taskDescription, lcStartDate, lcEndDate, taskType);
            }

            System.out.println("Successfully Read JSON file");
        } catch(Exception e){
            System.out.println(e);

        }
    }

    private void storeTasksData() {
        JSONArray JSONtasks = new JSONArray();
        for(Task task : allTasks) {
            JSONObject JSONtask = new JSONObject();
            JSONtask.put("Task Description",task.getDescription());
            JSONtask.put("Task Type",task.getType());
            if(task.getType().equals("Recurring Task")) {JSONtask.put("Start Date",((RecurringTask)task).getStartDate());}
            JSONtask.put("End Date",task.getDeadline());
            JSONtasks.put(JSONtask);
        }

        try (FileWriter file = new FileWriter("./src/Tasks.json")) {
            file.write(JSONtasks.toString());
        } catch(Exception e){
            System.out.println(e);
        }
    }

    private void removeTask() {
        int selectedIndex = allTasksListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex != -1) {
            allTasks.remove(selectedIndex);
        }
        storeTasksData();
    }

    private void filterTasksByDate(LocalDate date) {
        if (date != null) {
            ObservableList<Task> filteredTasks = allTasks.stream()
                    .filter(task -> task.getDeadline().isEqual(date) || (task instanceof RecurringTask && date.isAfter(((RecurringTask) task).getStartDate()) && date.isBefore(((RecurringTask) task).getDeadline())))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
            allTasksListView.setItems(filteredTasks);
        } else {
            // If no date selected, show all tasks
            allTasksListView.setItems(allTasks);
        }
    }

    // Custom ListCell to display task descriptions, deadlines, and types, with overdue tasks in red
    private class TaskListCell extends ListCell<Task> {
        @Override
        protected void updateItem(Task task, boolean empty) {
            super.updateItem(task, empty);
            if (empty || task == null) {
                setText(null);
            } else {
                String description = task.getDescription();
                String type = task instanceof MustDoTask ? "Must Do" : task instanceof SideTask ? "Side Task" : "Recurring Task";
                String deadlineString = task.getDeadline().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String text = description + " (" + type + ") - Deadline: " + deadlineString;
                setText(text);

                // If the task is overdue, set text to red
                if (task.getDeadline().isBefore(LocalDate.now())) {
                    setStyle("-fx-text-fill: red;");
                } else {
                    setStyle(""); // Reset style
                }
            }
        }
    }

    public VBox getRoot() {
        return root;
    }
}
