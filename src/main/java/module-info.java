module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;

    exports org.example.model;
    opens org.example.model to javafx.fxml;
    opens org.example to javafx.fxml;
    exports org.example;
    exports org.example.controller;
    opens org.example.controller to javafx.fxml;
}
