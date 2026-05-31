# Name of application: [TBD]
# Version: 0.8

# who did what:
1. Yejoong (Paul) Kim [Team Lead] - defined testing and validation methods to implement, ensured that MVC pattern was followed, wrote method for updating existing appointment, in addition to team lead responsibilities
2. Aekn Admal - implemented functionality for editing an existing appointment, added enums to the code regarding form state
3. Geeta Renavikar - wrote up about two examples of polymorphism: DataStorageHandler (and SQLiteStorageHandler) and Page (and HomePage, etc.)
4. Phuc Huynh - wrote numerous validation methods to ensure that arguments passed to constructors and other methods throw IllegalArgumentExceptions with specific error messages


# Use of Polymorphism in our Project

There are two noteworthy examples of polymorphism in our code:

## PageHandler, Page (interface), and implementations/subclasses of Page (i.e. HomePage, SemesterDataPage, etc.)

Take, for instance, the following code, where PageHandler switches to a specific Page:

```
private Map<String, Page> pageMap;

public void addPage(Page page) {
    pageMap.put(page.getPageId(), page);
}

public void switchToPage(String pageId) {
    Page page = pageMap.get(pageId);
    if (page != null) {
        // Calls whichever implementation you registered,
        // e.g. HomePage.initializeContent() / getContent()
        rootLayout.setCenterContent(page.getContent());
        rootLayout.pageTitleProperty().set(page.getPageTitle());
    }
}
```

Why it’s polymorphism:
PageHandler only knows about the Page interface. At runtime, it can hold a HomePage, a SemesterPage, whatever - all under the same Page type. When you call page.getContent() or page.getPageTitle(), Java dispatches to the concrete class’s override.

## DataStorageHandler (interface) and SQLiteStorageHandler (implementation, subclass)

Take, for instance, the following code, where SlotDataPageController makes use of the DataStorageHandler interface:

```
public class SlotDataPageController extends AbstractDataPageController<Slot> {
    private final DataStorageHandler storageHandler;

    public SlotDataPageController() {
        // We code to the interface, but get the concrete SQLite handler:
        this.storageHandler = SQLiteStorageHandler.getStorageHandlerInstance();
        loadPersistedItems();
    }

    @Override
    protected void loadPersistedItems() {
        // Polymorphic dispatch: at runtime this calls
        // SQLiteStorageHandler.getSlotList()
        sourceItems.setAll(storageHandler.getSlotList());
    }

    @Override
    protected void persistItem(Slot slot) {
        storageHandler.saveSlot(slot);  // dispatches to the SQLite version
    }
}
```

Why it’s polymorphism:
storageHandler is declared as the DataStorageHandler interface, but at runtime it points to the SQLiteStorageHandler singleton. All calls like saveSlot(...), getSlotList(), etc., invoke the concrete, database-backed implementation. You could swap in a CSVStorageHandler (previous implementation of data storage, but now deleted) or a mock stub for testing without touching the controller’s code.


# Dependencies

* SQLite JDBC Driver (https://github.com/xerial/sqlite-jdbc)
* JavaFX
* JUnit5

# Any other instruction that users need to know:

## Navigation Instructions:

-Use the navigation buttons on the left side (i.e. "Schedule", "Semester", "Course") to switch between pages
-Click on 'Home' to return to the main screen


## Creating Semester Office Hours:

-Navigate to "Semester"
-Create a new semester by clicking the "Add" button. It should open a panel to the right.
-Fill out each form field as required. To clear the form, press "Clear".
-Press "Save" - it should give you a notification on top of the form saying whether the save was successful.
-To close the form, press "Close".


## Creating Course:

-Navigate to "Course"
-Create a new course by clicking the "Add" button
-Fill out each form field as required. To clear the form, press "Clear".
-Press "Save" - it should give you a notification on top of the form saying whether the save was successful.
-To close the form, press "Close".


## Creating Slots:

-Navigate to "Slots"
-Create a new slot by clicking the "Add" button
-Fill out each form field as required. To clear the form, press "Clear".
-Press "Save" - it should give you a notification on top of the form saying whether the save was successful.
-To close the form, press "Close".


## Creating Appointments (or Office Hours Schedules)

-Navigate to "Schedule"
-Create a new Appointment (or Office Hours Schedule) by clicking the "Add" button
-Fill out each form field as required. To clear the form, press "Clear".
-Press "Save" - it should give you a notification on top of the form saying whether the save was successful.
-To close the form, press "Close".


## Searching Appointments (or Office Hours Schedules)
-Navigate to "Schedule"
-In the search bar, type a student's name. The table should update accordingly.
-To get all the appointments back, clear the search bar.


## Deleting Appointments (or Office Hours Schedules)

-Navigate to "Schedule"
-Select an appointment on the table.
-Next to the search bar, click "Delete". It should prompt you with a pop-up asking if you want to delete. Click "OK" if you want to, "Cancel" otherwise.

## Editing Appointments (or Office Hours Schedules)

-Navigate to "Schedule"
-Select an appointment on the table.
-Next to the search bar, click "Edit" to display a form populated with the selected appointment's data.
-Edit any form field.
-Press "Clear" to revert form back to its "Add" state.
-Press "Save" - it should give you a notification on top of the form indicating the edit was successful.
-To close the form, press "Close".

## Usage Tips:

-Make sure to fill out all required fields before submitting


## Troubleshooting:

-If the application does not load correctly, try restarting it


## General Guidance:

-Save your progress frequently to avoid data loss ("Save" button)
-Ensure you have a stable internet connection when accessing online features


# References

## Java Programming
* DeGregorio, Amy. “Constants in Java: Patterns and Anti-Patterns.” Baeldung, 8 Jan. 2024, www.baeldung.com/java-constants-good-practices.
* "Interface Path." Java™ Platform Standard Ed. 8, Oracle, https://docs.oracle.com/javase/8/docs/api/java/nio/file/Path.html#relativize-java.nio.file.Path-.
* "Primitive Data Types." The Java™ Tutorials, Oracle, https://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html.
* Yazdankhah, Ahmad. "Lecture 10: OOP Fundamentals (Part 3)." YouTube, 4 Oct. 2021, youtu.be/eZL5Jxw2OnA?feature=shared.
* Yazdankhah, Ahmad. “Lecture 16: Design Patterns (Part 2).” YouTube, 27 Oct. 2021, youtu.be/-Pfj5ff440E?feature=shared.

## JavaFX Usage
* "Class FilteredList<E>." JavaFX 8, Oracle, https://docs.oracle.com/javase/8/javafx/api/javafx/collections/transformation/FilteredList.html.
* "Class TextField." JavaFX 8 , Oracle, https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/TextField.html.
* FormsFX. https://github.com/dlsc-software-consulting-gmbh/FormsFX.
* "Interface ObservableList<E>." JavaFX 8, Oracle, https://docs.oracle.com/javase/8/javafx/api/javafx/collections/ObservableList.html.
* "JavaFX ComboBox CSS Style." StackOverflow, 18 July 2016, https://stackoverflow.com/questions/38437700/javafx-combobox-css-style.
* “JavaFX CSS Reference Guide.” JavaFX CSS Reference Guide, openjfx.io/javadoc/23/javafx.graphics/javafx/scene/doc-files/cssref.html. Accessed 11 Mar. 2025.
* "JavaFX CSS Reference Guide." Oracle JavaFX 8 API Documentation, Oracle, https://docs.oracle.com/javase/8/javafx/api/javafx/scene/doc-files/cssref.html.
* "JavaFX: Quick Guide to MVCI." PragmaticCoding, 1 Aug. 2024, https://www.pragmaticcoding.ca/javafx/elements/mvci-quick.
* "Multi-MVCI Projects." PragmaticCoding, 2 Oct. 2022, https://www.pragmaticcoding.ca/javafx/multimvci
* "Predicate." JavaFX 8, Oracle, https://docs.oracle.com/javase/8/docs/api/java/util/function/Predicate.html?is-external=true.
* Redko, Alla. “Release: Javafx 2.2.” Using JavaFX UI Controls: Button | JavaFX 2 Tutorials and Documentation, 27 Aug. 2013, docs.oracle.com/javafx/2/ui_controls/button.htm.
* Rutte, M. le. "Filtering ObservableList with CheckBox in JavaFX." StackOverflow, https://stackoverflow.com/a/50709073. 5 Jun. 2018.


## JUnit5 Usage
* "JUnit5 User Guide." JUnit5 User Guide, https://junit.org/junit5/docs/current/user-guide/.
* "Introduction to the Standard Directory Layout." Introduction to the Standard Directory Layout, https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html.
* Török, Péter. "Separation of JUnit classes into special test package?." StackOverflow, 5 Mar. 2010, https://stackoverflow.com/a/2388285.

## SQL and SQLite JDBC Usage
* BackSlash. "Why JDBC connections needs to close in finally block?" StackOverflow, https://stackoverflow.com/a/32816936. 28 Sep. 2015.
* "Class Application." JavaFX 2.2, Oracle, https://docs.oracle.com/javafx/2/api/javafx/application/Application.html.
* "Class File." Java™ Platform Standard Ed. 8, Oracle, https://docs.oracle.com/javase/8/docs/api/java/io/File.html.
* "CREATE TABLE." SQLite, https://www.sqlite.org/lang_createtable.html#rowid. 26 Apr. 2025.
* "Datatypes in SQLite." SQLite, https://www.sqlite.org/datatype3.html. 27 Apr. 2022.
* "DELETE." SQLite, https://www.sqlite.org/lang_delete.html.
* "Establishing a Connection." The Java™ Tutorials, Oracle, https://docs.oracle.com/javase/tutorial/jdbc/basics/connecting.html.
* "Interface PreparedStatement." Java™ Platform Standard Ed. 8, Oracle, https://docs.oracle.com/javase/8/docs/api/java/sql/PreparedStatement.html.
* "Interface Statement." Java™ Platform Standard Ed. 8, Oracle, https://docs.oracle.com/javase/8/docs/api/java/sql/Statement.html#executeQuery-java.lang.String-.
* "JDBC Basics." The Java™ Tutorials, Oracle, https://docs.oracle.com/javase/tutorial/jdbc/basics/index.html.
* "JDBC Database Access." The Java™ Tutorials, Oracle, https://docs.oracle.com/javase/tutorial/jdbc/index.html.
* Ordous. "Is there an issue with closing our database connections in the "Finally" block of a Try statement?" Software Engineering StackExchange, https://softwareengineering.stackexchange.com/a/285842. 4 Jun. 2015.
* "Path Operations." The Java™ Tutorials, Oracle, https://docs.oracle.com/javase/tutorial/essential/io/pathOps.html#resolve.
* "Processing SQL Statements with JDBC." The Java™ Tutorials, Oracle, https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html.
* "Setting Up Tables." The Java™ Tutorials, Oracle, https://docs.oracle.com/javase/tutorial/jdbc/basics/tables.html.
* "SQL Join Left Keyword." W3Schools, Refsnes Data AS, https://www.w3schools.com/sql/sql_join_left.asp.
* "SQLite Autoincrement." SQLite, https://www.sqlite.org/autoinc.html. 22 Feb. 2024.
* "SQLite Foreign key Support." SQLite, https://sqlite.org/foreignkeys.html. 23 Jan. 2024.
* SQLite JDBC. GitHub, https://github.com/xerial/sqlite-jdbc?tab=readme-ov-file.
* starball. "Must JDBC Resultsets and Statements be closed separately although the Connection is closed afterwards?" StackOverflow, https://stackoverflow.com/a/4511266. 23 Dec. 2022.
* "UPDATE." SQLite, https://www.sqlite.org/lang_update.html.
* "UPDATE Syntax." W3Schools, Refsnes Data AS, https://www.w3schools.com/sql/sql_update.asp
* "Usage." SQLite JDBC. GitHub, https://github.com/xerial/sqlite-jdbc/blob/master/USAGE.md.
* "Using Prepared Statements." The Java™ Tutorials, Oracle, https://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html.
