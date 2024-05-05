package Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GeneralTask extends Task {
    private String taskType;
    public GeneralTask(String taskDescription, LocalDate deadline, String taskType) {
        super(taskDescription, deadline);
        this.taskType = taskType;
    }

    public void done() {
        this.wasDone = true;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    @Override
    public String toString() {
        String deadlineString = deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return "( " + taskType + " ) " + description + " | Deadline: " + deadlineString;
    }
}
