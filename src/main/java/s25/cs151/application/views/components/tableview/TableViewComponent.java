package s25.cs151.application.views.components.tableview;

import s25.cs151.application.views.components.GUIComponent;

/**
 * A generic interface representing a table view component for displaying {@code DataType} objects.
 *
 * @param <DataType> the type of data object the table view displays (e.g. Course)
 */
public interface TableViewComponent<DataType> extends GUIComponent {

    /**
     * Refreshes the table view to reflect any changes in data.
     */
    void refresh();
}
