package s25.cs151.application.views.utils;

import javafx.scene.control.Label;
import s25.cs151.application.views.constants.FormMode;
import s25.cs151.application.views.constants.SaveResult;

public class FormMessageHandler {
    private FormMessageHandler() {}

    private static void clearMessageStyles(Label label) {
        label.getStyleClass().removeAll(
                "form-success-message",
                "form-edited-message",
                "form-error-message",
                "addable-form-message",
                "editable-form-message"
        );
    }

    public static void showResult(Label label, SaveResult result, String message) {
        clearMessageStyles(label);
        label.setText(message);
        switch (result) {
            case SAVED, EDITED -> label.getStyleClass().add("form-success-message");
            case DUPLICATE -> label.getStyleClass().add("form-error-message");
        }
    }

    public static void reset(Label label) {
        clearMessageStyles(label);
        label.setText("");
    }

    public static void showContext(Label label, FormMode formMode, String message) {
        clearMessageStyles(label);
        label.setText(message);
        switch (formMode) {
            case ADD -> label.getStyleClass().add("addable-form-message");
            case EDIT -> label.getStyleClass().add("editable-form-message");
        }
    }
}
