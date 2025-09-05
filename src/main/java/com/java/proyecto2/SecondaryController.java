package com.java.proyecto2;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;

public class SecondaryController {

    @FXML private TableView<FilaPlanilla> tablaPlanilla;
    @FXML private TableColumn<FilaPlanilla, String> colCedula;
    @FXML private TableColumn<FilaPlanilla, String> colNombre;
    @FXML private TableColumn<FilaPlanilla, String> colTipo;
    @FXML private TableColumn<FilaPlanilla, Double> colSalario;
    @FXML private TableColumn<FilaPlanilla, Double> colBono;
    @FXML private TableColumn<FilaPlanilla, Double> colTotal;

    @FXML
    private void initialize() {
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colSalario.setCellValueFactory(new PropertyValueFactory<>("salarioQuincena"));
        colBono.setCellValueFactory(new PropertyValueFactory<>("bono"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalAPagar"));

        // 👉 Usar planilla global
        if (App.getUltimaPlanilla() != null) {
            tablaPlanilla.getItems().addAll(App.getUltimaPlanilla());
        }
    }


    @FXML
    private void volver() throws Exception {
        App.setRoot("primary");
    }
}
