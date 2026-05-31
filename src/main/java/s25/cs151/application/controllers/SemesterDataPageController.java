package s25.cs151.application.controllers;

import s25.cs151.application.data.handlers.DataStorageHandler;
import s25.cs151.application.data.handlers.SQLiteStorageHandler;
import s25.cs151.application.models.Semester;

import java.util.Arrays;
import java.util.Comparator;

/**
 * A concrete controller for managing logic related to Semester data within the SemesterDataPage.
 */
public class SemesterDataPageController extends AbstractDataPageController<Semester> {

    private final DataStorageHandler storageHandler;

    public SemesterDataPageController() {
        this.storageHandler = SQLiteStorageHandler.getStorageHandlerInstance();
        loadPersistedItems();
        displayedItems.setComparator(getComparator());
    }

    /**
     * Loads persisted semester data from the storage handler and populates the source item list.
     */
    @Override
    protected void loadPersistedItems() {
        sourceItems.setAll(storageHandler.getSemesterList());
    }

    /**
     * Checks if Semester item already exists.
     *
     * @param semester - The semester to check for
     * @return {@code true} if duplicate exists, {@code false} otherwise
     */
    @Override
    public boolean isDuplicate(Semester semester) {
        return storageHandler.checkForSemester(semester);
    }

    /**
     * Returns the comparator used to sort semesters by year and season.
     *
     * @return Semester comparator
     */
    @Override
    protected Comparator<Semester> getComparator() {
        return Comparator
                .comparing(Semester::getYear, (y1, y2) -> Integer.parseInt(y2) - Integer.parseInt(y1))
                .thenComparing(Semester::getSeason,
                        Comparator.comparingInt(Arrays.asList("Winter", "Fall", "Summer", "Spring")::indexOf));
    }

    /**
     * Persists the semester data using the storage handler.
     *
     * @param semester - The Course object to persist
     */
    @Override
    protected void persistItem(Semester semester) {
        storageHandler.saveSemester(semester);
    }
}
