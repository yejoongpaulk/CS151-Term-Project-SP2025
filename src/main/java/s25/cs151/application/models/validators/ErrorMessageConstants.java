package s25.cs151.application.models.validators;

public class ErrorMessageConstants {
    public static final String ID_IS_INVALID = "Long id given to object must be -1 or >= 1.";
    public static final String SEASON_IS_NULL_OR_BLANK = "Season cannot be null or blank.";
    public static final String SEASON_IS_INVALID = "Season must be one of: (Winter, Spring, Summer, Fall).";
    public static final String YEAR_IS_NULL_OR_BLANK = "Year cannot be null or blank.";
    public static final String YEAR_IS_INVALID = "Year must be exactly 4 digits.";
    public static final String DAYS_IS_NULL_OR_BLANK = "Days cannot be null or blank.";
    public static final String DAYS_IS_INVALID = "Five days must be saved, whether true or untrue.";

    public static final String COURSE_IS_NULL = "Course cannot be null.";
    public static final String SLOT_IS_NULL = "Slot cannot be null.";
    public static final String STUDENT_IS_NULL_OR_BLANK = "Student name cannot be null or blank.";
    public static final String REASON_IS_NULL_OR_BLANK = "Student name cannot be null or blank.";


    public static final String COURSE_CODE_IS_NULL_OR_BLANK = "Course code cannot be null or blank.";
    public static final String COURSE_NAME_IS_NULL_OR_BLANK = "Course name cannot be null or blank.";
    public static final String COURSE_SECTION_IS_NULL_OR_BLANK = "Course section cannot be null or blank.";


    public static final String STARTING_TIME_IS_NULL_OR_BLANK = "Starting time cannot be null or blank.";
    public static final String ENDING_TIME_IS_NULL_OR_BLANK = "Ending time cannot be null or blank.";
}
