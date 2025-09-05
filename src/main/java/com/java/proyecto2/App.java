package com.java.proyecto2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App extends Application {

    private static Stage stage;
    private static Scene scene;
    private static final Map<String, Object> controllers = new HashMap<>();

    // 👉 Variables globales para compartir datos
    private static List<FilaPlanilla> ultimaPlanilla;
    private static ResumenPlanilla ultimoResumen;

    @Override
    public void start(Stage primaryStage) throws IOException {
        stage = primaryStage;
        setRoot("primary"); // pantalla inicial
        stage.setTitle("Sistema de Nómina - RRHH");
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        scene = new Scene(loader.load());
        stage.setScene(scene);

        controllers.put(fxml, loader.getController());
    }

    public static Object getController(String name) {
        return controllers.get(name);
    }

    // 👉 Métodos para compartir la planilla y resumen
    public static void setUltimaPlanilla(List<FilaPlanilla> planilla) {
        ultimaPlanilla = planilla;
    }

    public static List<FilaPlanilla> getUltimaPlanilla() {
        return ultimaPlanilla;
    }

    public static void setUltimoResumen(ResumenPlanilla resumen) {
        ultimoResumen = resumen;
    }

    public static ResumenPlanilla getUltimoResumen() {
        return ultimoResumen;
    }

    public static void main(String[] args) {
        launch();
    }
}
