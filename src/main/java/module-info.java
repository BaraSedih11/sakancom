module com.example.sakankom {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires MaterialFX;
    requires java.sql;
    requires com.oracle.database.jdbc;

    exports com.example.sakankom;
    opens com.example.sakankom to javafx.fxml;

}