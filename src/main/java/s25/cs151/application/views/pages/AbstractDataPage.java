package s25.cs151.application.views.pages;

import javafx.animation.PauseTransition;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.*;
import javafx.util.Duration;
import s25.cs151.application.controllers.AbstractDataPageController;
import s25.cs151.application.views.components.form.FormComponent;
import s25.cs151.application.views.components.tableview.TableViewComponent;
import s25.cs151.application.views.constants.FormMode;
import s25.cs151.application.views.constants.SaveResult;
import s25.cs151.application.views.utils.FormMessageHandler;

/**
 * A reusable abstract base class for constructing data definition pages used for managing {@code DataType} items.
 * Defines the UI structure for pages including components like sidemenu, data definition forms, and table views.
 *
 * @param <DataType> The data model managed by this page.
 * @param <FormType> The form component used to define and construct new {@code DataType} objects.
 * @param <TableType> The table component used to display {@code DataType}
 */
public abstract class AbstractDataPage<
        DataType,
        FormType extends FormComponent<DataType>,
        TableType extends TableViewComponent<DataType>>
        implements Page {

    protected final SplitPane splitPane = new SplitPane();
    protected final AbstractDataPageController<DataType> controller;

    protected FormType form;
    protected TableType tableView;
    private Label messageLabel;

    private VBox pageLayout;
    protected VBox tableSection;
    protected VBox formSection;

    private Button saveButton;
    private Button clearButton;
    private Button addButton;
    private Button hideFormButton;

    private PauseTransition messageDelay;

    /**
     * Constructs a new AbstractDataPage with the specified controller.
     *
     * @param controller - The controller responsible for managing data and view operations for this page.
     */
    public AbstractDataPage(AbstractDataPageController<DataType> controller) {
        this.controller = controller;
    }

    /**
     * Initialize the scene of the page, or, if it's called again,
     * reload/reset all widgets of the page's scene. This is the method where the
     * layout of the page is defined by code (i.e. the widgets, the layouts, etc.).
     * By the end of this method, the Scene object contained by the Page instance
     * should be ready to be displayed when getContent() is called.
     */
    @Override
    public void initializeContent() {
        splitPane.setOrientation(Orientation.HORIZONTAL);

        form = createForm();
        form.initialize();

        tableView = createTableView();
        tableView.initialize();

        tableSection = createTableSection();
        formSection = createFormSection();

        splitPane.getItems().add(tableSection);

        pageLayout = new VBox(10, splitPane);
        VBox.setVgrow(splitPane, Priority.ALWAYS);
    }
    /**
     * Instantiates the form used for defining {@code DataType} objects.
     *
     * @return - The form component for this page.
     */
    protected abstract FormType createForm();

    /**
     * Instantiates the table view used for displaying {@code DataType} objects.
     *
     * @return - The table view component for this page.
     */
    protected abstract TableType createTableView();

    protected VBox createTableSection() {
        addButton = new Button("Add");
        addButton.getStyleClass().addAll("add-button");
        addButton.setOnAction(_ -> showForm());

        HBox bottomBar = new HBox(10, addButton);

        VBox tableContainer = new VBox(10, createTopBar(), tableView.getLayout(), bottomBar);
        VBox.setVgrow(tableContainer, Priority.ALWAYS);
        tableContainer.setStyle("-fx-padding: 20px; -fx-alignment: top-center;");
        return tableContainer;
    }

    /**
     * Creates the form view layout.
     *
     * @return the Pane containing the form, save, and cancel button.
     */
    private VBox createFormSection() {
        saveButton = new Button("Save");
        clearButton = new Button("Clear");
        hideFormButton = new Button("Close");

        saveButton.setDisable(true);
        saveButton.setOnAction(_ -> saveForm());
        clearButton.setOnAction(_ -> clearForm());
        hideFormButton.setOnAction(_ -> hideForm());

        saveButton.getStyleClass().add("save-form-button");
        clearButton.getStyleClass().add("clear-form-button");
        hideFormButton.getStyleClass().add("hide-form-button");

        messageLabel = new Label();
        messageLabel.getStyleClass().add("form-message");

        HBox buttonBox = new HBox(10, saveButton, clearButton, hideFormButton);

        VBox formContainer = new VBox(0, messageLabel, form.getLayout(), buttonBox);
        formContainer.setStyle("-fx-alignment: top-center;");

        VBox.setMargin(buttonBox, new Insets(-10, 20, 0, 20));
        VBox.setMargin(messageLabel, new Insets(0, 0, -10, 0));
        VBox.setVgrow(formContainer, Priority.ALWAYS);
        buttonBox.setMinWidth(165);

        form.validProperty().addListener(
                (_, _, newVal) -> saveButton.setDisable(!newVal)
        );

        return formContainer;
    }

    protected SaveResult handleFormSubmission(DataType item) {
        return controller.handleSave(item);
    }

    protected void saveForm() {
        DataType item = form.extractItem();
        SaveResult result = handleFormSubmission(item);

        if (result != SaveResult.DUPLICATE) {
            form.resetForm();
        }

        applyResultMessage(result, item);
    }

    private void clearForm() {
        form.resetForm();
        applyContextMessage();
    }

    protected void showForm() {
        if (!splitPane.getItems().contains(formSection)) {
            splitPane.getItems().add(formSection);
            splitPane.setDividerPositions(0.45);
            addButton.setDisable(true);
        }
        applyContextMessage();
    }

    private void hideForm() {
        splitPane.getItems().remove(formSection);
        form.resetForm();
        addButton.setDisable(false);
        FormMessageHandler.reset(messageLabel);
    }

    private void applyResultMessage(SaveResult result, DataType item) {
        FormMessageHandler.showResult(messageLabel, result, getResultMessage(item, result));
        updateFormIndicator();
        waitThenRestoreContextMessage();
    }

    protected abstract String getResultMessage(DataType item, SaveResult result);

    private void applyContextMessage() {
        updateFormIndicator();
        FormMessageHandler.showContext(messageLabel, getFormMode(), getContextMessage(getFormMode()));
    }

    protected abstract String getContextMessage(FormMode mode);

    private void updateFormIndicator() {
        formSection.getStyleClass().removeAll("add-form-indicator", "edit-form-indicator");

        switch (getFormMode()) {
            case ADD -> formSection.getStyleClass().add("add-form-indicator");
            case EDIT -> formSection.getStyleClass().add("edit-form-indicator");
        }
    }

    private void waitThenRestoreContextMessage() {
        if (messageDelay != null) {
            messageDelay.stop();
        }

        messageDelay = new PauseTransition(Duration.seconds(5));
        messageDelay.setOnFinished(_ -> applyContextMessage());
        messageDelay.play();
    }

    protected FormMode getFormMode() {
        return FormMode.ADD;
    }

    @Override
    public Node getContent() {
        return pageLayout;
    }

    /**
     * Given a String identification name, find the widget (i.e. TableView, etc.) which has that id.
     *
     * @param widgetId - String id for the widget
     * @return widget in form of object - developer must cast return value appropriately
     */
    @Override
    public Object getWidgetById(String widgetId) {
        return null;
    }

    /**
     * Gets the page ID.
     *
     * @return the page ID string.
     */
    @Override
    public String getPageId() {
        return null;
    }

    protected Node createTopBar() {
        return new Pane();
    }
}
