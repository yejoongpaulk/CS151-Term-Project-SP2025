package s25.cs151.application.views.components.reusable;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import s25.cs151.application.views.components.GUIComponent;
import s25.cs151.application.controllers.PageHandler;

public class SideMenu implements GUIComponent {
    // private instance variable holding vbox
    private VBox vBox;
    private ToggleGroup toggleGroup;

    /**
     * Initialize all widgets in the GUIComponent.
     */
    @Override
    public void initialize() {
        // sidebar layout object
        this.vBox = new VBox();
        this.vBox.getStyleClass().add("sidebar");

        // label for app name
        Label appLabel = new Label("[TBD]");
        appLabel.getStyleClass().add("app-label");

        // all menu options: Home, Schedule, Semester, Course, EXIT
        toggleGroup = new ToggleGroup();

        ToggleButton homeButton = createToggleButton("Home", PageHandler::changeToHomePage);
        ToggleButton scheduleButton = createToggleButton("Schedule", PageHandler::changeToSchedulePage);
        ToggleButton semesterButton = createToggleButton("Semester", PageHandler::changeToSemesterPage);
        ToggleButton courseButton = createToggleButton("Course", PageHandler::changeToCoursePage);
        ToggleButton slotButton = createToggleButton("Slot", PageHandler::changeToSlotPage);

        Button exitButton = new Button("EXIT");
        exitButton.setOnAction(_ -> Platform.exit());
        exitButton.getStyleClass().add("side-exit-button");

        homeButton.setSelected(true);

        vBox.getChildren().addAll(appLabel, homeButton, scheduleButton, semesterButton, slotButton, courseButton, exitButton);

    }

    private ToggleButton createToggleButton(String label, Runnable action) {
        ToggleButton toggleButton = new ToggleButton(label);
        toggleButton.setToggleGroup(toggleGroup);
        toggleButton.getStyleClass().add("sidebar-button");
        toggleButton.setFocusTraversable(false);

        toggleButton.setOnAction(_ -> {
            if (!toggleButton.isSelected()) {
                toggleButton.setSelected(true);
            }
            action.run();
        });
        return toggleButton;
    }

    /**
     * Get layout object, which should include the widget itself.
     */
    @Override
    public VBox getLayout() {
        return this.vBox;
    }
}