package s25.cs151.application.views.components.form;

import com.dlsc.formsfx.model.structure.*;
import com.dlsc.formsfx.view.renderer.FormRenderer;
import javafx.beans.property.BooleanProperty;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * Abstract base class for all form components used for data definition (e.g. Course).
 * This class serves as a reusable Form with initialization, layout, data extraction, and form reset logic.
 * Subclasses must define the form layout and provide logic to extract the corresponding data object.
 *
 * @param <DataType> The type of data to be extracted from the form.
 */
public abstract class AbstractDataDefinitionForm<DataType> implements FormComponent<DataType> {
    private final VBox container;
    protected Form form;

    /**
     * Initializes the VBox containing the Form component.
     */
    public AbstractDataDefinitionForm() {
        this.container = new VBox();
    }

    /**
     * Creates and returns the form layout for defining a specific {@code DataType} object.
     * Subclasses must define the fields, groups, structure, and requirements of the form.
     *
     * @return Form representing the layout and logic for defining a specific {@code DataType} object.
     */
    protected abstract Form createForm();

    /**
     * Constructs and returns a data object of type {@code DataType} based on extracted form input.
     * Subclasses must define logic for extracting a concrete data object from the form's fields.
     *
     * @return {@code DataType} object as defined by form fields.
     */
    @Override
    public abstract DataType extractItem();

    /**
     * Initializes the form component by creating and rendering the form in the container.
     */
    public final void initialize() {
        this.form = createForm();
        FormRenderer renderer = new FormRenderer(form);
        renderer.setMinWidth(getMinWidth());
        this.container.getChildren().add(renderer);
    }

    protected abstract double getMinWidth();

    public final BooleanProperty validProperty() {
        return form.validProperty();
    }

    /**
     * Returns whether the current form state is valid.
     *
     * @return {@code true} if the form is valid and {@code false} otherwise.
     */
    public final boolean isFormInvalid() {
        return !form.isValid();
    }

    /**
     * Resets the form to its default state.
     * Subclasses may override this method to provide custom reset logic (e.g. default fields).
     */
    public void resetForm() {
        form.reset();
    }

    /**
     * Get layout object, which should include the widget itself.
     */
    public final Pane getLayout() {
        return container;
    }

    public void refreshForm() {
        container.getChildren().clear();
        this.form = createForm();
        FormRenderer renderer = new FormRenderer(form);
        container.getChildren().add(renderer);
    }

}
