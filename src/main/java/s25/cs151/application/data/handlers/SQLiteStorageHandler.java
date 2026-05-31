package s25.cs151.application.data.handlers;

import s25.cs151.application.models.Appointment;
import s25.cs151.application.models.Course;
import s25.cs151.application.models.Semester;
import s25.cs151.application.models.Slot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLiteStorageHandler implements DataStorageHandler {

    private Connection connection;
    private final String DATABASE_NAME = "data.db";
    private final String STORAGE_FOLDER_NAME = "data-storage";


    private static final SQLiteStorageHandler classInstance = new SQLiteStorageHandler();


    /**
     * Return the singleton SQLiteStorageHandler instance;
     * @return - SQLiteStorageHandler singleton instance
     */
    public static SQLiteStorageHandler getStorageHandlerInstance() {
        return classInstance;
    }

    /**
     * Closes connection.
     */
    public void closeConnection() {
        try {
            if (this.connection != null) {
                this.connection.close();
            }
        }
        catch (SQLException e) {
            System.err.println("Failed to close connection");
        }
    }


    /**
     * Given a String directory path for a folder, initialize all data
     * in that folder.
     *
     * @param folderPathStr - folder location where all data is stored
     */
    @Override
    public void initializeStorage(String folderPathStr) {
        // check if tables need to be initialized
        boolean needToCreateTables = true;

        // get path of folder
        Path folderPath = Paths.get(folderPathStr);

        // create a relative path for the database itself
        Path databasePath = Paths.get(DATABASE_NAME);

        // create a joined path
        Path fullPath = folderPath.resolve(databasePath);

        try {
            // construct File instance for the folder
            File folder = new File(folderPath.toString());

            // construct File instance for database itself
            File database = new File(fullPath.toString());

            // if directory does not exist, make one
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // if database does not exist, make one
            if (!database.exists()) {
                database.createNewFile();
            } else {
                needToCreateTables = false;
            }

            // create connection
            this.connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", fullPath.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (!needToCreateTables) {
            return;
        }

        try (Statement statement = this.connection.createStatement()) {
            // enable foreign keys
            String enableForeignKeysQuery = "PRAGMA foreign_keys = ON";

            // create table queries
            String createSemesterTableQuery = "create table semester (semester_id integer primary key, season text not null, year text not null, days text not null)";
            String createCourseTableQuery = "create table course (course_id integer primary key, code unique not null, name text not null, section text not null)";
            String createSlotTableQuery = "create table slot (slot_id integer primary key, start_time text not null, end_time text not null)";
            String createAppointmentTableQuery = "create table appointment (appointment_id integer primary key, course_id_parent integer not null, slot_id_parent integer not null, student text not null, date text not null, reason text not null, comment text not null, foreign key(course_id_parent) references course(course_id), foreign key(slot_id_parent) references slot(slot_id) )";

            // perform queries
            statement.execute(enableForeignKeysQuery);
            statement.execute(createSemesterTableQuery);
            statement.execute(createCourseTableQuery);
            statement.execute(createSlotTableQuery);
            statement.execute(createAppointmentTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Given a Semester object, check if the semester already exists
     * in the data.
     *
     * @param semester - Semester object that function checks if exists in data
     * @return true if semester exists, false otherwise
     */
    @Override
    public boolean checkForSemester(Semester semester) {
        // prepare a statement
        String query = "select count(*) from semester where (season = ? AND year = ?) OR (semester_id = ?)";

        // prepare response
        boolean rowExists = false;

        // auto-close PreparedStatement after it is used
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            // add arguments to SQL query
            statement.setString(1, semester.getSeason());
            statement.setString(2, semester.getYear());
            statement.setLong(3, semester.getId());

            // execute statement
            try (ResultSet resultSet = statement.executeQuery()) {
                // check how many rows match the id
                int count = resultSet.getInt(1);

                // if greater than 0, then confirm that semester exists
                if (count > 0) {
                    rowExists = true;
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowExists;
    }


    /**
     * Given a Semester object, persist semester object in data storage.
     *
     * @param semester - Semester object to save to data
     */
    @Override
    public void saveSemester(Semester semester) {
        // prepare a statement
        String query = "insert into semester values(NULL, ?, ?, ?);";

        // auto-close PreparedStatement after it is used
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            // add arguments to SQL query
            statement.setString(1, semester.getSeason());
            statement.setString(2, semester.getYear());
            statement.setString(3, semester.getDays());

            // execute statement
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Given a list of Semester strings, create semester objects from persistent storage.
     *
     * @param semesterIds - list of semesterIds
     * @return List of Semester objects
     */
    @Override
    public List<Semester> getSemesters(List<String> semesterIds) {
        return List.of();
    }

    /**
     * Retrieve all persisted Semester data as a Collection (preferably a List or a better approach)
     *
     * @return Collection<Semester> - Collection object containing Semester data
     */
    @Override
    public List<Semester> getSemesterList() {
        // arraylist
        List<Semester> semesters = new ArrayList<>();

        // prepare a statement
        String query = "select * from semester;";

        // auto-close PreparedStatement after it is used
        try (Statement statement = this.connection.createStatement()) {
            // execute statement
            try (ResultSet resultSet = statement.executeQuery(query)) {
                // read each row of result set
                while (resultSet.next()) {
                    // get information
                    long semesterId = resultSet.getLong("semester_id");
                    String season = resultSet.getString("season");
                    String year = resultSet.getString("year");
                    String days = resultSet.getString("days");

                    // create semester and add to list
                    semesters.add(new Semester(semesterId, season, year, days));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return semesters;
    }

    /**
     * Given a Course object, check if given course already exists in persistent data.
     *
     * @param course - Course object
     * @return true if exists already, false otherwise
     */
    @Override
    public boolean checkForCourse(Course course) {
        // prepare a statement
        String query = "select count(*) from course where (code = ? AND name = ? AND section = ?) OR (course_id = ?)";

        // prepare response
        boolean rowExists = false;

        // auto-close PreparedStatement after it is used
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            // add arguments to SQL query
            statement.setString(1, course.getCode());
            statement.setString(2, course.getName());
            statement.setString(3, course.getSection());
            statement.setLong(4, course.getId());

            // execute statement
            try (ResultSet resultSet = statement.executeQuery()) {
                // check how many rows match the id
                int count = resultSet.getInt(1);

                // if greater than 0, then confirm that course exists
                if (count > 0) {
                    rowExists = true;
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowExists;
    }

    /**
     * Given a Course object, persist in data storage.
     *
     * @param course - Course object to save
     */
    @Override
    public void saveCourse(Course course) {
        // prepare a statement
        String query = "insert into course values(NULL, ?, ?, ?);";

        // auto-close PreparedStatement after it is used
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            // add arguments to SQL query
            statement.setString(1, course.getCode());
            statement.setString(2, course.getName());
            statement.setString(3, course.getSection());

            // execute statement
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Given a list of Course codes, create Course objects from persistent storage.
     *
     * @param courseCodes - list of courseCodes
     * @return List of Course objects
     */
    @Override
    public List<Course> getCourses(List<String> courseCodes) {
        return List.of();
    }

    /**
     * Get list of all Courses saved in persistent storage.
     *
     * @return List of Course objects
     */
    @Override
    public List<Course> getCourseList() {
        // arraylist
        List<Course> courses = new ArrayList<>();

        // prepare a statement
        String query = "select * from course;";

        // auto-close PreparedStatement after it is used
        try (Statement statement = this.connection.createStatement()) {
            // execute statement
            try (ResultSet resultSet = statement.executeQuery(query)) {
                // read each row of result set
                while (resultSet.next()) {
                    // get information
                    long id = resultSet.getLong("course_id");
                    String code = resultSet.getString("code");
                    String name = resultSet.getString("name");
                    String section = resultSet.getString("section");

                    // create semester and add to list
                    courses.add(new Course(id, code, name, section));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    /**
     * Given a Slot object, check if it already exists in persistent data.
     *
     * @param slot - Slot object to check
     * @return true if exists already, false otherwise
     */
    @Override
    public boolean checkForSlot(Slot slot) {
        // prepare a statement
        String query = "select count(*) from slot where (start_time = ? AND end_time = ?) OR (slot_id = ?)";

        // prepare response
        boolean rowExists = false;

        // auto-close PreparedStatement after it is used
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            // add arguments to SQL query
            statement.setString(1, slot.getStartTime());
            statement.setString(2, slot.getEndTime());
            statement.setLong(3, slot.getId());

            // execute statement
            try (ResultSet resultSet = statement.executeQuery()) {
                // check how many rows match the id
                int count = resultSet.getInt(1);

                // if greater than 0, then confirm that slot exists
                if (count > 0) {
                    rowExists = true;
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowExists;
    }

    /**
     * Given a Slot object, save to persistent storage.
     *
     * @param slot - Slot object to save
     */
    @Override
    public void saveSlot(Slot slot) {
        // prepare a statement
        String query = "insert into slot values(NULL, ?, ?);";

        // auto-close PreparedStatement after it is used
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            // add arguments to SQL query
            statement.setString(1, slot.getStartTime());
            statement.setString(2, slot.getEndTime());

            // execute statement
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Given a list of semesterID strings, get all slots with semesterID, making sure to create
     * the appropriate parent "Semester" objects as well (getSemesters() may be useful for this).
     *
     * @param semesterIds - list of semesterIds
     * @return List of Semester objects
     */
    @Override
    public List<Semester> getSlots(List<String> semesterIds) {
        return List.of();
    }

    /**
     * Get list of Slots saved in persistent storage.
     *
     * @return List of Slot objects
     */
    @Override
    public List<Slot> getSlotList() {
        // arraylist
        List<Slot> slots = new ArrayList<>();

        // prepare a statement
        String query = "select * from slot;";

        // auto-close PreparedStatement after it is used
        try (Statement statement = this.connection.createStatement()) {
            // execute statement
            try (ResultSet resultSet = statement.executeQuery(query)) {
                // read each row of result set
                while (resultSet.next()) {
                    // get information
                    long id = resultSet.getLong("slot_id");
                    String startTime = resultSet.getString("start_time");
                    String endTime = resultSet.getString("end_time");

                    // create semester and add to list
                    slots.add(new Slot(id, startTime, endTime));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return slots;
    }

    /**
     * NEW (for version 0.6 from this point on): Check if a given Appointment already exists in persistent storage.
     *
     * @param appointment - Appointment object to check.
     * @return true if appointment already exists, false otherwise.
     */
    @Override
    public boolean checkForAppointment(Appointment appointment) {
        // prepare a statement
        String query = "select count(*) from appointment where (student = ? AND date = ? AND course_id_parent = ? AND slot_id_parent) OR (appointment_id = ?)";

        // prepare response
        boolean rowExists = false;

        // auto-close PreparedStatement after it is used
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            // add arguments to SQL query
            statement.setString(1, appointment.getStudent());
            statement.setString(2, appointment.getDate());
            statement.setLong(3, appointment.getCourse().getId());
            statement.setLong(4, appointment.getSlot().getId());
            statement.setLong(4, appointment.getId());

            // execute statement
            try (ResultSet resultSet = statement.executeQuery()) {
                // check how many rows match the id
                int count = resultSet.getInt(1);

                // if greater than 0, then confirm that appointment exists
                if (count > 0) {
                    rowExists = true;
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rowExists;
    }

    /**
     * NEW (for version 0.6): Persist a given Appointment object in storage.
     *
     * @param appointment - Appointment object to save.
     */
    @Override
    public void saveAppointment(Appointment appointment) {
        // prepare a statement
        String query = "insert into appointment values(NULL, ?, ?, ?, ?, ?, ?);";

        // auto-close PreparedStatement after it is used
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            // add arguments to SQL query
            statement.setLong(1, appointment.getCourse().getId());
            statement.setLong(2, appointment.getSlot().getId());
            statement.setString(3, appointment.getStudent());
            statement.setString(4, appointment.getDate());
            statement.setString(5, appointment.getReason());
            statement.setString(6, appointment.getComment());

            // execute statement
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete a given Appointment in storage.
     *
     * @param appointment - appointment to delete
     */
    @Override
    public void deleteAppointment(Appointment appointment) {
        // prepare a statement
        String query = "delete from appointment where appointment_id = ?;";

        // auto-close PreparedStatement after it is used
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            // add arguments to SQL query
            statement.setLong(1, appointment.getId());

            // execute statement
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * NEW (for version 0.6): Retrieve all persisted Appointment data as a List.
     *
     * @return List of Appointment objects.
     */
    @Override
    public List<Appointment> getAppointmentList() {
        // arraylist
        List<Appointment> appointments = new ArrayList<>();

        // prepare a statement
        String query = "select * from appointment left join course on appointment.course_id_parent = course.course_id left join slot on appointment.slot_id_parent = slot.slot_id;";

        // auto-close PreparedStatement after it is used
        try (Statement statement = this.connection.createStatement()) {
            // execute statement
            try (ResultSet resultSet = statement.executeQuery(query)) {
                // read each row of result set
                while (resultSet.next()) {
                    // get information
                    long id = resultSet.getLong("appointment_id");
                    String student = resultSet.getString("student");
                    String date = resultSet.getString("date");
                    String reason = resultSet.getString("reason");
                    String comment = resultSet.getString("comment");

                    // get slot information
                    long slotId = resultSet.getLong("slot_id_parent");
                    String startTime = resultSet.getString("start_time");
                    String endTime = resultSet.getString("end_time");

                    // get course information
                    long courseId = resultSet.getLong("course_id_parent");
                    String name = resultSet.getString("name");
                    String code = resultSet.getString("code");
                    String section = resultSet.getString("section");

                    Slot slot = new Slot(slotId, startTime, endTime);
                    Course course = new Course(courseId, code, name, section);

                    // create semester and add to list
                    appointments.add(new Appointment(id, course, slot, student, date, reason, comment));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    /**
     * Given an appointment, update its entry in the database.
     *
     * @param appointment - appointment to be updated
     */
    @Override
    public void updateAppointment(Appointment appointment) {
        // prepare a statement
        String query = "update appointment set course_id_parent = ?, slot_id_parent = ?, student = ?, date = ?, reason = ?, comment = ? where appointment_id = ?;";

        // auto-close PreparedStatement after it is used
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            // add parameters to prepared statement
            statement.setLong(1, appointment.getCourse().getId());
            statement.setLong(2, appointment.getSlot().getId());
            statement.setString(3, appointment.getStudent());
            statement.setString(4, appointment.getDate());
            statement.setString(5, appointment.getReason());
            statement.setString(6, appointment.getComment());
            statement.setLong(7, appointment.getId());

            // this code will execute the SQL prepared statement
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
