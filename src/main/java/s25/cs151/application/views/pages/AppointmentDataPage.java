package s25.cs151.application.views.pages;

import javafx.beans.binding.Bindings;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import s25.cs151.application.controllers.AppointmentDataPageController;
import s25.cs151.application.models.Appointment;
import s25.cs151.application.views.components.form.AppointmentForm;
import s25.cs151.application.views.components.tableview.AppointmentTableView;
import s25.cs151.application.views.constants.FormMode;
import s25.cs151.application.views.constants.PageIdConstants;
import s25.cs151.application.views.constants.SaveResult;

/**
 * A concrete page for managing Appointment data.
 */
public class AppointmentDataPage extends AbstractDataPage<Appointment, AppointmentForm, AppointmentTableView> {

    private TextField searchField;
    private Button deleteButton;
    private Button editButton;

    /**
     * Constructs the AppointmentDataPage with its controller.
     *
     */
    public AppointmentDataPage() {
        super(new AppointmentDataPageController());
    }

    protected Node createTopBar() {
        // delete button
        deleteButton = new Button("Delete");
        deleteButton.getStyleClass().addAll("appointment-delete-button");

        deleteButton.disableProperty().bind(
                Bindings.or(
                        Bindings.isNull(tableView.selectedItemProperty()),
                        form.editingProperty()
                )
        );
        deleteButton.setOnAction(_ -> {
            Appointment selected = tableView.getSelectedItem();
            if (selected != null && confirmDelete(selected)) {
                controller.handleDelete(selected);
            }
        });

        editButton = new Button("Edit");
        editButton.getStyleClass().addAll("appointment-edit-button");

        editButton.disableProperty().bind(Bindings.isNull(tableView.selectedItemProperty()));
        editButton.setOnAction(_ -> {
            Appointment selected = tableView.getSelectedItem();
            if (selected != null) {
                form.setEditingAppointment(selected);
                showForm();
            }
        });

        // add textfield for students
        searchField = new TextField();
        searchField.setPromptText("Search by student name...");
        searchField.textProperty().addListener((_, _, searchText) -> {
            ((AppointmentDataPageController) controller).handleSearch(searchText);
        });
        searchField.getStyleClass().add("appointment-search-field");

        HBox topBar = new HBox(10, searchField, deleteButton, editButton);
        topBar.setStyle("-fx-alignment: center-left");

        return topBar;
    }

    /**
     * Returns the title text for this page.
     *
     * @return title text, "SCHEDULE MANAGEMENT"
     */
    @Override
    public String getPageTitle() {
        return "Office Hours Schedule";
    }

    /**
     * Creates and returns a new AppointmentForm used by this page.
     *
     * @return a new AppointmentForm object.
     */
    @Override
    protected AppointmentForm createForm() {
        return new AppointmentForm();
    }

    /**
     * Creates and returns a new AppointmentTableView used by this page.
     *
     * @return a new AppointmentTableView object.
     */
    @Override
    protected AppointmentTableView createTableView() {
        return new AppointmentTableView(controller.getDisplayedItems());
    }

    private boolean confirmDelete(Appointment appointment) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Deletion");
        alert.setHeaderText("Delete Appointment");
        alert.setContentText("Are you sure you want to permanently delete the appointment for " +
                appointment.getStudent() + "?");

        return alert.showAndWait()
                .filter(button -> button == ButtonType.OK)
                .isPresent();
    }

    @Override
    protected SaveResult handleFormSubmission(Appointment item) {
        Appointment editing = form.getEditingAppointment();
        AppointmentDataPageController tempControl = (AppointmentDataPageController) controller;
        return form.isEditing()
                ? tempControl.handleUpdate(item, editing)
                : tempControl.handleSave(item);
    }

    @Override
    protected void showForm() {
        if (!form.isEditing()) {
            form.refreshForm();
        }
        super.showForm();
    }

    @Override
    protected String getResultMessage(Appointment item, SaveResult result) {
        return switch (result) {
            case SAVED -> String.format(
                    "Success: Saved an appointment for %s on %s from %s to %s.",
                    item.getStudent(),
                    item.getDate(),
                    item.getSlot().getStartTime(),
                    item.getSlot().getEndTime()
            );
            case EDITED -> "Success: Appointment has been modified.";
            case DUPLICATE -> throw new IllegalStateException("DUPLICATE result should never occur for Appointment.");
        };
    }

    @Override
    protected String getContextMessage(FormMode mode) {
        if (mode == FormMode.EDIT) {
            Appointment item = form.getEditingAppointment();
            return String.format("Modifying appointment for %s on %s.", item.getStudent(), item.getDate());
        }
        return "Fill out the form to schedule a new appointment.";
    }

    @Override
    protected FormMode getFormMode() {
        return form.isEditing() ? FormMode.EDIT : FormMode.ADD;
    }

    @Override
    public Node getContent() {
        form.refreshForm();
        return super.getContent();
    }

    @Override
    public String getPageId() {
        return PageIdConstants.SCHEDULE_PAGE_ID;
    }
}
