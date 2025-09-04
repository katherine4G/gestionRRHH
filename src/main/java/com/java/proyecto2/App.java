package com.java.proyecto2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws IOException {
        try {
            FabricaEmpleados fabrica = new FabricaEmpleados();
            //"src/main/java/com/java/proyecto2/empleados.csv"
            System.out.println("App.class: "+ App.class.getResourceAsStream("/com/java/proyecto2/data/empleados.csv"));
            Path archivo = Paths.get("data/empleados.csv");
            System.out.println("Rutas de empleados.csv: " + archivo.toAbsolutePath());
            // 🔹 Crear algunos empleados de prueba
            Salario salarioBase = new Salario(0.0, 0, 0);

            Asalariado asalariado = new Asalariado(1200.0, salarioBase, "101", "Ana Perez");
            //int p_horas_maximas, Salario salario, String cedula, String nombre, Incentivo p_incentivo
            PorHora porHora = new PorHora(80,new Salario(10.0, 0, 40), "102", "Luis Gomez",null);
            //Salario salario, String cedula, String nombre, Incentivo p_incentivo
            Temporal temporal = new Temporal(new Salario(50.0, 10, 0), "103", "Maria Ruiz",null);
            //Salario salario, String cedula, String nombre, double p_porcentaje_ventas, double p_total_ventas, Incentivo p_incentivo
            Comisionista comisionista = new Comisionista(new Salario(300.0, 0, 0), "104", "Carlos Lopez", 0.05, 5000.0,null);
            Practicante practicante = new Practicante("105", "Jose Soto", 200);

            // 🔹 Agregar a la fábrica
            fabrica.agregar(asalariado);
            fabrica.agregar(porHora);
            fabrica.agregar(temporal);
            fabrica.agregar(comisionista);
            fabrica.agregar(practicante);

            // 🔹 Guardar en CSV

            fabrica.guardarEmpleadosCsv(archivo);
               
            System.out.println("Empleados guardados en: " + archivo.toAbsolutePath());

            // 🔹 Cargar de nuevo para verificar
            FabricaEmpleados otraFabrica = new FabricaEmpleados();
            otraFabrica.cargarEmpleadosCsv(archivo);

            var planilla = otraFabrica.generarPlanilla();
            for (FilaPlanilla f : planilla) {
                System.out.printf("%s - %s - %s - Q: %.2f - Bono: %.2f - Total: %.2f%n", f.cedula, f.nombre, f.tipo, f.salarioQuincena, f.bono, f.totalAPagar);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        launch();
    }
}

