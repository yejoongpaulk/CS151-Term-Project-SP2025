package s25.cs151.application.views.components.form;

import com.dlsc.formsfx.model.structure.*;
import javafx.beans.property.*;
import s25.cs151.application.data.handlers.SQLiteStorageHandler;
import s25.cs151.application.models.Appointment;
import s25.cs151.application.models.Course;
import s25.cs151.application.models.Slot;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * A concrete form component user for Appointment definition and construction.
 * This class serves as a reusable appointment form with form creation, data extraction, and form reset logic.
 */
public class AppointmentForm extends AbstractDataDefinitionForm<Appointment> {
    // Form fields
    private StringField studentNameField;
    private DateField dateField;
    private SingleSelectionField<Slot> timeSlotField;
    private SingleSelectionField<String> courseField;
    private StringField reasonField;
    private StringField commentField;

    // Lists for single selection fields.
    private List<Slot> slotList;
    private List<Course> courseList;
    private List<String> courseCodes;

    private final StringProperty studentNameProperty = new SimpleStringProperty();
    private final StringProperty reasonProperty = new SimpleStringProperty();
    private final StringProperty commentProperty = new SimpleStringProperty();
    private final ObjectProperty<LocalDate> dateProperty = new SimpleObjectProperty<>(LocalDate.now());

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    private Appointment editingAppointment = null;
    private final BooleanProperty editing = new SimpleBooleanProperty(false);

    /**
     * Loads the latest persisted Slot and Course Data into corresponding lists.
     */
    private void loadLists() {
        slotList = SQLiteStorageHandler.getStorageHandlerInstance().getSlotList()
                .stream()
                .sorted(Slot.START_TIME_COMPARATOR)
                .toList();

        courseList = SQLiteStorageHandler.getStorageHandlerInstance().getCourseList()
                .stream()
                .sorted(Course.COURSE_CODE_COMPARATOR)
                .toList();

        courseCodes = courseList.stream()
                .map(Course::getCourseId)
                .toList();
    }

    /**
     * Refreshes lists and selects first item.
     * This method should be called whenever form is displayed.
     */
    public void refreshForm() {
        refreshLists();
        timeSlotField.select(0);
        courseField.select(0);
    }

    /**
     * Refreshes lists to match persisted data.
     */
    private void refreshLists() {
        loadLists();
        timeSlotField.itemsProperty().clear();
        timeSlotField.itemsProperty().addAll(slotList);
        courseField.itemsProperty().clear();
        courseField.itemsProperty().addAll(courseCodes);
    }

    /**
     * Populates form based on item provided to edit.
     *
     * @param editingAppointment - The appointment to edit.
     */
    public void setEditingAppointment(Appointment editingAppointment) {
        this.editingAppointment = editingAppointment;
        editing.set(true);
        refreshLists();

        studentNameProperty.set(editingAppointment.getStudent());
        dateProperty.setValue(LocalDate.parse(editingAppointment.getDate(), DATE_FORMATTER));

        for (int i = 0; i < slotList.size(); i++) {
            if (slotList.get(i).getId() == editingAppointment.getSlot().getId()) {
                timeSlotField.select(i);
                break;
            }
        }

        for (int i = 0; i < courseList.size(); i++) {
            if (courseList.get(i).getId() == editingAppointment.getCourse().getId()) {
                courseField.select(i);
                break;
            }
        }

        reasonProperty.set(editingAppointment.getReason());
        commentProperty.set(editingAppointment.getComment());
    }

    protected double getMinWidth() {
        return 760;
    }

    /**
     * Returns the appointment being edited.
     *
     * @return the Appointment object being edited.
     */
    public Appointment getEditingAppointment() {
        return editingAppointment;
    }

    public boolean isEditing() {
        return editingProperty().get();
    }

    /**
     * Returns a simple boolean property indicating whether the form is editing or not.
     *
     * @return a BooleanProperty indicating whether form is currently editing an item.
     */
    public BooleanProperty editingProperty() {
        return editing;
    }

    /**
     * Creates and returns the form layout for defining an Appointment object.
     *
     * @return a Form for defining an Appointment object.
     */
    @Override
    protected Form createForm() {
        loadLists();

        studentNameProperty.set("");
        reasonProperty.set("");
        commentProperty.set("");

        studentNameField = Field.ofStringType(studentNameProperty)
                .label("Student's Name")
                .styleClass("form-text-field")
                .placeholder("e.g. John Doe")
                .required("Student's full name is required.");

        dateField = Field.ofDate(dateProperty)
                .label("Schedule Date")
                .styleClass("form-date-picker")
                .placeholder("e.g. 02/18/2025")
                .required("Schedule date is required.");

        timeSlotField = Field.ofSingleSelectionType(slotList)
                .label("Time Slot")
                .styleClass("form-combo-box")
                .required("Please select a time slot.")
                .select(0);

        courseField = Field.ofSingleSelectionType(courseCodes)
                .label("Course")
                .styleClass("form-combo-box")
                .required("Please select a course.")
                .select(0);

        reasonField = Field.ofStringType(reasonProperty)
                .label("Reason")
                .styleClass("form-multiline-field")
                .multiline(true);

        commentField = Field.ofStringType(commentProperty)
                .label("Comment")
                .styleClass("form-multiline-field")
                .multiline(true);

        return Form.of(
                Group.of(
                        studentNameField,
                        dateField,
                        timeSlotField,
                        courseField,
                        reasonField,
                        commentField
                )
        );
    }

    /**
     * Constructs and returns an Appointment object from extracted form input.
     *
     * @return an Appointment object constructed from form input.
     */
    @Override
    public Appointment extractItem() {
        return new Appointment(
                editingAppointment == null ? -1 : editingAppointment.getId(),
                extractCourse(),
                extractSlot(),
                extractStudentName(),
                extractDate(),
                extractReason(),
                extractComment()
        );
    }

    /**
     * Returns a student name String extracted from the form.
     *
     * @return student name String.
     */
    private String extractStudentName() {
        return studentNameField.getValue().trim();
    }

    /**
     * Returns a schedule date String extracted from the form.
     *
     * @return schedule date String.
     */
    private String extractDate() {
        return dateField.getValue().format(DATE_FORMATTER);
    }

    /**
     * Returns the selected Slot object extracted from the form.
     *
     * @return selected Slot from form.
     */
    private Slot extractSlot() {
        return timeSlotField.getSelection();
    }

    /**
     * Returns the selected Course object based on the course code extracted from the form.
     *
     * @return selected Course, or {@code null} if no matching course (this should never happen).
     */
    private Course extractCourse() {
        String selectedCourseId = courseField.getSelection();

        if (selectedCourseId == null) return  null;

        return courseList
                .stream()
                .filter(course -> course.getCourseId().equals(selectedCourseId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Returns the optional reason extracted from the form.
     *
     * @return reason String, or an empty String if left empty.
     */
    private String extractReason() {
        return reasonField.getValue().trim();
    }

    /**
     * Returns the optional comment extracted from the form.
     *
     * @return comment String, or an empty String if left empty.
     */
    private String extractComment() {
        return commentField.getValue().trim();
    }

    /**
     * Resets the form to its default state.
     * Sets date field to local date.
     * Sets slot field to first option.
     * Sets course field to first option.
     */
    @Override
    public void resetForm() {
        editingAppointment = null;
        editing.set(false);
        refreshForm();

        form.reset();
        studentNameProperty.set("");
        dateProperty.setValue(LocalDate.now());
        timeSlotField.select(0);
        courseField.select(0);
        reasonProperty.set("");
        commentProperty.set("");
    }
}
