package s25.cs151.application;

import javafx.application.Application;
import javafx.stage.Stage;
import s25.cs151.application.controllers.PageHandler;
import s25.cs151.application.data.handlers.SQLiteStorageHandler;
import s25.cs151.application.views.constants.PageIdConstants;
import s25.cs151.application.views.pages.*;

import java.io.IOException;

public class Main extends Application {
    private Stage stage;
    private PageHandler pageHandler;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            this.stage = stage;

            // connection
            SQLiteStorageHandler storageHandler = SQLiteStorageHandler.getStorageHandlerInstance();
            storageHandler.initializeStorage(".");

            pageHandler = PageHandler.getPageHandlerInstance();
            pageHandler.initialize(stage);

            //setupStorage();
            setupPages();

            //setupStage();

            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        if (SQLiteStorageHandler.getStorageHandlerInstance() != null) {
            SQLiteStorageHandler.getStorageHandlerInstance().closeConnection();
        }
    }

    /**
     * Initializes the storage handler to ensure semesters.csv exists.
     * Must be called before Pages are initialized;
     */
    private void setupStorage() {
        SQLiteStorageHandler storageHandler = SQLiteStorageHandler.getStorageHandlerInstance();
        storageHandler.initializeStorage(".");
    }

    /**
     * Creates, initializes, and registers Pages with PageHandler.
     */
    private void setupPages() {
        PageHandler pageHandler = PageHandler.getPageHandlerInstance();

        // Create, initialize, and register all pages
        HomePage homePage = new HomePage();
        homePage.initializeContent();
        pageHandler.addPage(homePage);

        SemesterDataPage semesterPage = new SemesterDataPage();
        semesterPage.initializeContent();
        pageHandler.addPage(semesterPage);

        CourseDataPage coursePage = new CourseDataPage();
        coursePage.initializeContent();
        pageHandler.addPage(coursePage);

        SlotDataPage slotPage = new SlotDataPage();
        slotPage.initializeContent();
        pageHandler.addPage(slotPage);

        AppointmentDataPage appointmentPage = new AppointmentDataPage();
        appointmentPage.initializeContent();
        pageHandler.addPage(appointmentPage);

        // Initially switch to the home page
        pageHandler.switchToPage(PageIdConstants.HOME_PAGE_ID);
    }


    /**
     * Sets up the stage and launch with HomePage.
     */
    private void setupStage() {
        stage.setTitle("Application");
        pageHandler.switchToPage(PageIdConstants.HOME_PAGE_ID);
        stage.setWidth(1000);
        stage.setHeight(700);
    }

    public static void main(String[] args) {
        launch();
    }
}