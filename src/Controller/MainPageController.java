package Controller;

import Task.MustDoTask;
import Task.RecurringTask;
import Task.SideTask;
import Task.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Scanner;
import java.util.stream.Collectors;

public class MainPageController {

    // Task
    private ObservableList<Task> allTasks;
    //Date
    public static MainPageController instance = null;

    public static MainPageController getInstance() {
        if (instance == null) {
            instance = new MainPageController();
        }
        return instance;
    }

    public void startApp() {
        allTasks = FXCollections.observableArrayList();
        fetchTasksData();
    }

    public void addTask(String taskDescription, LocalDate startDate, LocalDate endDate, String taskType) {
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
        storeTasksData();
    }

    public void removeTask(int index) {
        allTasks.remove(index);
        storeTasksData();
    }

    public ObservableList<Task> filterTasks(LocalDate date, String type) {
        ObservableList<Task> filteredTasks = allTasks;
        if (date != null) {
            filteredTasks = allTasks.stream()
                    .filter(task -> task.getDeadline().isEqual(date) || (task instanceof RecurringTask && (date.isEqual(((RecurringTask) task).getStartDate()) || date.isAfter(((RecurringTask) task).getStartDate())) && date.isBefore(((RecurringTask) task).getDeadline())))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }
        if(type == null) return filteredTasks;
        if(type.equals("Must Do") || type.equals("Side Task") || type.equals("Recurring Task")) {
            filteredTasks = filteredTasks.stream()
                    .filter(task -> task.getType().equals(type))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }
        return filteredTasks;
    }

    public void fetchTasksData() {
        try (FileReader file = new FileReader("./src/Tasks.json")) {
            String stringTasks = (new Scanner(file).nextLine()).toString();
            JSONArray arrTasks = new JSONArray(stringTasks);

            for(int i=0;i<arrTasks.length();i++) {
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

    public void storeTasksData() {
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

    public void setAllTasks(ObservableList<Task> allTasks) {
        this.allTasks = allTasks;
    }

    public ObservableList<Task> getAllTasks() {
        return allTasks;
    }


}