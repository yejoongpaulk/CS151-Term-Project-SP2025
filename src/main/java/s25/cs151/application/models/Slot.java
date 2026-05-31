package s25.cs151.application.models;

import s25.cs151.application.models.validators.ModelValidators;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Locale;

public class Slot {
    private long id;
    private String startTime;
    private String endTime;


    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("h:mm a", Locale.US);
    public static final Comparator<Slot> START_TIME_COMPARATOR = Comparator.comparing(
            slot -> LocalTime.parse(slot.getStartTime(), TIME_FORMATTER)
    );

    /**
     * Given a Semester object, and start/end time of slot, create a Slot instance.
     * @param id - id for object
     * @param startTime - start time of slot
     * @param endTime - end time of slot
     */
     public Slot(long id, String startTime, String endTime) {
        ModelValidators.checkId(id);
        ModelValidators.checkStartTime(startTime);
        ModelValidators.checkEndTime(endTime);

        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Get the (database) ID associated with the object.
     * @return long
     */
    public long getId() {
        return this.id;
    }

    /**
     * Get start time for Slot instance.
     * @return start time
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * Set start time for Slot instance.
     * @param startTime - Slot's start time
     */
    public void setStartTime(String startTime) {
        ModelValidators.checkStartTime(startTime);
        this.startTime = startTime;
    }

    /**
     * Get end time for Slot instance.
     * @return end time
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * Set end time for Slot instance.
     * @param endTime - Slot's end time
     */
    public void setEndTime(String endTime) {
        ModelValidators.checkEndTime(endTime);
        this.endTime = endTime;
    }

    /**
     * Returns a String representation of Slot in the format "startTime - endTime" (e.g. 4:30 PM - 4:45 PM).
     *
     * @return String representation showing slot's interval.
     */
    @Override
    public String toString() {
        return startTime + " - " + endTime;
    }
}
