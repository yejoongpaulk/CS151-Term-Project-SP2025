package s25.cs151.application.views.pages;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import s25.cs151.application.views.constants.PageIdConstants;
import s25.cs151.application.views.constants.StyleNameConstants;

/**
 * HomePage, an implementation of the Page abstract class, is the Page instance concerned with
 * the widgets and layout associated with the homepage of the application.
 */
public class HomePage implements Page {

    private final VBox content = new VBox();

    public String getPageTitle() {
        return "Home";
    }

    /**
     * Get the pageId of the Page instance.
     *
     * @return String pageId of the Page instance
     */
    @Override
    public String getPageId() {
        return PageIdConstants.HOME_PAGE_ID;
    }

    /**
     * Initialize the scene of the page, or, if it's called again,
     * reload/reset all widgets of the page's scene. This is the method where the
     * layout of the page is defined by code (i.e. the widgets, the layouts, etc.).
     * By the end of this method, the Scene object contained by the Page instance
     * should be ready to be displayed when getScene() is called.
     */
    @Override
    public void initializeContent() {
        content.getChildren().clear();
        content.setSpacing(30);

        // create the label for all appointments
        Text appointmentsText = new Text("APPOINTMENTS");
        appointmentsText.getStyleClass().add("home-page-headings");

        // create an empty space with a notice saying that
        // there aren't any appointments (for now)
        VBox appointmentsVBox = new VBox(new Label("There are no upcoming appointments..."));

        // create the label for all courses
        Text coursesText = new Text("COURSES");
        coursesText.getStyleClass().add("home-page-headings");

        // create an empty space with a notice saying that
        // there aren't any courses (for now)
        VBox coursesVBox = new VBox(new Label("There are no current courses..."));

        appointmentsVBox.getStyleClass().add("home-page-container");
        coursesVBox.getStyleClass().add("home-page-container");

        // appointment and course text objects set style classes
        appointmentsText.getStyleClass().addAll(StyleNameConstants.CENTER_TEXT_CLASS, StyleNameConstants.BOLD_CLASS);
        coursesText.getStyleClass().addAll(StyleNameConstants.CENTER_TEXT_CLASS, StyleNameConstants.BOLD_CLASS);

        // vBox
        content.getChildren().addAll(appointmentsText, appointmentsVBox, coursesText, coursesVBox);
        content.getStyleClass().addAll("home-page-vbox", StyleNameConstants.CENTER_CLASS);
    }

    @Override
    public Node getContent() {
        return content;
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
}
