package GUI;

import Task.MustDoTask;
import Task.SideTask;
import Task.Task;
import javafx.scene.control.ListCell;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaskListCell extends ListCell<Task> {
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
