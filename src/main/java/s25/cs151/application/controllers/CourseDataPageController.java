package s25.cs151.application.controllers;

import s25.cs151.application.data.handlers.DataStorageHandler;
import s25.cs151.application.data.handlers.SQLiteStorageHandler;
import s25.cs151.application.models.Course;

import java.util.Comparator;

/**
 * A concrete controller for managing logic related to Course data within the CourseDataPage.
 */
public class CourseDataPageController extends AbstractDataPageController<Course> {

    private final DataStorageHandler storageHandler;

    public CourseDataPageController() {
        this.storageHandler = SQLiteStorageHandler.getStorageHandlerInstance();
        loadPersistedItems();
        displayedItems.setComparator(getComparator());
    }

    /**
     * Loads persisted course data from the storage handler and populates the source item list.
     */
    @Override
    protected void loadPersistedItems() {
        sourceItems.setAll(storageHandler.getCourseList());
    }

    /**
     * Checks if Course item already exists.
     *
     * @param course - The course to check for
     * @return {@code true} if duplicate exists, {@code false} otherwise
     */
    @Override
    public boolean isDuplicate(Course course) {
        return storageHandler.checkForCourse(course);
    }

    /**
     * Returns the comparator used to sort courses by course code.
     *
     * @return Course comparator
     */
    @Override
    protected Comparator<Course> getComparator() {
        return Course.COURSE_CODE_COMPARATOR;
    }

    /**
     * Persists the course data using the storage handler.
     *
     * @param course - The Course object to persist
     */
    @Override
    protected void persistItem(Course course) {
        storageHandler.saveCourse(course);
    }
}