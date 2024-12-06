package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class TimeFrame {
    //man får deleta tiden om man vill ändra
    private final LocalTime startTime;
    private final LocalTime endTime;
    private final LocalDate date;

    public TimeFrame(LocalTime startTime, LocalTime endTime, LocalDate date) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public LocalDate getDate() {
        return date;
    }
}
