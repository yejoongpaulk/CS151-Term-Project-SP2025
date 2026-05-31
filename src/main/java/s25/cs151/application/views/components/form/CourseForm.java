package s25.cs151.application.views.components.form;

import com.dlsc.formsfx.model.structure.*;
import s25.cs151.application.models.Course;

/**
 * A concrete form component user for Course definition and construction.
 * This class serves as a reusable course form with form creation and data extraction logic.
 */
public class CourseForm extends AbstractDataDefinitionForm<Course> {
    // Form fields
    private StringField courseCodeField;
    private StringField courseNameField;
    private StringField courseSectionField;

    /**
     * Creates and returns the form layout for defining a Course object.
     *
     * @return a Form for defining a Course object.
     */
    @Override
    protected Form createForm() {
        courseCodeField = Field.ofStringType("")
                .label("* Course Code")
                .styleClass("form-text-field")
                .placeholder("e.g. CS151, Math42")
                .required("Course code is required");

        courseNameField = Field.ofStringType("")
                .label("* Course Name")
                .styleClass("form-text-field")
                .placeholder("e.g. Object-Oriented Design")
                .required("Course name is required");

        courseSectionField = Field.ofStringType("")
                .label("* Section Number")
                .styleClass("form-text-field")
                .placeholder("e.g. 01, 04, 81")
                .required("Section number is required");

        return Form.of(
                Group.of(
                        courseCodeField,
                        courseNameField,
                        courseSectionField
                )
        );
    }

    /**
     * Constructs and returns a Course object from extracted form input.
     *
     * @return a Course object constructed from form input.
     */
    @Override
    public Course extractItem() {
        return new Course(
                -1,
                extractCourseCode(),
                extractCourseName(),
                extractCourseSection()
        );
    }

    protected double getMinWidth() {
        return 800;
    }

    /**
     * Returns the course code extracted from the form.
     *
     * @return course code String.
     */
    private String extractCourseCode() {
        return courseCodeField.getValue().trim().toUpperCase();
    }

    /**
     * Returns the course name extracted from the form.
     *
     * @return course name String.
     */
    private String extractCourseName() {
        return courseNameField.getValue().trim();
    }

    /**
     * Returns the course section extracted from the form.
     *
     * @return course section String.
     */
    private String extractCourseSection() {
        return courseSectionField.getValue().trim();
    }
}
