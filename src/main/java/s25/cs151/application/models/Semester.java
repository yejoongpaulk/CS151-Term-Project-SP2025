package s25.cs151.application.models;

import s25.cs151.application.models.validators.ModelValidators;

import java.util.ArrayList;
import java.util.Objects;


public class Semester {
    private long id;
    private String season;
    private String year;
    private String days;
    private ArrayList<Slot> slots;

    /**
     * Given a season (i.e. "Spring") and a year (i.e. "2024"), create
     * a Semester instance.
     * @param id - id for object
     * @param season - season of Semester
     * @param year - year of Semester
     * @param days - days available in Semester
     */
    public Semester(long id, String season, String year, String days) {
        // EXAMPLE CHECK(S): check parameters
        ModelValidators.checkId(id);
        ModelValidators.checkSeason(season);
        ModelValidators.checkYear(year);
        ModelValidators.checkDays(days);

        this.id = id;
        this.season = season;
        this.year = year;
        this.days = days;
    }

    /**
     * Get the (database) ID associated with the object.
     * @return long
     */
    public long getId() {
        return this.id;
    }


    /**
     * Return the season for this given Semester instance.
     * @return String - season
     */
    public String getSeason() {
        return this.season;
    }

    /**
     * Set the season for this given Semester instance.
     * @param season - set season
     */
    public void setSeason(String season) {
        // EXAMPLE CHECK: check that season is valid
        ModelValidators.checkSeason(season);

        this.season = season;
    }

    /**
     * Get the year for this given Semester instance.
     * @return String - year
     */
    public String getYear() {
        return this.year;
    }

    /**
     * Set the year for this given Semester instance.
     * @param year - String year
     */
    public void setYear(String year) {
        // EXAMPLE CHECK: check that year is valid
        ModelValidators.checkYear(year);

        this.year = year;
    }

    /**
     * Get the days for the Semester instance.
     * @return days available
     */
    public String getDays() {
        return days;
    }

    /**
     * Set the days for the Semester instance.
     * @param days - String days available
     */
    public void setDays(String days) {
        // EXAMPLE CHECK: check that days is valid
        ModelValidators.checkDays(days);

        this.days = days;
    }

    /**
     * Get all slots from Semester.
     * @return ArrayList of Slot objects
     */
    public ArrayList<Slot> getSlots() {
        return this.slots;
    }

    /**
     * Set all slots for Semester.
     * @param slots ArrayList of Slot objects
     */
    public void setSlots(ArrayList<Slot> slots) {
        this.slots = slots;
    }

    /**
     * Check if this Semester equals another object based on year and season.
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

        Semester that = (Semester) other;
        return Objects.equals(this.year, that.year) && Objects.equals(this.season, that.season);
    }

    /**
     * Give a quick String representation of the Semester.
     * @return String representation (basic) of semester
     */
    public String toString() {
        // concatenate the semester id and the days available
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getSeason());
        stringBuilder.append(" ");
        stringBuilder.append(this.getYear());
        stringBuilder.append(" ");
        stringBuilder.append(this.getDays());

        return stringBuilder.toString();
    }
}
