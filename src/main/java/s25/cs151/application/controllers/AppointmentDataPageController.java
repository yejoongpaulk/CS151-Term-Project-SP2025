package s25.cs151.application.controllers;

import s25.cs151.application.data.handlers.DataStorageHandler;
import s25.cs151.application.data.handlers.SQLiteStorageHandler;
import s25.cs151.application.models.Appointment;
import s25.cs151.application.models.Slot;
import s25.cs151.application.views.constants.SaveResult;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

/**
 * A concrete controller for managing logic related to Appointment data within the AppointmentDataPage.
 */
public class AppointmentDataPageController extends AbstractDataPageController<Appointment> {

    private DataStorageHandler storageHandler;

    public AppointmentDataPageController() {
        this.storageHandler = SQLiteStorageHandler.getStorageHandlerInstance();
        loadPersistedItems();
        displayedItems.setComparator(getComparator());
    }

    /**
     * Delete an item from TableView and from database. Override in subclasses as necessary.
     *
     * @param itemToDelete - item to delete
     */
    @Override
    public void handleDelete(Appointment itemToDelete) {
        deleteItem(itemToDelete);
        sourceItems.remove(itemToDelete);
    }


    /**
     * Loads persisted appointment data from the storage handler and populates the source item list.
     */
    @Override
    protected void loadPersistedItems() {
        List<Appointment> appointments = storageHandler.getAppointmentList();
        sourceItems.setAll(appointments);
    }

    /**
     * Always returns {@code false} as duplicates are allowed.
     *
     * @param appointment - Appointment item being checked for duplicates.
     * @return always {@code false} as duplicates are allowed.
     */
    @Override
    public boolean isDuplicate(Appointment appointment) {
        return false;
    }

    /**
     * Returns the comparator used to sort appointments by date, then time slot.
     *
     * @return Appointment comparator
     */
    @Override
    protected Comparator<Appointment> getComparator() {
        DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        return Comparator
                .comparing((Appointment a) -> LocalDate.parse(a.getDate(), DATE_FORMATTER))
                .thenComparing(Appointment::getSlot, Slot.START_TIME_COMPARATOR);
    }

    /**
     * Persists the appointment data using the storage handler.
     *
     * @param appointment - The Appointment object to persist
     */
    @Override
    protected void persistItem(Appointment appointment) {
        storageHandler.saveAppointment(appointment);
    }

    /**
     * Deletes appointment from storage.
     *
     * @param appointment
     */
    protected void deleteItem(Appointment appointment) {
        storageHandler.deleteAppointment(appointment);
    }

    public void handleSearch(String searchText) {
        String query = searchText.toLowerCase().strip();

        if (query.isEmpty()) {
            setFilter(_ -> true);
            setDefaultComparator();
        } else {
            setFilter(appointment -> appointment.getStudent().toLowerCase().contains(query));
            setComparator(getComparator().reversed());
        }
    }

    /**
     * Handles updating the form input to persistent storage if valid.
     *
     * @return Update result status
     */
    public SaveResult handleUpdate(Appointment updatedItem, Appointment oldItem) {
        storageHandler.updateAppointment(updatedItem);

        for (int i = 0; i < sourceItems.size(); i++) {
            Appointment existing = sourceItems.get(i);
            if (existing.getId() == oldItem.getId()) {
                sourceItems.set(i, updatedItem);
                break;
            }
        }

        return SaveResult.EDITED;
    }

}