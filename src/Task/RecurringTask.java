package Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RecurringTask extends Task {

    private LocalDate startDate;

    public RecurringTask(String taskDescription, LocalDate startDate, LocalDate endDate) {
        super(taskDescription, endDate, "Recurring Task");
        this.startDate = startDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    @Override
    public void doTask() {
        // Implementation specific to RecurringTask
        System.out.println("Completing recurring task: " + getDescription());
    }

    @Override
    public String toString() {
        String deadlineString = deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String startString = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return description + " (" + type + ") - Start: " + startString + " - Deadline: " + deadlineString;
    }
}
