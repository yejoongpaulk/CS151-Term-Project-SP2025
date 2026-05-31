package s25.cs151.application.models.validators;

import s25.cs151.application.models.Course;
import s25.cs151.application.models.Slot;

public class ModelValidators {
    /**
     * Check that a long id passed into any model is either -1 (representing a new object) or
     * a positive number (that is, a number that is >= 1). If the id is either 0 or any other
     * negative number, then throw an IllegalArgumentException with ErrorMessageConstants.INVALID_ID as
     * a message.
     *
     * @param id - long number to check
     */
    public static void checkId(long id) {
        if (id < -1 || id == 0) {
            throw new IllegalArgumentException(ErrorMessageConstants.ID_IS_INVALID);
        }
    }


    /**
     * Check if season is not empty, and that it is a valid format. If the season is null or blank, then throw
     * an IllegalArgumentException with ErrorMessageConstants.SEMESTER_IS_NULL_OR_BLANK. If the season is
     * not one of: [Winter, Spring, Summer, Fall], then throw an IllegalArgumentException with
     * ErrorMessageConstants.SEMESTER_IS_INVALID as a message.
     *
     * @param season - String to check
     */
    public static void checkSeason(String season) {
        // check null or blank
        if (season == null || season.isBlank()) {
            throw new IllegalArgumentException(ErrorMessageConstants.SEASON_IS_NULL_OR_BLANK);
        }

        // check if season is one of the four seasons
        if (!season.equals("Winter") && !season.equals("Spring") && !season.equals("Summer") && !season.equals("Fall")) {
            throw new IllegalArgumentException(ErrorMessageConstants.SEASON_IS_INVALID);
        }
    }

    /**
     * Check if year is not empty, and that it is a valid format. If the year is null or blank, then
     * throw an IllegalArgumentException with ErrorMessageConstants.YEAR_IS_NULL_OR_BLANK. If the year is
     * not exactly four digits, then throw an IllegalArgumentException with ErrorMessageConstants.YEAR_IS_INVALID as
     * a message.
     *
     * @param year - String to check
     */
    public static void checkYear(String year) {
        // check if year is not blank
        if (year == null || year.isBlank()) {
            throw new IllegalArgumentException(ErrorMessageConstants.YEAR_IS_NULL_OR_BLANK);
        }

        // check if year is exactly four digits
        if (!year.matches("\\d{4}")) {
            throw new IllegalArgumentException(ErrorMessageConstants.YEAR_IS_INVALID);
        }
    }

    /**
     * Check if days is not empty, and that it is a valid format. If days is null or blank, then
     * throw an IllegalArgumentException with ErrorMessageConstants.DAYS_IS_NULL_OR_BLANK. If the year is
     * not exactly four digits, then throw an IllegalArgumentException with ErrorMessageConstants.DAYS_IS_INVALID as
     * a message.
     *
     * @param days - String to check
     */
    public static void checkDays(String days) {
        // check if year is not blank
        if (days == null || days.isBlank()) {
            throw new IllegalArgumentException(ErrorMessageConstants.DAYS_IS_NULL_OR_BLANK);
        }

        // check if year is exactly five "T" or "F" characters.
        if (!days.matches("[TF]{5}")) {
            throw new IllegalArgumentException(ErrorMessageConstants.DAYS_IS_INVALID);
        }
    }

    public static void checkStudent(String student) {
        if (student == null || student.isBlank()) {
            throw new IllegalArgumentException("Student name cannot be null or blank.");
        }
    }

    public static void checkDate(String date) {
        if (date == null || date.isBlank()) {
            throw new IllegalArgumentException("Date cannot be null or blank.");
        }
    }

    public static void checkSlot(Slot slot) {
        if (slot == null) {
            throw new IllegalArgumentException("Slot cannot be null.");
        }
    }

    public static void checkCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }
    }

    public static void checkCourseCode(String code) {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Course code cannot be null or blank.");
        }
    }

    public static void checkCourseName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Course name cannot be null or blank.");
        }
    }

    public static void checkCourseSection(String section) {
        if (section == null || section.isBlank()) {
            throw new IllegalArgumentException("Course section cannot be null or blank.");
        }
    }

    public static void checkStartTime(String startTime) {
        if (startTime == null || startTime.isBlank()) {
            throw new IllegalArgumentException("Start time cannot be null or blank.");
        }
    }

    public static void checkEndTime(String endTime) {
        if (endTime == null || endTime.isBlank()) {
            throw new IllegalArgumentException("End time cannot be null or blank.");
        }
    }
}
