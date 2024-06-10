module project.monsterinc {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    opens project.monsterinc to javafx.fxml;
    exports project.monsterinc;
    exports project.monsterinc.entities;
    opens project.monsterinc.entities to javafx.fxml;
}