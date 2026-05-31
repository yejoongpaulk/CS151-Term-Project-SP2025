package s25.cs151.application.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import s25.cs151.application.views.constants.SaveResult;

import java.util.Comparator;
import java.util.function.Predicate;

/**
 * Abstract controller for managing the logic of data definition pages which includes logic for saving, loading,
 * and resetting data through associated form and table view components.
 *
 * @param <DataType> The data model this controller handles (e.g. Course)
 */
public abstract class AbstractDataPageController<DataType> {

    // Source list of items.
    protected final ObservableList<DataType> sourceItems;

    // Filtered view of source items for display.
    protected FilteredList<DataType> filteredList;

    // Sorted view of filtered items for display.
    protected final SortedList<DataType> displayedItems;

    /**
     * Constructs a controller with initialized source, filtered, and sorted item lists.
     */
    public AbstractDataPageController() {
        this.sourceItems = FXCollections.observableArrayList();
        this.filteredList = new FilteredList<>(sourceItems);
        this.displayedItems = new SortedList<>(filteredList);
    }

    /**
     * Loads data from persistent storage into the sourceItems list.
     * Must be implemented by concrete subclasses.
     */
    protected abstract void loadPersistedItems();

    /**
     * Checks if {@code DataType} item already exists.
     * Must be implemented by concrete subclasses.
     *
     * @param item - The item to check for
     * @return {@code true} if duplicate exists, {@code false} otherwise
     */
    public abstract boolean isDuplicate(DataType item);

    /**
     * Returns a comparator to sort displayed items.
     * Must be implemented by concrete subclasses.
     *
     * @return Comparator for sorting {@code DataType} objects.
     */
    protected abstract Comparator<DataType> getComparator();

    /**
     * Persists the given {@code DataType} object using the storage handler.
     * @param item - The item to persist.
     */
    protected abstract void persistItem(DataType item);

    /**
     * Handles saving the form input to persistent storage if valid and not duplicate.
     * Concrete subclasses may override for {@code DataType} specific logic.
     *
     * @return Save result status (SAVED or DUPLICATE)
     */
    public SaveResult handleSave(DataType item) {
        if (isDuplicate(item)) {
            return SaveResult.DUPLICATE;
        } else {
            persistItem(item);
            sourceItems.add(item);
            return SaveResult.SAVED;
        }
    }

    /**
     * Delete an item from TableView and from database. Override in subclasses as necessary.
     *
     * @param itemToDelete - item to delete
     */
    public void handleDelete(DataType itemToDelete) {
        sourceItems.remove(itemToDelete);
    }

    /**
     * Returns the list of items sorted by the comparator for display in table view.
     *
     * @return Sorted observable list of {@code DataType} objects.
     */
    public final ObservableList<DataType> getDisplayedItems() {
        return displayedItems;
    }

    /**
     * Reloads the source and displayed item list from persistent storage.
     */
    public final void refreshItems() {
        loadPersistedItems();
    }

    /**
     * Set filter given a Predicate.
     * @param predicate lambda expression to see if Student name contains a certain string.
     */
    public void setFilter(Predicate<DataType> predicate) {
        this.filteredList.setPredicate(predicate);
    }

    /**
     * Set items comparator given a Comparator.
     * @param comparator Comparator defining how items are sorted.
     */
    public void setComparator(Comparator<DataType> comparator) {
        this.displayedItems.setComparator(comparator);
    }

    /**
     * Set items comparator to the default comparator
     */
    protected void setDefaultComparator() {
        displayedItems.setComparator(getComparator());
    }
}

