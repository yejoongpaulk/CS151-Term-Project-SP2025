package test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import s25.cs151.application.data.handlers.DataStorageHandler;
import s25.cs151.application.data.handlers.SQLiteStorageHandler;
import s25.cs151.application.models.Course;
import s25.cs151.application.models.Appointment; // new for version 0.7
import s25.cs151.application.models.Semester; // new for version 0.7
import s25.cs151.application.models.Slot; // new for version 0.7

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestDataStorageHandler {
    @BeforeEach
    void setUpStorage(@TempDir Path tempDir) throws IOException {
        // prepare storage handler for use
        DataStorageHandler storageHandler = SQLiteStorageHandler.getStorageHandlerInstance();
        storageHandler.initializeStorage(tempDir.toString());
    }

    @AfterEach
    void clearStorage() throws IOException {
        // close connection
        SQLiteStorageHandler.getStorageHandlerInstance().closeConnection();
    }

    @Test
    void testCourseStorage() throws IOException {
        DataStorageHandler storageHandler = SQLiteStorageHandler.getStorageHandlerInstance();

        // create two courses to save
        Course course1 = new Course(1, "CS151", "Object-Oriented Programming", "04");
        Course course2 = new Course(2, "CS154", "Formal Languages and Computability", "02");

        // save courses to storage handler
        storageHandler.saveCourse(course1);
        storageHandler.saveCourse(course2);

        // ensure courses are saved
        assertTrue(storageHandler.checkForCourse(course1));
        assertTrue(storageHandler.checkForCourse(course2));

        // retrieve two courses
        List<Course> coursesList = storageHandler.getCourseList();

        // prepare references for courses obtained from storage
        Course course1Retrieved = null, course2Retrieved = null;

        // go through every course and assign the proper one to the proper variable
        for (Course course : coursesList) {
            if (course.getId() == course1.getId()) {
                course1Retrieved = course;
            }

            if (course.getId() == course2.getId()) {
                course2Retrieved = course;
            }
        }

        // ensure courses are retrieved
        assertNotNull(course1Retrieved);
        assertNotNull(course2Retrieved);

        // ensure courses are correct
        assertEquals(course1Retrieved.getCode(), course1.getCode());
        assertEquals(course1Retrieved.getName(), course1.getName());
        assertEquals(course1Retrieved.getSection(), course1.getSection());
        assertEquals(course2Retrieved.getCode(), course2.getCode());
        assertEquals(course2Retrieved.getName(), course2.getName());
        assertEquals(course2Retrieved.getSection(), course2.getSection());
    }

    // new tests for version 0.7
    @Test
    void testSemesterStorage() throws IOException {
        DataStorageHandler storageHandler = SQLiteStorageHandler.getStorageHandlerInstance();

        // Create a semester and save it
        Semester semester = new Semester(1, "Fall", "2025", "TTTTT");
        storageHandler.saveSemester(semester);

        // Check that the semester exists
        assertTrue(storageHandler.checkForSemester(semester));

        // Retrieve all semesters and find the saved one
        List<Semester> semesterList = storageHandler.getSemesterList();
        Semester retrievedSemester = null;

        for (Semester s : semesterList) {
            if (s.getId() == semester.getId()) {
                retrievedSemester = s;
            }
        }

        // Verify the retrieved semester matches the saved one
        assertNotNull(retrievedSemester);
        assertEquals(retrievedSemester.getId(), semester.getId());
    }

     @Test
    void testSlotStorage() throws IOException {
        DataStorageHandler storageHandler = SQLiteStorageHandler.getStorageHandlerInstance();

        // Create a slot and save it
        Slot slot = new Slot(1, "10:00 AM", "11:00 AM");
        storageHandler.saveSlot(slot);

        // Check that the slot exists
        assertTrue(storageHandler.checkForSlot(slot));

        // Retrieve all slots and find the saved one
        List<Slot> slotList = storageHandler.getSlotList();
        Slot retrievedSlot = null;

        for (Slot s : slotList) {
            if (s.getId() == slot.getId()) {
                retrievedSlot = s;
            }
        }

        // Verify the retrieved slot matches the saved one
        assertNotNull(retrievedSlot);
        assertEquals(retrievedSlot.getId(), slot.getId());
        assertEquals(retrievedSlot.getStartTime(), slot.getStartTime());
        assertEquals(retrievedSlot.getEndTime(), slot.getEndTime());
    }

    @Test
    void testAppointmentStorage() throws IOException {
        DataStorageHandler storageHandler = SQLiteStorageHandler.getStorageHandlerInstance();

        // course
        Course course = new Course(1, "CS151", "Object-Oriented Programming", "04");
        storageHandler.saveCourse(course);

        // slot
        Slot slot = new Slot(1, "10:00 AM", "11:00 AM");
        storageHandler.saveSlot(slot);

        // Create an appointment and save it
        Appointment appointment = new Appointment(1, course, slot, "John Doe", "4/25/2025", "CS151 Term Project", "Will talk about the unit tests.");
        storageHandler.saveAppointment(appointment);

        // Check that the appointment exists
        assertTrue(storageHandler.checkForAppointment(appointment));

        // Retrieve all appointments and find the saved one
        List<Appointment> appointmentList = storageHandler.getAppointmentList();
        Appointment retrievedAppointment = null;

        for (Appointment a : appointmentList) {
            if (a.getId() == appointment.getId()) {
                retrievedAppointment = a;
            }
        }

        // Verify the retrieved appointment matches the saved one
        assertNotNull(retrievedAppointment);
        assertEquals(retrievedAppointment.getId(), appointment.getId());
        assertEquals(retrievedAppointment.getCourse().getCode(), appointment.getCourse().getCode());
        assertEquals(retrievedAppointment.getSlot().getStartTime(), appointment.getSlot().getStartTime());
        assertEquals(retrievedAppointment.getSlot().getEndTime(), appointment.getSlot().getEndTime());
        assertEquals(retrievedAppointment.getStudent(), appointment.getStudent());
        assertEquals(retrievedAppointment.getReason(), appointment.getReason());
        assertEquals(retrievedAppointment.getComment(), appointment.getComment());
    }
}
