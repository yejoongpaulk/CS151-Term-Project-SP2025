package s25.cs151.application.views.components.tableview;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import s25.cs151.application.models.Appointment;
import s25.cs151.application.models.Slot;

import java.util.List;

/**
 * A concrete table view component for displaying Appointment objects.
 */
public class AppointmentTableView extends AbstractDataTableView<Appointment> {

    // Ensures at least Date, Slot, Student, & Course columns are visible at minimum width + Scrollbar.
    private static final int APPOINTMENT_TABLE_VIEW_MIN_WIDTH = 405 + 8;

    /**
     * Constructs an AppointmentTableView with the provided observable list of appointments.
     *
     * @param appointmentList - The observable list of appointments to be displayed.
     */
    public AppointmentTableView(ObservableList<Appointment> appointmentList) {
        super(appointmentList);
    }

    /**
     * Creates and configures the TableView columns for displaying appointment dates, time slot, student name,
     * course code, appointment reason, and appointment comments.
     */
    @Override
    protected void createTable() {

        final StringProperty dateTitle = new SimpleStringProperty("Date");

        TableColumn<Appointment, String> dateColumn = new TableColumn<>();
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        dateColumn.setMinWidth(90);
        dateColumn.setPrefWidth(90);
        dateColumn.setMaxWidth(185);
        dateColumn.setSortable(false);

        dateColumn.textProperty().bind(dateTitle);

        dateColumn.widthProperty().addListener((_, _, newWidth) -> {
            int threshold = 140;
            if (newWidth.doubleValue() < threshold) {
                dateTitle.set("Date");
            } else {
                dateTitle.set("Appointment Date");
            }
        });


        TableColumn<Appointment, Slot> slotColumn = new TableColumn<>("Time Slot");
        slotColumn.setCellValueFactory(new PropertyValueFactory<>("slotDisplay"));
        slotColumn.setMinWidth(140);
        slotColumn.setMaxWidth(185);
        slotColumn.setSortable(false);


        final StringProperty studentTitle = new SimpleStringProperty("Student");

        TableColumn<Appointment, String> studentColumn = new TableColumn<>();
        studentColumn.setCellValueFactory(new PropertyValueFactory<>("student"));
        studentColumn.setMinWidth(75);
        studentColumn.setPrefWidth(75);
        studentColumn.setMaxWidth(350);
        studentColumn.setSortable(false);

        studentColumn.textProperty().bind(studentTitle);

        studentColumn.widthProperty().addListener((_, _, newWidth) -> {
            int threshold = 125;
            if (newWidth.doubleValue() < threshold) {
                studentTitle.set("Student");
            } else {
                studentTitle.set("Student's Name");
            }
        });

        TableColumn<Appointment, String> courseColumn = new TableColumn<>("Course");
        courseColumn.setCellValueFactory(new PropertyValueFactory<>("courseDisplay"));
        courseColumn.setMinWidth(95);
        courseColumn.setMaxWidth(185);
        courseColumn.setSortable(false);

        TableColumn<Appointment, String> reasonColumn = new TableColumn<>("Reason");
        reasonColumn.setCellValueFactory(new PropertyValueFactory<>("reason"));
        reasonColumn.setPrefWidth(150);
        reasonColumn.setMinWidth(95);
        reasonColumn.setSortable(false);

        TableColumn<Appointment, String> commentColumn = new TableColumn<>("Comment");
        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        commentColumn.setPrefWidth(150);
        commentColumn.setMinWidth(95);
        commentColumn.setSortable(false);

        tableView.getColumns().addAll(List.of(
                dateColumn,
                slotColumn,
                studentColumn,
                courseColumn,
                reasonColumn,
                commentColumn
        ));

        tableView.setMinWidth(APPOINTMENT_TABLE_VIEW_MIN_WIDTH);

        tableView.setPlaceholder(new Label(
                "No appointment information to display.\n" +
                        "Add a new appointment or clear your search."
        ));
    }
}
