package s25.cs151.application.views.pages;

import javafx.scene.Node;

public interface Page {
    /**
     * Get the pageId of the Page instance.
     * @return String pageId of the Page instance
     */
    String getPageId();

    /**
     * Initialize the scene of the page, or, if it's called again,
     * reload/reset all widgets of the page's scene. This is the method where the
     * layout of the page is defined by code (i.e. the widgets, the layouts, etc.).
     * By the end of this method, the Scene object contained by the Page instance
     * should be ready to be displayed when getScene() is called.
     */
    void initializeContent();

    Node getContent();

    String getPageTitle();

    /**
     * Given a String identification name, find the widget (i.e. TableView, etc.) which has that id.
     * @param widgetId - String id for the widget
     * @return widget in form of object - developer must cast return value appropriately
     */
    Object getWidgetById(String widgetId);
}
