package s25.cs151.application.controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;
import s25.cs151.application.Main;
import s25.cs151.application.views.constants.PageIdConstants;
import s25.cs151.application.views.layout.RootLayout;
import s25.cs151.application.views.pages.Page;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Singleton class that handles pages and interactions between pages.
 */
public class PageHandler {
    // private instance variable that holds a map of
    // Page instances by String
    private Map<String, Page> pageMap;

    // private instance variable holding stylesheet resource
    private final URL stylesheet = Main.class.getResource("styles.css");

    // private instance variable holding Stage instance
    private Stage stage;

    private RootLayout rootLayout;
    private Scene mainScene;

    // singleton instance
    private static final PageHandler pageHandlerInstance = new PageHandler();

    /**
     * Private constructor for the singleton instance.
     */
    private PageHandler() {
        // initialize a hash map of pages
        this.pageMap = new HashMap<>();
    }

    /**
     * Return the singleton PageHandler instance.
     * @return PageHandler singleton instance
     */
    public static PageHandler getPageHandlerInstance() {
        return pageHandlerInstance;
    }

    /**
     * Set stage for PageHandler instance.
     * @param stage - Stage object used for scenes
     */
    public void initialize(Stage stage) {
        this.stage = stage;
        this.rootLayout = new RootLayout();
        this.mainScene = new Scene(rootLayout, 1400, 800);
        mainScene.getStylesheets().add(String.valueOf(stylesheet));
        stage.setScene(mainScene);
        stage.setTitle("Office Hour Scheduler");
    }

    /**
     * Given a Page object, add it to map of pages.
     * @param page - Page object to be added
     */
    public void addPage(Page page) {
        this.pageMap.put(page.getPageId(), page);
    }

    /**
     * Given a String pageId, retrieve the page with pageId.
     * @param pageId - String pageId of Page instance
     * @return Page instance
     */
    public Page getPage(String pageId) {
        return this.pageMap.get(pageId);
    }

    /**
     * Given a String pageId, change the current scene
     * to that belonging to the Page instance with String pageId.
     * @param pageId - String pageId of Page instance
     */
    public void switchToPage(String pageId) {
        Page page = pageMap.get(pageId);
        if (page != null) {
            rootLayout.setCenterContent(page.getContent());
            rootLayout.pageTitleProperty().set(page.getPageTitle());
        }
    }

    public static void changeToHomePage() {
        getPageHandlerInstance().switchToPage(PageIdConstants.HOME_PAGE_ID);
    }

    public static void changeToSemesterPage() {
        getPageHandlerInstance().switchToPage(PageIdConstants.SEMESTER_PAGE_ID);
    }

    public static void changeToCoursePage() {
        getPageHandlerInstance().switchToPage(PageIdConstants.COURSE_PAGE_ID);
    }

    public static void changeToSlotPage() {
        getPageHandlerInstance().switchToPage(PageIdConstants.SLOT_PAGE_ID);
    }

    public static void changeToSchedulePage() {
        getPageHandlerInstance().switchToPage(PageIdConstants.SCHEDULE_PAGE_ID);
    }

}