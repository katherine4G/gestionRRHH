module com.java.proyecto2 {
    requires javafx.controls;
    requires javafx.fxml;
    
    exports com.java.proyecto2.app;
    exports com.java.proyecto2.modelo;
    exports com.java.proyecto2.servicios;

    opens com.java.proyecto2.app to javafx.fxml;
}
