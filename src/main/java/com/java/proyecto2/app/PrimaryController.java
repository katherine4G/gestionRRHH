package com.java.proyecto2.app;

import com.java.proyecto2.app.FilaPlanilla;
import com.java.proyecto2.app.App;
import com.java.proyecto2.servicios.FabricaEmpleados;
import com.java.proyecto2.modelo.Salario;
import com.java.proyecto2.modelo.Practicante;
import com.java.proyecto2.modelo.Comisionista;
import com.java.proyecto2.modelo.Temporal;
import com.java.proyecto2.modelo.PorHora;
import com.java.proyecto2.modelo.Asalariado;
import com.java.proyecto2.modelo.Empleado;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class PrimaryController {

    @FXML private TableView<FilaEmpleado> tablaEmpleados;
    @FXML private TableColumn<FilaEmpleado, String> colCedula;
    @FXML private TableColumn<FilaEmpleado, String> colNombre;
    @FXML private TableColumn<FilaEmpleado, String> colTipo;
    @FXML private ComboBox<String> cbTipoEmpleado;

    @FXML private TextField txtSalario;
    @FXML private TextField txtTarifaHora;
    @FXML private TextField txtHoras;
    @FXML private TextField txtTarifaDiaria;
    @FXML private TextField txtDias;
    @FXML private TextField txtBase;
    @FXML private TextField txtPorcentaje;
    @FXML private TextField txtVentas;
    @FXML private TextField txtApoyo;
    @FXML private TextField txtCedula;
    @FXML private TextField txtNombre;
    @FXML private Label lblSinArchivo;
    @FXML private Button btnGuardarEmpleado;

    
    
    private final FabricaEmpleados fabrica = new FabricaEmpleados();
    private List<FilaPlanilla> ultimaPlanilla;
    private ResumenPlanilla ultimoResumen;

    @FXML
    private void initialize() {
        // Desactivar el formulario al inicio
        
        btnGuardarEmpleado.setDisable(true);
        cbTipoEmpleado.setDisable(true);
        txtCedula.setDisable(true);
        txtNombre.setDisable(true);

        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedula"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

         lblSinArchivo.setVisible(true);
        tablaEmpleados.setVisible(false);

        // Opciones de tipo de empleado
        cbTipoEmpleado.getItems().addAll("Asalariado", "PorHora", "Temporal", "Comisionista", "Practicante");

        cbTipoEmpleado.setOnAction(e -> actualizarCampos());
    }

    @FXML
    private void guardarEmpleado() throws IOException {
        try {
            String cedula = txtCedula.getText().trim();
            String nombre = txtNombre.getText().trim();
            String tipo = cbTipoEmpleado.getValue();

            if (cedula.isEmpty() || nombre.isEmpty() || tipo == null) {
                showError("Debe completar cédula, nombre y seleccionar tipo.");
                return;
            }

            switch (tipo) {
                case "Asalariado": {
                    double salarioMensual = Double.parseDouble(txtSalario.getText().trim());
                    Salario s = new Salario(0.0, 0, 0);
                    Asalariado a = new Asalariado(salarioMensual, s, cedula, nombre);
                    fabrica.agregar(a);
                    break;
                }
                case "PorHora": {
                    double tarifaHora = Double.parseDouble(txtTarifaHora.getText().trim());
                    int horas = Integer.parseInt(txtHoras.getText().trim());
                    Salario s = new Salario(tarifaHora, 0, horas);
                    PorHora ph = new PorHora(horas, s, cedula, nombre, null);
                    fabrica.agregar(ph);
                    break;
                }
                case "Temporal": {
                    double tarifaDiaria = Double.parseDouble(txtTarifaDiaria.getText().trim());
                    int dias = Integer.parseInt(txtDias.getText().trim());
                    Salario s = new Salario(tarifaDiaria, dias, 0);
                    Temporal t = new Temporal(s, cedula, nombre, null);
                    fabrica.agregar(t);
                    break;
                }
                case "Comisionista": {
                    double base = Double.parseDouble(txtBase.getText().trim());
                    double porcentaje = Double.parseDouble(txtPorcentaje.getText().trim());
                    double ventas = Double.parseDouble(txtVentas.getText().trim());
                    Salario s = new Salario(base, 0, 0);
                    Comisionista c = new Comisionista(s, cedula, nombre, porcentaje, ventas, null);
                    fabrica.agregar(c);
                    break;
                }
                case "Practicante": {
                    double apoyo = Double.parseDouble(txtApoyo.getText().trim());
                    Practicante p = new Practicante(cedula, nombre, apoyo);
                    fabrica.agregar(p);
                    break;
                }
            }

            mostrarEmpleadosEnTabla();
            fabrica.guardarEmpleadosCsv(java.nio.file.Paths.get("data/empleados.csv"));


            // limpiar
            txtCedula.clear();
            txtNombre.clear();
            txtSalario.clear();
            txtTarifaHora.clear();
            txtHoras.clear();
            txtTarifaDiaria.clear();
            txtDias.clear();
            txtBase.clear();
            txtPorcentaje.clear();
            txtVentas.clear();
            txtApoyo.clear();
            cbTipoEmpleado.setValue(null);
            actualizarCampos();

            showInfo("Empleado agregado y guardado en empleados.csv.");
        } catch (NumberFormatException e) {
            showError("Error al guardar empleado: " + e.getMessage());
        }
    }
    @FXML
    private void cargarEmpleados() {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Seleccionar empleados.csv");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            File file = fc.showOpenDialog(null);

            if (file != null) {
                fabrica.cargarEmpleadosCsv(file.toPath());
                mostrarEmpleadosEnTabla();
                showInfo("Empleados cargados correctamente desde " + file.getName());
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Información");
            alert.setHeaderText("Opciones disponibles");

            Label msg = new Label("Al archivo empleados.csv, puede aplicarle filtros de asistencia o ventas antes de generar la planilla.");
            msg.setWrapText(true);
            msg.setMaxWidth(400); // Ajusta ancho máximo para forzar el salto de línea

            alert.getDialogPane().setContent(msg);
            alert.showAndWait();

            // Habilitar formulario
            btnGuardarEmpleado.setDisable(false);
            cbTipoEmpleado.setDisable(false);
            txtCedula.setDisable(false);
            txtNombre.setDisable(false);
        } catch (Exception e) {
            showError("Error cargando empleados: " + e.getMessage());
        }
    }

    private void mostrarEmpleadosEnTabla() {
        tablaEmpleados.getItems().clear();

        for (Empleado e : fabrica.listarEmpleados()) {
            tablaEmpleados.getItems().add(new FilaEmpleado(
                    e.getCedula(), e.getNombre(), e.getClass().getSimpleName()
            ));
        }
        for (Practicante p : fabrica.listarPracticantes()) {
            tablaEmpleados.getItems().add(new FilaEmpleado(
                    p.getCedula(), p.getNombre(), "Practicante"
            ));
        }

        // 📌 Mostrar mensaje si no hay datos
        boolean vacio = tablaEmpleados.getItems().isEmpty();
        lblSinArchivo.setVisible(vacio);
        tablaEmpleados.setVisible(!vacio);
    }

    @FXML
    private void switchToSecondary() {
        try {
            App.setRoot("secondary");
        } catch (Exception e) {
            showError("No se pudo cambiar de pantalla: " + e.getMessage());
        }
    }

    @FXML
    private void switchToResumen() {
        try {
            App.setRoot("resumen");
        } catch (Exception e) {
            showError("No se pudo cambiar de pantalla: " + e.getMessage());
        }
    }

    @FXML
    private void salir() {
        System.exit(0);
    }

    @FXML
    private void mostrarAcercaDe() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Acerca de");
        alert.setHeaderText("Sistema de Nómina - RRHH");
        alert.setContentText("Proyecto POO - Herencia y Polimorfismo\n"
                + "Universidad Nacional, Campus Coto\n"
                + "Autores:\nKatherine Guatemala.\nIgnacio Artavía.\nJoseph Quirós.");
        alert.showAndWait();
     }
   
    @FXML
    private void generarPlanilla() {
        try {
            ultimaPlanilla = fabrica.generarPlanilla();
            ultimoResumen = fabrica.resumirPlanilla(ultimaPlanilla);

            App.setUltimaPlanilla(ultimaPlanilla);
            App.setUltimoResumen(ultimoResumen);

            java.nio.file.Path ruta = java.nio.file.Paths.get("out/planilla_quincena.csv");
            fabrica.exportarPlanillaCsv(ruta, ultimaPlanilla);

            showInfo("Planilla generada con " + ultimaPlanilla.size() + " registros.\n"
                    + "Archivo guardado en:\n" + ruta.toAbsolutePath());

        } catch (Exception e) {
            showError("Error al generar planilla: " + e.getMessage());
        }
    }

    @FXML
    private void cargarAsistencia() {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Seleccionar asistencia.csv");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            File file = fc.showOpenDialog(null);

            if (file != null) {
                fabrica.aplicarAsistencia(file.toPath());
                showInfo("Asistencia aplicada correctamente desde " + file.getName());
                mostrarEmpleadosEnTabla(); // refresca si quieres ver cambios
            }
        } catch (Exception e) {
            showError("Error cargando asistencia: " + e.getMessage());
        }
    }

    @FXML
    private void cargarVentas() {
        try {
            FileChooser fc = new FileChooser();
            fc.setTitle("Seleccionar ventas.csv");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            File file = fc.showOpenDialog(null);

            if (file != null) {
                fabrica.aplicarVentas(file.toPath());
                showInfo("Ventas aplicadas correctamente desde " + file.getName());
                mostrarEmpleadosEnTabla();
            }
        } catch (Exception e) {
            showError("Error cargando ventas: " + e.getMessage());
        }
    }

    private void actualizarCampos() {
      // Ocultar todos
      txtSalario.setVisible(false); txtSalario.setManaged(false);
      txtTarifaHora.setVisible(false); txtTarifaHora.setManaged(false);
      txtHoras.setVisible(false); txtHoras.setManaged(false);
      txtTarifaDiaria.setVisible(false); txtTarifaDiaria.setManaged(false);
      txtDias.setVisible(false); txtDias.setManaged(false);
      txtBase.setVisible(false); txtBase.setManaged(false);
      txtPorcentaje.setVisible(false); txtPorcentaje.setManaged(false);
      txtVentas.setVisible(false); txtVentas.setManaged(false);
      txtApoyo.setVisible(false); txtApoyo.setManaged(false);

      String tipo = cbTipoEmpleado.getValue();
      if (tipo == null) return;

      switch (tipo) {
          case "Asalariado":
              txtSalario.setVisible(true); txtSalario.setManaged(true);
              break;
          case "PorHora":
              txtTarifaHora.setVisible(true); txtTarifaHora.setManaged(true);
              txtHoras.setVisible(true); txtHoras.setManaged(true);
              break;
          case "Temporal":
              txtTarifaDiaria.setVisible(true); txtTarifaDiaria.setManaged(true);
              txtDias.setVisible(true); txtDias.setManaged(true);
              break;
          case "Comisionista":
              txtBase.setVisible(true); txtBase.setManaged(true);
              txtPorcentaje.setVisible(true); txtPorcentaje.setManaged(true);
              txtVentas.setVisible(true); txtVentas.setManaged(true);
              break;
          case "Practicante":
              txtApoyo.setVisible(true); txtApoyo.setManaged(true);
              break;
      }
  }

    private void showInfo(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, msg);
        alert.showAndWait();
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg);
        alert.showAndWait();
    }

    // Getters para otros controladores
    public List<FilaPlanilla> getUltimaPlanilla() {
        return ultimaPlanilla;
    }

    public ResumenPlanilla getUltimoResumen() {
        return ultimoResumen;
    }

    // Clase auxiliar para la tabla
    public static class FilaEmpleado {
        private final String cedula;
        private final String nombre;
        private final String tipo;

        public FilaEmpleado(String cedula, String nombre, String tipo) {
            this.cedula = cedula;
            this.nombre = nombre;
            this.tipo = tipo;
        }

        public String getCedula() { return cedula; }
        public String getNombre() { return nombre; }
        public String getTipo() { return tipo; }
    }
}
