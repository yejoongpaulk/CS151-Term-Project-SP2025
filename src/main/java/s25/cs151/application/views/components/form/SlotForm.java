package s25.cs151.application.views.components.form;

import com.dlsc.formsfx.model.structure.*;
import s25.cs151.application.models.Slot;
import java.util.ArrayList;
import java.util.List;

/**
 * A concrete form component user for Slot definition and construction.
 * This class serves as a reusable slot form with form creation and data extraction logic.
 */
public class SlotForm extends AbstractDataDefinitionForm<Slot> {
    // Form fields
    private SingleSelectionField<String> startTimeField;
    private SingleSelectionField<String> endTimeField;

    // Lists for single selection fields.
    private final List<String> timeOptions = generateTimeOptions();

    /**
     * Generates a list of time options in 15-minute intervals.
     * Time formatted as "hh:mm AM/PM" (e.g. 12:00 AM)
     *
     * @return List of Strings of a 24-hour period in 15-minute intervals.
     */
    private List<String> generateTimeOptions() {
        List<String> timeOptions = new ArrayList<>();

        // 12:00 AM - 12:45 AM
        for (int minute = 0; minute < 60; minute += 15) {
            String time = String.format("12:%02d AM", minute);
            timeOptions.add(time);
        }

        // 1:00 AM - 11:45 AM
        for (int hour = 1; hour <= 11; hour++) {
            for (int minute = 0; minute < 60; minute += 15) {
                String time = String.format("%d:%02d AM", hour, minute);
                timeOptions.add(time);
            }
        }

        // 12:00 PM - 12:45 PM
        for (int minute = 0; minute < 60; minute += 15) {
            String time = String.format("12:%02d PM", minute);
            timeOptions.add(time);
        }

        // 1:00 PM - 11:45 PM
        for (int hour = 1; hour <= 11; hour++) {
            for (int minute = 0; minute < 60; minute += 15) {
                String time = String.format("%d:%02d PM", hour, minute);
                timeOptions.add(time);
            }
        }

        return timeOptions;
    }

    /**
     * Creates and returns the form layout for defining a Slot object.
     *
     * @return a Form for defining a Slot object.
     */
    protected Form createForm() {
        startTimeField = Field.ofSingleSelectionType(timeOptions)
                .label("Start Time")
                .styleClass("form-combo-box")
                .required("Please choose a start time.");

        endTimeField = Field.ofSingleSelectionType(timeOptions)
                .label("End Time")
                .styleClass("form-combo-box")
                .required("Please choose an end time.");


        return Form.of(
                Group.of(
                        startTimeField,
                        endTimeField
                )
        );
    }

    protected double getMinWidth() {
        return 520;
    }

    /**
     * Constructs and returns a Slot object from extracted form input.
     *
     * @return a Slot object constructed from form input.
     */
    @Override
    public Slot extractItem() {
        return new Slot(
                -1,
                extractStartTime(),
                extractEndTime()
        );
    }

    /**
     * Returns selected start time String extracted from the form.
     *
     * @return start time String.
     */
    private String extractStartTime() {
        return startTimeField.getSelection();
    }

    /**
     * Returns selected end time String extracted from the form.
     *
     * @return end time String.
     */
    private String extractEndTime() {
        return endTimeField.getSelection();
    }
}
