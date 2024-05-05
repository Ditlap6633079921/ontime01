package Task;

import java.time.LocalDate;

public abstract class Task {
    protected String description;
    protected LocalDate deadline;

    protected boolean wasDone;
//    protected String type;

    public Task(String description, LocalDate deadline) {
        this.description = description;
        this.deadline = deadline;
        this.wasDone = false;
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

    public abstract void done();

    public boolean getWasDone() {
        return wasDone;
    }

    public void setWasDone(boolean wasDone) {
        this.wasDone = wasDone;
    }

    //    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }

//    @Override
//    public String toString() {
//        String deadlineString = deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//        return description + " (" + type + ") - Deadline: " + deadlineString;
//    }
}
