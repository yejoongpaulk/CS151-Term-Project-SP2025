package s25.cs151.application.views.components.form;

import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.model.validators.CustomValidator;
import com.dlsc.formsfx.view.controls.SimpleCheckBoxControl;
import s25.cs151.application.models.Semester;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A concrete form component user for Semester definition and construction.
 * This class serves as a reusable semester form with form creation, data extraction, and form reset logic.
 */
public class SemesterForm extends AbstractDataDefinitionForm<Semester> {
    // Form fields
    private SingleSelectionField<String> seasonField;
    private StringField yearField;
    private MultiSelectionField<String> daysField;

    // Field options
    private final static List<String> SEMESTER_OPTIONS = List.of("Spring", "Summer", "Fall", "Winter");
    private final static List<String> DAY_OPTIONS = List.of("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");

    /**
     * Creates and returns the form layout for defining a Semester object.
     *
     * @return a Form for defining a Semester object.
     */
    @Override
    protected Form createForm() {
        seasonField = Field.ofSingleSelectionType(SEMESTER_OPTIONS)
                .label("* Semester")
                .styleClass("form-combo-box")
                .required("Please select a semester.")
                .select(0);

        yearField = Field.ofStringType("")
                .label("* Year")
                .styleClass("form-text-field")
                .placeholder("Please enter a 4-digit year (i.e. 2025).")
                .validate(
                        CustomValidator.forPredicate(value -> {
                                    try {
                                        int year = Integer.parseInt(value);
                                        return year >= 1000 && year <= 9999;
                                    } catch (NumberFormatException e) {
                                        return false;
                                    }
                                },
                                "Please enter a 4-digit year (i.e. 2025)."));

        daysField = Field.ofMultiSelectionType(DAY_OPTIONS)
                .label("* Day(s)")
                .required("Please select at least one day.")
                .render(new SimpleCheckBoxControl<>())
                .styleClass("form-checkbox");


        return Form.of(
                Group.of(
                        seasonField,
                        yearField,
                        daysField
                )
        );
    }

    protected double getMinWidth() {
        return 540;
    }

    /**
     * Constructs and returns a Semester object from extracted form input.
     *
     * @return a Semester object constructed from form input.
     */
    @Override
    public Semester extractItem() {
        return new Semester(
                -1,
                extractSeason(),
                extractYear(),
                extractDays()
        );
    }

    /**
     * Returns the selected season String extracted from the form.
     *
     * @return season String.
     */
    private String extractSeason() {
        return seasonField.getSelection();
    }

    /**
     * Returns the year String extracted from the form.
     *
     * @return year String.
     */
    private String extractYear() {
        return yearField.getValue();
    }

    /**
     * Returns a String of days extracted from the form.
     *
     * @return days selected String.
     */
    private String extractDays() {
        return DAY_OPTIONS.stream()
                .map(day -> daysField.getSelection().contains(day) ? "T" : "F")
                .collect(Collectors.joining());
    }

    /**
     * Resets the form to its default state.
     * Sets season field to "Spring".
     */
    @Override
    public void resetForm() {
        form.reset();
        seasonField.select(0);
    }
}
