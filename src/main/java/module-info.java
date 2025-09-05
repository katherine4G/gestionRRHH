module com.java.proyecto2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.java.proyecto2 to javafx.fxml;
    exports com.java.proyecto2;
}
