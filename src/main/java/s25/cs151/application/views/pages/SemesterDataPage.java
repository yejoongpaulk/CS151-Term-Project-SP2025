package s25.cs151.application.views.pages;

import s25.cs151.application.controllers.SemesterDataPageController;
import s25.cs151.application.models.Semester;
import s25.cs151.application.views.components.form.SemesterForm;
import s25.cs151.application.views.components.tableview.SemesterTableView;
import s25.cs151.application.views.constants.FormMode;
import s25.cs151.application.views.constants.PageIdConstants;
import s25.cs151.application.views.constants.SaveResult;

/**
 * A concrete page for managing Semester data.
 */
public class SemesterDataPage extends AbstractDataPage<Semester, SemesterForm, SemesterTableView> {

    public SemesterDataPage() {
        super(new SemesterDataPageController());
    }

    /**
     * Returns the title text for this page.
     *
     * @return title text, "SEMESTER MANAGEMENT"
     */
    @Override
    public String getPageTitle() {
        return "Semesters";
    }

    /**
     * Creates and returns a new SemesterForm used by this page.
     *
     * @return a new SemesterForm object.
     */
    @Override
    protected SemesterForm createForm() {
        return new SemesterForm();
    }

    @Override
    protected String getResultMessage(Semester item, SaveResult result) {
        return switch (result) {
            case SAVED -> String.format(
                    "Success: The %s %s semester has been saved.",
                    item.getSeason(),
                    item.getYear()
            );
            case DUPLICATE -> String.format(
                    String.format(
                            "Error: The %s %s semester has already been saved.",
                            item.getSeason(),
                            item.getYear()
                    )
            );
            case EDITED -> throw new IllegalStateException("EDITED result should never occur for Semester.");

        };
    }

    @Override
    protected String getContextMessage(FormMode mode) {
        return "Fill out the form to add a new semester.";
    }

    /**
     * Creates and returns a new SemesterTableView used by this page.
     *
     * @return a new SemesterTableView object.
     */
    @Override
    protected SemesterTableView createTableView() {
        return new SemesterTableView(controller.getDisplayedItems());
    }

    @Override
    public String getPageId() {
        return PageIdConstants.SEMESTER_PAGE_ID;
    }
}
