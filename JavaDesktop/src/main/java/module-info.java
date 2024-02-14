module com.example.workshop6 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.workshop6 to javafx.fxml;
    exports com.example.workshop6;
}