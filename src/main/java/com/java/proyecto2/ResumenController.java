package com.java.proyecto2;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableCell;
import javafx.collections.FXCollections;

import java.util.Map;

public class ResumenController {

    @FXML private TableView<TotalPorTipo> tablaResumen;
    @FXML private TableColumn<TotalPorTipo, String> colTipo;
    @FXML private TableColumn<TotalPorTipo, Double> colTotal;
    @FXML private Label lblTotalGeneral;

    @FXML
    private void initialize() {
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        colTotal.setCellFactory(tc -> new TableCell<>() {
            @Override
            protected void updateItem(Double value, boolean empty) {
                super.updateItem(value, empty);
                setText(empty || value == null ? null : String.format("₡ %.2f", value));
            }
        });

        // 👉 Usar resumen global
        if (App.getUltimoResumen() != null) {
            ResumenPlanilla resumen = App.getUltimoResumen();
            tablaResumen.setItems(FXCollections.observableArrayList(
                    resumen.totalPorTipo.entrySet().stream()
                            .map(entry -> new TotalPorTipo(entry.getKey(), entry.getValue()))
                            .collect(java.util.stream.Collectors.toList())
            ));
            lblTotalGeneral.setText("Total General: ₡ " + String.format("%.2f", resumen.totalGeneral));
        }
    }


    @FXML
    private void volver() throws Exception {
        App.setRoot("primary");
    }

    public static class TotalPorTipo {
        private final String tipo;
        private final Double total;

        public TotalPorTipo(String tipo, Double total) {
            this.tipo = tipo;
            this.total = total;
        }

        public String getTipo() { return tipo; }
        public Double getTotal() { return total; }
    }
}
