package Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Task {
    protected String description;
    protected LocalDate deadline;
    protected String type;

    public Task(String description, LocalDate deadline, String type) {
        this.description = description;
        this.deadline = deadline;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public abstract void doTask();

    @Override
    public String toString() {
        String deadlineString = deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return description + " (" + type + ") - Deadline: " + deadlineString;
    }
}
