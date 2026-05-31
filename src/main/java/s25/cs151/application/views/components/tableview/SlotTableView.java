package s25.cs151.application.views.components.tableview;

import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import s25.cs151.application.models.Slot;
import java.util.List;

/**
 * A concrete table view component for displaying Slot objects.
 */
public class SlotTableView extends AbstractDataTableView<Slot> {

    /**
     * Constructs a SlotTableView with the provided observable list of slots.
     *
     * @param slotList - The observable list of slots to be displayed.
     */
    public SlotTableView(ObservableList<Slot> slotList) {
        super(slotList);
    }

    /**
     * Creates and configures the TableView columns for displaying slot start and end time.
     */
    protected void createTable() {
        TableColumn<Slot, String> startColumn = new TableColumn<>("Start Time");
        startColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        startColumn.setMinWidth(90);
        startColumn.setSortable(false);

        TableColumn<Slot, String> endColumn = new TableColumn<>("End Time");
        endColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        endColumn.setMinWidth(90);
        endColumn.setSortable(false);

        tableView.getColumns().addAll(List.of(startColumn, endColumn));
        tableView.setMinWidth(180+14.2);

        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        tableView.setPlaceholder(new Label(
                "No time slots to display.\n" +
                        "Click \"Add\" to get started."
        ));
    }
}
