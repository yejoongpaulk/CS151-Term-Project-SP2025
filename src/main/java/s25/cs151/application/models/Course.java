package s25.cs151.application.models;

import s25.cs151.application.models.validators.ModelValidators;

import java.util.Comparator;

public class Course {
    private long id;
    private String code;
    private String name;
    private String section;

    // Custom Comparator for Course, sorting by code in descending order.
    public static final Comparator<Course> COURSE_CODE_COMPARATOR =
            Comparator.comparing(Course::getCode, String::compareTo).reversed();

    /**
     * Given a code, name, and section, create a Course instance.
     * @param id - id for object
     * @param code - code for class (i.e. CS151)
     * @param name - name for class (i.e. "Object-Oriented Design")
     * @param section - section for class (i.e. "07")
     */
     public Course(long id, String code, String name, String section) {
        ModelValidators.checkId(id);
        ModelValidators.checkCourseCode(code);
        ModelValidators.checkCourseName(name);
        ModelValidators.checkCourseSection(section);

        this.id = id;
        this.code = code;
        this.name = name;
        this.section = section;
    }

    /**
     * Get the (database) ID associated with the object.
     * @return long
     */
    public long getId() {
        return this.id;
    }

    /**
     * Get Course code.
     * @return course code as String
     */
    public String getCode() {
        return code;
    }

    /**
     * Set Course code.
     * @param code - course code as String.
     */
    public void setCode(String code) {
        ModelValidators.checkCourseCode(code);
        this.code = code;
    }

    /**
     * Get name of Course.
     * @return name as String
     */
    public String getName() {
        return name;
    }

    /**
     * Set Course name.
     * @param name - name as String
     */
    public void setName(String name) {
        ModelValidators.checkCourseName(name);
        this.name = name;
    }

    /**
     * Get Course section.
     * @return section as String
     */
    public String getSection() {
        return section;
    }

    /**
     * Set Course section.
     * @param section - section as String
     */
    public void setSection(String section) {
        ModelValidators.checkCourseSection(section);
        this.section = section;
    }

    /**
     * Gets the String representation of CourseId which is formatted as "code-section"
     *
     * @return Formatted Course Id
     */
    public String getCourseId() {
        return code + "-" + section;
    }

    /**
     * Check if this Course equals another object based on code, name, and section.
     * @param other - the object to compare against
     * @return - true if year and season match, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        Course that = (Course) other;
        return this.code.equalsIgnoreCase(that.code) &&
                this.name.equalsIgnoreCase(that.name) &&
                this.section.equals(that.section);
    }

    /**
     * Returns a string representation of Course "code name section"
     * @return - String representation of Course.
     */
    @Override
    public String toString() {
        return code + " " + name + " " + section;
    }
}
