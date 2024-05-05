package Task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class RecurringTask extends Task {

    private LocalDate startDate;
    private LocalDate dateWasDone;

    public RecurringTask(String taskDescription, LocalDate startDate, LocalDate endDate) {
        super(taskDescription, endDate);
        this.startDate = startDate;
        this.dateWasDone = null;
    }

    public void done() {
        this.wasDone = true;
        dateWasDone = LocalDate.now();
    }

    public boolean dayDone(LocalDate inputDate) {
        return true;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getDateWasDone() {
        return dateWasDone;
    }

    public void setDateWasDone(LocalDate dateWasDone) {
        this.dateWasDone = dateWasDone;
    }

    @Override
    public String toString() {
        String deadlineString = deadline.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String startString = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return "( Recurring Task ) " + description + " | From: " + startString + " Until: " + deadlineString;
    }
}
