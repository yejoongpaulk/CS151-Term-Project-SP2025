package s25.cs151.application.data.handlers;

import s25.cs151.application.models.Appointment;
import s25.cs151.application.models.Course;
import s25.cs151.application.models.Semester;
import s25.cs151.application.models.Slot;

import java.util.List;

public interface DataStorageHandler {
    /**
     * Given a String directory path for a folder, initialize all data
     * in that folder.
     *
     * @param folderPathStr - folder location where all data is stored
     */
    void initializeStorage(String folderPathStr);

    /**
     * Given a Semester object, check if the semester already exists
     * in the data.
     *
     * @param semester - Semester object that function checks if exists in data
     * @return true if semester exists, false otherwise
     */
    boolean checkForSemester(Semester semester);

    /**
     * Given a Semester object, persist semester object in data storage.
     *
     * @param semester - Semester object to save to data
     */
    void saveSemester(Semester semester);


    /**
     * Given a list of Semester strings, create semester objects from persistent storage.
     * @param semesterIds - list of semesterIds
     * @return List of Semester objects
     */
    List<Semester> getSemesters(List<String> semesterIds);


    /**
     * Retrieve all persisted Semester data as a Collection (preferably a List or a better approach)
     * @return Collection<Semester> - Collection object containing Semester data
     */
    List<Semester> getSemesterList();


    /**
     * Given a Course object, check if given course already exists in persistent data.
     * @param course - Course object
     * @return true if exists already, false otherwise
     */
    boolean checkForCourse(Course course);


    /**
     * Given a Course object, persist in data storage.
     * @param course - Course object to save
     */
    void saveCourse(Course course);


    /**
     * Given a list of Course codes, create Course objects from persistent storage.
     * @param courseCodes - list of courseCodes
     * @return List of Course objects
     */
    List<Course> getCourses(List<String> courseCodes);


    /**
     * Get list of all Courses saved in persistent storage.
     * @return List of Course objects
     */
    List<Course> getCourseList();


    /**
     * Given a Slot object, check if it already exists in persistent data.
     * @param slot - Slot object to check
     * @return true if exists already, false otherwise
     */
    boolean checkForSlot(Slot slot);


    /**
     * Given a Slot object, save to persistent storage.
     * @param slot - Slot object to save
     */
    void saveSlot(Slot slot);

    /**
     * Given a list of semesterID strings, get all slots with semesterID, making sure to create
     * the appropriate parent "Semester" objects as well (getSemesters() may be useful for this).
     * @param semesterIds - list of semesterIds
     * @return List of Semester objects
     */
    List<Semester> getSlots(List<String> semesterIds);


    /**
     * Get list of Slots saved in persistent storage.
     * @return List of Slot objects
     */
    List<Slot> getSlotList();

    /**
     * NEW (for version 0.6 from this point on): Check if a given Appointment already exists in persistent storage.
     * @param appointment - Appointment object to check.
     * @return true if appointment already exists, false otherwise.
     */
    boolean checkForAppointment(Appointment appointment);

    /**
     * NEW (for version 0.6): Persist a given Appointment object in storage.
     * @param appointment - Appointment object to save.
     */
    void saveAppointment(Appointment appointment);


    /**
     * Delete a given Appointment in storage.
     * @param appointment
     */
    void deleteAppointment(Appointment appointment);


    /**
     * NEW (for version 0.6): Retrieve all persisted Appointment data as a List.
     * @return List of Appointment objects.
     */
    List<Appointment> getAppointmentList();


    /**
     * Given an appointment, update its entry in the database.
     * @param appointment - appointment to be updated
     */
    void updateAppointment(Appointment appointment);
}
