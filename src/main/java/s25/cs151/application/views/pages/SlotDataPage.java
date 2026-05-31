package s25.cs151.application.views.pages;

import s25.cs151.application.controllers.SlotDataPageController;
import s25.cs151.application.models.Slot;
import s25.cs151.application.views.components.form.SlotForm;
import s25.cs151.application.views.components.tableview.SlotTableView;
import s25.cs151.application.views.constants.FormMode;
import s25.cs151.application.views.constants.PageIdConstants;
import s25.cs151.application.views.constants.SaveResult;

/**
 * A concrete page class for managing Slot data.
 */
public class SlotDataPage extends AbstractDataPage<Slot, SlotForm, SlotTableView> {
    public SlotDataPage() {
        super(new SlotDataPageController());
    }

    /**
     * Returns the title text for this page.
     *
     * @return title text, "TIME SLOT MANAGEMENT"
     */
    @Override
    public String getPageTitle() {
        return "Time Slots";
    }

    /**
     * Creates and returns a new SlotForm used by this page.
     *
     * @return a new SlotForm object.
     */
    @Override
    protected SlotForm createForm() {
        return new SlotForm();
    }

    /**
     * Creates and returns a new SlotTableView used by this page.
     *
     * @return a new SlotTableView object.
     */
    @Override
    protected SlotTableView createTableView() {
        return new SlotTableView(controller.getDisplayedItems());
    }

    @Override
    protected String getResultMessage(Slot item, SaveResult result) {
        return switch (result) {
            case SAVED -> String.format(
                    "Success: A time slot from %s to %s has been saved.",
                    item.getStartTime(),
                    item.getEndTime()
            );
            case DUPLICATE -> throw new IllegalStateException("DUPLICATE result should never occur for Slot.");
            case EDITED -> throw new IllegalStateException("EDITED result should never occur for Slot.");
        };
    }

    @Override
    protected String getContextMessage(FormMode mode) {
        return "Fill out the form to add a new time slot.";
    }

    @Override
    public String getPageId() {
        return PageIdConstants.SLOT_PAGE_ID;
    }
}
