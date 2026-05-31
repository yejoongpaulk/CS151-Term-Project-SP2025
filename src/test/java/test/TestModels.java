package test;

import org.junit.jupiter.api.Test;
import s25.cs151.application.models.Course;
import s25.cs151.application.models.Semester;
import s25.cs151.application.models.Appointment; // new for version 0.7
import s25.cs151.application.models.Slot; // new for version 0.7
import s25.cs151.application.models.validators.ErrorMessageConstants;

import static org.junit.jupiter.api.Assertions.*;

public class TestModels {
    @Test
    /**
     * Test that Semesters work as intended.
     */
    void testSemester() {
        // create semester object
        Semester semester = new Semester(1, "Spring", "2025", "TTTTT");

        // ensure that information is gotten correctly
        assertEquals("Spring", semester.getSeason());
        assertEquals("2025", semester.getYear());
        assertEquals("TTTTT", semester.getDays());

        // make changes to semester's season
        semester.setSeason("Summer");

        // check that change is done correctly
        assertEquals("Summer", semester.getSeason());
    }

    // new tests for version 0.7

    /**
     * Test that Courses work as intended.
     */
     
    @Test
    void testCourse() {
        // create course object
        Course course = new Course(1, "CS151", "Object-Oriented Design", "01");

        // ensure that information is gotten correctly
        assertEquals("CS151", course.getCode());
        assertEquals("Object-Oriented Design", course.getName());
        assertEquals("01", course.getSection());

        // make changes to course's name
        course.setName("Object-Oriented Programming");

        // check that change is done correctly
        assertEquals("Object-Oriented Programming", course.getName());
    }


    /**
     * Test that Slots work as intended.
     */
    @Test
    void testSlot() {
        // create slot object
        Slot slot = new Slot(1, "10:00 AM", "11:00 AM");

        // ensure that information is gotten correctly
        assertEquals(1, slot.getId());
        assertEquals("10:00 AM", slot.getStartTime());
        assertEquals("11:00 AM", slot.getEndTime());

        // make changes to slot's day
        slot.setStartTime("10:30 AM");
        slot.setEndTime("11:30 AM");

        // check that change is done correctly
        assertEquals("10:30 AM", slot.getStartTime());
        assertEquals("11:30 AM", slot.getEndTime());
    }

    /**
     * Test that Appointments work as intended.
     */
    @Test
    void testAppointment() {
        // create course object
        Course course = new Course(1, "CS151", "Object-Oriented Design", "01");

        // create slot object
        Slot slot = new Slot(1, "10:00 AM", "11:00 AM");

        // create appointment object
        Appointment appointment = new Appointment(1, course, slot, "John Doe", "4/25/2025", "CS151 Term Project", "Will discuss SQL handling.");

        // ensure that information is gotten correctly
        assertEquals("John Doe", appointment.getStudent());
        assertEquals("CS151", appointment.getCourse().getCode());
        assertEquals("10:00 AM", appointment.getSlot().getStartTime());
        assertEquals("11:00 AM", appointment.getSlot().getEndTime());
        assertEquals("4/25/2025", appointment.getDate());
        assertEquals("CS151 Term Project", appointment.getReason());
        assertEquals("Will discuss SQL handling.", appointment.getComment());

        // make changes to appointment's information
        appointment.setComment("Will discuss handling errors.");
        appointment.setDate("4/26/2025");
        appointment.setReason("CS151 Term Project Errors");
        appointment.setStudent("John Smith");

        // check that change is done correctly
        assertEquals("Will discuss handling errors.", appointment.getComment());
        assertEquals("CS151 Term Project Errors", appointment.getReason());
        assertEquals("4/26/2025", appointment.getDate());
        assertEquals("John Smith", appointment.getStudent());
    }


    /**
     * Check that the semester correctly throws an IllegalArgumentException when it is passed an invalid value in the constructor.
     * The QA engineer responsible for this test case must check that the exception is thrown in the following circumstances:
     *          [X] id (long) is either strictly less than -1 or equal to 0. Any other number is valid.
     *          [X] Season is a null or blank string.
     *          [ ] Season isn't one of: [Winter, Spring, Summer, Fall].
     *          [ ] Year is a null or blank string.
     *          [ ] Year is not exactly four digits.
     *          [ ] Days is a null or blank string
     *          [ ] Days is a string that does not have exactly five "T" or "F" characters. For instance,
     *          if the semester is passed something like "TTT" or "FFFFFFFF" or "HELLO", then check that it throws
     *          an IllegalArgumentException.
     */
    @Test
    void testSemesterIllegalArgumentConstructor() {
        // EXAMPLE TEST: test that the semester throws an IllegalArgumentException when you pass a negative long
        // other than -1 or 0 into the argument "id", and that the message is "ErrorMessageConstants.ID_IS_INVALID".
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Semester(-2, null, "2020", "TTTTT"));
        assertEquals(ErrorMessageConstants.ID_IS_INVALID, exception.getMessage());

        // EXAMPLE TEST: test that the semester throws an IllegalArgumentException when you pass "null"
        // for the argument "season", and that the message is "ErrorMessageConstants.SEASON_IS_NULL_OR_BLANK"
        exception = assertThrows(IllegalArgumentException.class, () -> new Semester(-1, null, "2020", "TTTTT"));
        assertEquals(ErrorMessageConstants.SEASON_IS_NULL_OR_BLANK, exception.getMessage());

        // EXAMPLE TEST: test that the semester throws an IllegalArgumentException when you pass an empty string
        // for the argument "season", and that the message is "ErrorMessageConstants.SEASON_IS_INVALID"
        exception = assertThrows(IllegalArgumentException.class, () -> new Semester(-1, "", "2020", "TTTTT"));
        assertEquals(ErrorMessageConstants.SEASON_IS_NULL_OR_BLANK, exception.getMessage());

        // EXAMPLE TEST: test that the semester throws an IllegalArgumentException when you pass a string of only spaces
        // for the argument "season", and that the message is "ErrorMessageConstants.SEASON_IS_NULL_OR_BLANK"
        exception = assertThrows(IllegalArgumentException.class, () -> new Semester(-1, "      ", "2020", "TTTTT"));
        assertEquals(ErrorMessageConstants.SEASON_IS_NULL_OR_BLANK, exception.getMessage());

        // CODE BELOW:
    }


    /**
     * Check that the course throws an IllegalArgumentException when it is passed an invalid value in the constructor.
     * The QA engineer responsible for this test case must check that the exception is thrown in the following circumstances:
     *          [X] id (long) is either strictly less than -1 or equal to 0. Any other number is valid.
     *          [ ] Course code is a null or blank string.
     *          [ ] Course name is a null or blank string.
     *          [ ] Course section is a null or blank string.
     */
    @Test
    void testCourseIllegalArgumentConstructor() {
        // EXAMPLE TEST: test that the semester throws an IllegalArgumentException when you pass a negative long
        // other than -1 or 0 into the argument "id", and that the message is "ErrorMessageConstants.ID_IS_INVALID".
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Course(-2, "CS151", "Object-Oriented Design", "04"));
        assertEquals(ErrorMessageConstants.ID_IS_INVALID, exception.getMessage());

        // CODE BELOW:
    }

    /**
     * Check that the slot throws an IllegalArgumentException when it is passed an invalid value in the constructor.
     * The QA engineer responsible for this test case must check that the exception is thrown in the following circumstances:
     *          [X] id (long) is either strictly less than -1 or greater than or equal to 1. Any other number is valid.
     *          [ ] Slot starting time is a null or blank string.
     *          [ ] Slot ending time is a null or blank string.
     */
    @Test
    void testSlotIllegalArgumentConstructor() {
        // EXAMPLE TEST: test that the semester throws an IllegalArgumentException when you pass a negative long
        // other than -1 or 0 into the argument "id", and that the message is "ErrorMessageConstants.ID_IS_INVALID".
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Slot(-2, "11:45 AM", "12:00 PM"));
        assertEquals(ErrorMessageConstants.ID_IS_INVALID, exception.getMessage());

        // CODE BELOW:
    }

    /**
     * Check that the appointment throws an IllegalArgumentException when it is passed an invalid value in the constructor.
     * The QA engineer responsible for this test case must check that the exception is thrown in the following circumstances:
     *          [X] id (long) is either strictly less than -1 or greater than or equal to 1. Any other number is valid.
     *          [ ] Appointment's course is null.
     *          [ ] Appointment's slot is null.
     *          [ ] Student name is a null or blank string.
     *          [ ] Reason is a null string (it CAN be blank).
     *          [ ] Comment is a null string (it CAN be blank).
     */
    @Test
    void testAppointmentIllegalArgumentConstructor() {
        // null and non-null courses for the benefit of QA engineer
        Course notNullCourse = new Course(1, "CS151", "Object-Oriented Design", "04");
        Course nullCourse = null;

        Slot notNullSlot = new Slot(1, "11:45 AM", "12:45 PM");
        Slot nullSlot = null;


        // EXAMPLE TEST: test that the semester throws an IllegalArgumentException when you pass a negative long
        // other than -1 or 0 into the argument "id", and that the message is "ErrorMessageConstants.ID_IS_INVALID".
        Exception exception = assertThrows(IllegalArgumentException.class, () -> new Appointment(-2, notNullCourse, notNullSlot, "John Smith", "4/25/2025", "Reason", "Comment"));
        assertEquals(ErrorMessageConstants.ID_IS_INVALID, exception.getMessage());

        // CODE BELOW:
    }
}
