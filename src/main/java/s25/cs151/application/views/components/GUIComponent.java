package s25.cs151.application.views.components;

import javafx.scene.layout.Pane;

/**
 * GUIComponent is an interface that defines a (static) reusable GUI component, such as a persistent MenuBar or
 * a persistent sidebar.
 */
public interface GUIComponent {
    /**
     * Initialize all widgets in the GUIComponent.
     */
    void initialize();

    /**
     * Get layout object, which should include the widget itself.
     */
    Pane getLayout();
}
