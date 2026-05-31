package s25.cs151.application.views.components.tableview;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import s25.cs151.application.models.Semester;

import java.util.List;

/**
 * A concrete table view component for displaying Semester objects.
 */
public class SemesterTableView extends AbstractDataTableView<Semester> {

    private FormatLevel currentFormat = FormatLevel.FULL;

    private enum FormatLevel {
        MEDIUM, FULL
    }

    /**
     * Constructs a SemesterTableView with the provided observable list of semesters.
     *
     * @param semesterList - The observable list of semesters to be displayed.
     */
    public SemesterTableView(ObservableList<Semester> semesterList) {
        super(semesterList);
    }

    /**
     * Creates and configures the TableView columns for displaying semester year, season, and days.
     */
    @Override
    protected void createTable() {
        TableColumn<Semester, String> yearColumn = new TableColumn<>("Year");
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        yearColumn.setMinWidth(60);
        yearColumn.setResizable(false);
        yearColumn.setSortable(false);

        TableColumn<Semester, String> seasonColumn = new TableColumn<>("Semester");
        seasonColumn.setCellValueFactory(new PropertyValueFactory<>("season"));
        seasonColumn.setMinWidth(100);
        seasonColumn.setResizable(false);
        seasonColumn.setSortable(false);

        TableColumn<Semester, String> daysColumn = new TableColumn<>("Selected Day(s)");
        daysColumn.setCellValueFactory(cellData -> {
            String boolDays = cellData.getValue().getDays();
            return new SimpleStringProperty(formatDays(boolDays, currentFormat));
        });
        daysColumn.setResizable(false);
        daysColumn.setSortable(false);
        daysColumn.setPrefWidth(290);


        tableView.getColumns().addAll(List.of(yearColumn, seasonColumn, daysColumn));
        tableView.setMinWidth(350+15);
        tableView.setPlaceholder(new Label(
                "No semester information to display.\n" +
                        "Click \"Add\" to get started."
        ));

        tableView.widthProperty().addListener((_, _, newValue) -> {
            double width = newValue.doubleValue();
            if (width < 490 && currentFormat != FormatLevel.MEDIUM) {
                currentFormat = FormatLevel.MEDIUM;
                daysColumn.setPrefWidth(170);
                tableView.refresh();
            } else if (width >= 490 && currentFormat != FormatLevel.FULL) {
                currentFormat = FormatLevel.FULL;
                daysColumn.setPrefWidth(290);
                tableView.refresh();
            }
        });
    }

    private String formatDays(String boolDays, FormatLevel level) {
        String[] mediumDays = {"Mon", "Tues", "Wed", "Thurs", "Fri"};
        String[] fullDays = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
        String[] daysToUse = (level == FormatLevel.FULL) ? fullDays : mediumDays;

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < boolDays.length() && i < daysToUse.length; i++) {
            if (boolDays.charAt(i) == 'T') {
                if (!result.isEmpty()) result.append(", ");
                result.append(daysToUse[i]);
            }
        }
        return result.toString();
    }
}
