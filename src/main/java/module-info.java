module com.java.proyecto2 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.java.proyecto2 to javafx.fxml;
    exports com.java.proyecto2;
}
