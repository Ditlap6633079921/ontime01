package Controller;

import Task.GeneralTask;
import Task.RecurringTask;
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
    private static MainPageController instance = null;

    public static MainPageController getInstance() {
        if (instance == null) {
            instance = new MainPageController();
        }
        return instance;
    }

    public void startApp() {
        allTasks = FXCollections.observableArrayList();
        fetchTasksData();
        for(Task task : allTasks) {
            if (task instanceof RecurringTask && ((RecurringTask) task).getDateWasDone() != null && !((RecurringTask) task).getDateWasDone().isEqual(LocalDate.now())) {
                task.setWasDone(false);
                ((RecurringTask) task).setDateWasDone(null);
            }
        }
    }

    public Task addTask(String taskDescription, LocalDate startDate, LocalDate endDate, String taskType) {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            // If so, set the end date equal to the start date
            endDate = startDate;
        }
        Task newTask = null;
        if (!taskDescription.isEmpty() && endDate != null) {
            switch (taskType) {
                case "Must Do":
                    newTask = new GeneralTask(taskDescription, endDate, "Must Do"); // For Must Do, use end date as deadline
                    break;
                case "Side Task":
                    newTask = new GeneralTask(taskDescription, endDate, "Side Task"); // For Side Task, use end date as deadline
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
        return newTask;
    }

    public void removeTask(int index) {
        if(index == -1) return;
        allTasks.remove(index);
        storeTasksData();
    }

    public void doneTask(int index) {
        if(index == -1) return;
        allTasks.get(index).done();
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
        if(type.equals("Must Do") || type.equals("Side Task")) {
            filteredTasks = filteredTasks.stream()
                    .filter(task -> (task instanceof GeneralTask) && ((GeneralTask) task).getTaskType().equals(type))
                    .collect(Collectors.toCollection(FXCollections::observableArrayList));
        }
        if(type.equals("Recurring Task")) {
            filteredTasks = filteredTasks.stream()
                    .filter(task -> (task instanceof RecurringTask))
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
                Task task = this.addTask(taskDescription, lcStartDate, lcEndDate, taskType);
                boolean wasDone = arrTasks.getJSONObject(i).getBoolean("Done");
                task.setWasDone(wasDone);
                if(taskType.equals("Recurring Task")){
                    String dateWasDone = arrTasks.getJSONObject(i).getString("DateWasDone");
                    if(dateWasDone.equals(""))
                        ((RecurringTask)task).setDateWasDone(null);
                    else
                        ((RecurringTask)task).setDateWasDone(LocalDate.parse(dateWasDone, formatter));
                }
            }
            System.out.println(arrTasks);
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
            String type = "";
            if(task instanceof GeneralTask) type = ((GeneralTask) task).getTaskType();
            if(task instanceof RecurringTask) type = "Recurring Task";
            JSONtask.put("Task Type", type);
            JSONtask.put("End Date",task.getDeadline());
            JSONtask.put("Done", task.getWasDone());
            if(type.equals("Recurring Task")) {
                JSONtask.put("Start Date",((RecurringTask)task).getStartDate());
                if(((RecurringTask)task).getDateWasDone() == null)
                    JSONtask.put("DateWasDone", "");
                else
                    JSONtask.put("DateWasDone", ((RecurringTask)task).getDateWasDone());
            }
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