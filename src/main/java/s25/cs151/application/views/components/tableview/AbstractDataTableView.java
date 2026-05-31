package s25.cs151.application.views.components.tableview;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Abstract base class for all table view components displaying defined data (e.g. Course)
 * This class serves as a reusable TableView with initialization, layout, and refresh logic.
 * Subclasses should define the table's column structure and formatting by implementing {@link #createTable()}.
 *
 * @param <DataType> The type of data displayed in the TableView
 */
public abstract class AbstractDataTableView<DataType> implements TableViewComponent<DataType> {
    private final VBox container;
    protected final TableView<DataType> tableView;

    /**
     * Constructs the abstract table view with the specified observable data list.
     *
     * @param displayedList The list of data to be displayed by the TableView.
     */
    public AbstractDataTableView(ObservableList<DataType> displayedList) {
        this.container = new VBox();
        this.tableView = new TableView<>(displayedList);
    }

    /**
     * Creates and formats the TableView.
     * Subclasses must implement this method to define how the table should appear.
     */
    protected abstract void createTable();

    /**
     * Get selected item from TableView.
     * @return item
     */
    public DataType getSelectedItem() {
        return selectedItemProperty().get();
    }

    /**
     * Initializes the table view component.
     * Creates the table view layout and adds it to the container.
     */
    public void initialize() {
        createTable();
        tableView.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        tableView.getStyleClass().add("styled-table-view");

        tableView.setRowFactory(_ -> {
            TableRow<DataType> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (row.isEmpty()) {
                    tableView.getSelectionModel().clearSelection();
                }
            });
            return row;
        });

        container.getChildren().add(tableView);
    }

    /**
     * Refreshes the TableView to reflect changes in the displayedList.
     */
    @Override
    public void refresh() {
        tableView.refresh();
    }

    /**
     * Get layout object, which should include the widget itself.
     *
     * @return Pane containing the TableView.
     */
    @Override
    public Pane getLayout() {
        return container;
    }

    public ReadOnlyObjectProperty<DataType> selectedItemProperty() {
        return tableView.getSelectionModel().selectedItemProperty();
    }
}
