package s25.cs151.application.views.components.tableview;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import s25.cs151.application.models.Appointment;
import s25.cs151.application.models.Course;
import java.util.List;

/**
 * A concrete table view component for displaying Course objects.
 */
public class CourseTableView extends AbstractDataTableView<Course> {

    /**
     * Constructs a CourseTableView with the provided observable list of courses.
     *
     * @param courseList - The observable list of courses to be displayed.
     */
    public CourseTableView(ObservableList<Course> courseList) {
        super(courseList);
    }

    /**
     * Creates and configures the TableView columns for displaying course code, name, and section number.
     */
    @Override
    protected void createTable() {
        final StringProperty codeTitle = new SimpleStringProperty("Course Code");

        TableColumn<Course, String> codeColumn = new TableColumn<>();
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        codeColumn.setMinWidth(85);
        codeColumn.setPrefWidth(125);
        codeColumn.setMaxWidth(125);
        codeColumn.setSortable(false);
        codeColumn.textProperty().bind(codeTitle);

        codeColumn.widthProperty().addListener((_, _, newWidth) -> {
            int threshold = 105;
            if (newWidth.doubleValue() < threshold) {
                codeTitle.set("Code");
            } else {
                codeTitle.set("Course Code");
            }
        });

        TableColumn<Course, String> nameColumn = new TableColumn<>("Course Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setMinWidth(155);
        nameColumn.setMaxWidth(400);
        nameColumn.setSortable(false);


        final StringProperty sectionTitle = new SimpleStringProperty("Section");

        TableColumn<Course, String> sectionColumn = new TableColumn<>();
        sectionColumn.setCellValueFactory(new PropertyValueFactory<>("section"));
        sectionColumn.setMinWidth(75);
        sectionColumn.setPrefWidth(75);
        sectionColumn.setMaxWidth(125);
        sectionColumn.setSortable(false);

        sectionColumn.textProperty().bind(sectionTitle);

        sectionColumn.widthProperty().addListener((_, _, newWidth) -> {
            int threshold = 125;
            if (newWidth.doubleValue() < threshold) {
                sectionTitle.set("Section");
            } else {
                sectionTitle.set("Section Number");
            }
        });

        tableView.getColumns().addAll(List.of(codeColumn, nameColumn,sectionColumn));
        //tableView.setPrefWidth(125+155+125+14.2);
        tableView.setMinWidth(125+155+75+15);

        tableView.setPlaceholder(new Label(
                "No course information to display.\n" +
                        "Click \"Add\" to get started."
        ));
    }
}
