package s25.cs151.application.views.layout;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import s25.cs151.application.views.components.reusable.SideMenu;

public class RootLayout extends StackPane {

    private final StringProperty pageTitle = new SimpleStringProperty();
    private final Label pageTitleLabel = new Label();
    private final StackPane centerContent = new StackPane();

    public RootLayout() {
        initializeLayout();
    }

    private void initializeLayout() {
        // Sidebar
        SideMenu sideMenu = new SideMenu();
        sideMenu.initialize();
        Node sideMenuLayout = sideMenu.getLayout();

        // Title bar
        pageTitleLabel.textProperty().bind(pageTitle);
        pageTitleLabel.getStyleClass().add("page-title");

        VBox titleBar = new VBox(pageTitleLabel);
        titleBar.setPadding(new Insets(10));

        BorderPane mainArea = new BorderPane();
        mainArea.setTop(titleBar);
        mainArea.setCenter(centerContent);

        HBox mainLayout = new HBox(sideMenuLayout, mainArea);
        HBox.setHgrow(mainArea, Priority.ALWAYS);

        this.getChildren().add(mainLayout);
    }

    public void setCenterContent(Node content) {
        centerContent.getChildren().setAll(content);
    }

    public StringProperty pageTitleProperty() {
        return pageTitle;
    }
}
