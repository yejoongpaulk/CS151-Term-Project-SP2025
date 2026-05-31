package s25.cs151.application.models;

import s25.cs151.application.models.validators.ModelValidators;

public class Appointment {
    private long id;
    private Course course;
    private Slot slot;
    private String student;
    private String date;
    private String reason;
    private String comment;

    /**
     * Given a Course, Slot, Student, Date, Reason, and Comment, create an Appointment instance.
     * @param id - long id
     * @param course - Course associated with the appointment
     * @param slot - Slot associated with the appointment
     * @param student - Student attending the appointment
     * @param date - Date of the appointment
     * @param reason - Reason for the appointment
     * @param comment - Additional comments
     */
   public Appointment(long id, Course course, Slot slot, String student, String date, String reason, String comment) {
        ModelValidators.checkId(id);
        ModelValidators.checkStudent(student);
        ModelValidators.checkDate(date);
        ModelValidators.checkSlot(slot);
        ModelValidators.checkCourse(course);

        this.id = id;
        this.course = course;
        this.slot = slot;
        this.student = student;
        this.date = date;
        this.reason = reason;
        this.comment = comment;
    }

    /**
     * Get the (database) ID associated with the object.
     * @return long
     */
    public long getId() {
        return this.id;
    }

    /**
     * Get the Course associated with the appointment.
     * @return Course
     */
    public Course getCourse() {
        return course;
    }

    /**
     * Returns a String representation of Course formatted as "courseCode-courseSection" (e.g. CS151-04)
     *
     * @return String representation of Course object used for display.
     */
    public String getCourseDisplay() {
        return course.getCode() + "-" + course.getSection();
    }

    /**
     * Set the Course associated with the appointment.
     * @param course - Course to set
     */
     public void setCourse(Course course) {
        ModelValidators.checkCourse(course);
        this.course = course;
    }

    /**
     * Get the Slot associated with the appointment.
     * @return Slot
     */
    public Slot getSlot() {
        return slot;
    }

    /**
     * Returns a String representation of Slot formatted as "startTime - endTime" (e.g. 4:30 PM - 4:45 PM).
     *
     * @return String representation of Slot object used for display.
     */
    public String getSlotDisplay() {
        return slot.toString();
    }

    /**
     * Set the Slot associated with the appointment.
     * @param slot - Slot to set
     */
    public void setSlot(Slot slot) {
        ModelValidators.checkSlot(slot);
        this.slot = slot;
    }

    /**
     * Get the Student associated with the appointment.
     * @return Student name
     */
    public String getStudent() {
        return student;
    }

    /**
     * Set the Student associated with the appointment.
     * @param student - Student name
     */
    public void setStudent(String student) {
        ModelValidators.checkStudent(student);
        this.student = student;
    }

    /**
     * Get the date of the appointment.
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * Set the date of the appointment.
     * @param date - Appointment date
     */
    public void setDate(String date) {
        ModelValidators.checkDate(date);
        this.date = date;
    }

    /**
     * Get the reason for the appointment.
     * @return reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Set the reason for the appointment.
     * @param reason - Reason for appointment
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * Get additional comments for the appointment.
     * @return comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set additional comments for the appointment.
     * @param comment - Additional comments
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return student + ", " + getCourseDisplay() + ", " + date + ", " + getSlotDisplay();
    }
}
