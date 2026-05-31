package s25.cs151.application.controllers;

import s25.cs151.application.data.handlers.DataStorageHandler;
import s25.cs151.application.data.handlers.SQLiteStorageHandler;
import s25.cs151.application.models.Slot;

import java.util.Comparator;

/**
 * A concrete controller for managing logic related to Slot data within the SlotDataPage.
 */
public class SlotDataPageController extends AbstractDataPageController<Slot> {

    private final DataStorageHandler storageHandler;

    public SlotDataPageController() {
        this.storageHandler = SQLiteStorageHandler.getStorageHandlerInstance();
        loadPersistedItems();
        displayedItems.setComparator(getComparator());
    }

    /**
     * Loads persisted slot data from the storage handler and populates the source item list.
     */
    @Override
    protected void loadPersistedItems() {
        sourceItems.setAll(storageHandler.getSlotList());
    }

    /**
     * Always returns {@code false} as duplicates are allowed.
     *
     * @param slot - Slot item being checked for duplicates.
     * @return always {@code false} as duplicates are allowed.
     */
    @Override
    public boolean isDuplicate(Slot slot) {
        return false;
    }

    /**
     * Returns the comparator used to sort slots by start time.
     *
     * @return Slot comparator
     */
    @Override
    protected Comparator<Slot> getComparator() {
        return Slot.START_TIME_COMPARATOR;
    }

    /**
     * Persists the slot data using the storage handler.
     *
     * @param slot - The Slot object to persist
     */
    @Override
    protected void persistItem(Slot slot) {
        storageHandler.saveSlot(slot);
    }
}
