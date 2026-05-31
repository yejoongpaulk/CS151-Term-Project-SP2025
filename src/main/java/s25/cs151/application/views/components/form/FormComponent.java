package s25.cs151.application.views.components.form;

import javafx.beans.property.BooleanProperty;
import s25.cs151.application.views.components.GUIComponent;

/**
 * A generic interface representing a form component for defining, extracting, and constructing {@code DataType} objects.
 *
 * @param <DataType> the type of data object the form constructs (e.g., Course, Appointment)
 */
public interface FormComponent<DataType> extends GUIComponent {

    /**
     * Returns whether the current form state is invalid.
     *
     * @return {@code true} if the form is invalid and {@code false} otherwise.
     */
    boolean isFormInvalid();

    BooleanProperty validProperty();

    /**
     * Constructs and returns a data object of type {@code DataType} based on extracted form input.
     *
     * @return {@code DataType} object as defined by form fields.
     */
    DataType extractItem();

    /**
     * Resets the form to its default state.
     */
    void resetForm();
}
