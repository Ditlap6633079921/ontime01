package Task;

import java.time.LocalDate;

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
}
