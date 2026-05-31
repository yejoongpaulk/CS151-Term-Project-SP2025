package s25.cs151.application.views.pages;

import s25.cs151.application.controllers.CourseDataPageController;
import s25.cs151.application.models.Course;
import s25.cs151.application.views.components.form.CourseForm;
import s25.cs151.application.views.components.tableview.CourseTableView;
import s25.cs151.application.views.constants.FormMode;
import s25.cs151.application.views.constants.PageIdConstants;
import s25.cs151.application.views.constants.SaveResult;

/**
 * A concrete page for managing Course data.
 */
public class CourseDataPage extends AbstractDataPage<Course, CourseForm, CourseTableView> {

    public CourseDataPage() {
        super(new CourseDataPageController());
    }

    /**
     * Returns the title text for this page.
     *
     * @return title text, "COURSE MANAGEMENT"
     */
    @Override
    public String getPageTitle() {
        return "Courses";
    }

    /**
     * Creates and returns a new CourseForm used by this page.
     *
     * @return a new CourseForm object.
     */
    @Override
    protected CourseForm createForm() {
        return new CourseForm();
    }

    @Override
    protected String getResultMessage(Course item, SaveResult result) {
        return switch (result) {
            case SAVED -> String.format(
                    "Success: The course %s-%s %s has been saved.",
                    item.getCode(),
                    item.getSection(),
                    item.getName()
            );
            case DUPLICATE -> String.format(
                    "Error: The course %s-%s %s already exists.",
                    item.getCode(),
                    item.getSection(),
                    item.getName()
            );
            case EDITED -> throw new IllegalStateException("EDITED result should never occur for Course.");

        };
    }

    @Override
    protected String getContextMessage(FormMode mode) {
        return "Fill out the form to add a new course.";
    }

    /**
     * Constructs and returns a new CourseTableView used by this page.
     *
     * @return a new CourseTableView object.
     */
    @Override
    protected CourseTableView createTableView() {
        return new CourseTableView(controller.getDisplayedItems());
    }

    @Override
    public String getPageId() {
        return PageIdConstants.COURSE_PAGE_ID;
    }
}