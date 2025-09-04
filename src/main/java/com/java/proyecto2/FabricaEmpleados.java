package com.java.proyecto2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class FabricaEmpleados {

    private final List<Empleado> empleados = new ArrayList<>();
    private final List<Practicante> practicantes = new ArrayList<>();

    // --------------------------------------------------
    // Gestión en memoria
    // --------------------------------------------------
    public void agregar(Empleado e) { empleados.add(e); }
    public void agregar(Practicante p) { practicantes.add(p); }

    public Optional<Empleado> buscarEmpleadoPorCedula(String cedula) {
        return empleados.stream().filter(e -> e.getCedula().equals(cedula)).findFirst();
    }

    public Optional<Practicante> buscarPracticantePorCedula(String cedula) {
        return practicantes.stream().filter(p -> p.getCedula().equals(cedula)).findFirst();
    }

    public List<Empleado> listarEmpleados() { return List.copyOf(empleados); }
    public List<Practicante> listarPracticantes() { return List.copyOf(practicantes); }

    public void limpiar() {
        empleados.clear();
        practicantes.clear();
    }

    // --------------------------------------------------
    // Persistencia: empleados.csv
    // Formato:
    // ASALARIADO;cedula;nombre;salarioMensual
    // PORHORAS;cedula;nombre;tarifaHora;horasQuincena
    // TEMPORAL;cedula;nombre;tarifaDiaria;diasActivos
    // COMISIONISTA;cedula;nombre;base;porcentaje;ventasQuincena
    // PRACTICANTE;cedula;nombre;apoyoQuincena
    // --------------------------------------------------
    public void cargarEmpleadosCsv(Path ruta) throws IOException {
        limpiar();
        if (!Files.exists(ruta)) return;

        List<String> lineas = Files.readAllLines(ruta, StandardCharsets.UTF_8);
        for (String cruda : lineas) {
            String linea = cruda.trim();
            if (linea.isEmpty() || linea.startsWith("#")) continue;

            String[] cols = linea.split(";", -1);
            String tipo = cols[0].toUpperCase(Locale.ROOT);

            switch (tipo) {
                case "ASALARIADO": {
                    String cedula = cols[1];
                    String nombre = cols[2];
                    double salarioMensual = Double.parseDouble(cols[3]);
                    Salario s = new Salario(0.0, 0, 0); // no se usa en el cálculo directo
                    Asalariado a = new Asalariado(salarioMensual, s, cedula, nombre);
                    agregar(a);
                    break;
                }
                case "PORHORAS": {
                    String cedula = cols[1];
                    String nombre = cols[2];
                    double tarifaHora = Double.parseDouble(cols[3]);
                    int horas = Integer.parseInt(cols[4]);
                    Salario s = new Salario(tarifaHora, 0, horas);
                    PorHora ph = new PorHora(0,s, cedula, nombre,null);
                    agregar(ph);
                    break;
                }
                case "TEMPORAL": {
                    String cedula = cols[1];
                    String nombre = cols[2];
                    double tarifaDiaria = Double.parseDouble(cols[3]);
                    int dias = Integer.parseInt(cols[4]);
                    Salario s = new Salario(tarifaDiaria, dias, 0);
                    //Salario salario, String cedula, String nombre, Incentivo p_incentivo
                    Temporal t = new Temporal(s, cedula, nombre,null);
                    agregar(t);
                    break;
                }
                case "COMISIONISTA": {
                    String cedula = cols[1];
                    String nombre = cols[2];
                    double base = Double.parseDouble(cols[3]);
                    double porcentaje = Double.parseDouble(cols[4]); // ej. 0.05
                    double ventas = Double.parseDouble(cols[5]);
                    // Usamos Salario.tarifa como "base quincenal"
                    Salario s = new Salario(base, 0, 0);
                    // Asegúrate de tener este constructor y/o setters
                    Comisionista c = new Comisionista(s, cedula, nombre, porcentaje, ventas,null);
                    agregar(c);
                    break;
                }
                case "PRACTICANTE": {
                    String cedula = cols[1];
                    String nombre = cols[2];
                    double apoyo = Double.parseDouble(cols[3]);
                    Practicante p = new Practicante(cedula, nombre, apoyo);
                    agregar(p);
                    break;
                }
                default:
                    break;
            }
        }
    }

    public void guardarEmpleadosCsv(Path ruta) throws IOException {
        List<String> lineas = new ArrayList<>();

        // Empleados
        for (Empleado e : empleados) {
            if (e instanceof Asalariado) {
                Asalariado a = (Asalariado) e;
 
                double mensual = a.salarioQuincena() * 2.0;
                lineas.add(String.format(Locale.US,
                        "ASALARIADO;%s;%s;%.2f", e.getCedula(), e.getNombre(), mensual));
            } else if (e instanceof PorHora) {
                Salario s = e.salario;
                lineas.add(String.format(Locale.US,
                        "PORHORAS;%s;%s;%.2f;%d", e.getCedula(), e.getNombre(), s.getTarifa(), s.getHoras()));
            } else if (e instanceof Temporal) {
                Salario s = e.salario;
                lineas.add(String.format(Locale.US,
                        "TEMPORAL;%s;%s;%.2f;%d", e.getCedula(), e.getNombre(), s.getTarifa(), s.getDias()));
            } else if (e instanceof Comisionista) {
                Comisionista c = (Comisionista) e;
                Salario s = e.salario;
                // Requiere getters en Comisionista
                lineas.add(String.format(Locale.US,
                        "COMISIONISTA;%s;%s;%.2f;%.4f;%.2f",
                        e.getCedula(), e.getNombre(), s.getTarifa(), c.getPorcentaje_ventas(), c.getTotal_ventas()));
            }
        }

        // Practicantes
        for (Practicante p : practicantes) {
            lineas.add(String.format(Locale.US,
                    "PRACTICANTE;%s;%s;%.2f", p.getCedula(), p.getNombre(), p.getAyuda()));
        }

        Path padre = ruta.getParent();
        if (padre != null) Files.createDirectories(padre);
        Files.write(ruta, lineas, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    // --------------------------------------------------
    // Asistencia y Ventas (opcionales)
    // asistencia.csv:
    // PORHORAS;cedula;horasQuincena
    // TEMPORAL;cedula;diasActivos
    // --------------------------------------------------
    public void aplicarAsistencia(Path rutaAsistencia) throws IOException {
        if (!Files.exists(rutaAsistencia)) return;

        List<String> lineas = Files.readAllLines(rutaAsistencia, StandardCharsets.UTF_8);
        for (String cruda : lineas) {
            String linea = cruda.trim();
            if (linea.isEmpty() || linea.startsWith("#")) continue;

            String[] cols = linea.split(";", -1);
            String tipo = cols[0].toUpperCase(Locale.ROOT);
            String cedula = cols[1];

            if ("PORHORAS".equals(tipo)) {
                int horas = Integer.parseInt(cols[2]);
                buscarEmpleadoPorCedula(cedula)
                        .filter(e -> e instanceof PorHora)
                        .ifPresent(e -> e.salario.setHoras(horas));
            } else if ("TEMPORAL".equals(tipo)) {
                int dias = Integer.parseInt(cols[2]);
                buscarEmpleadoPorCedula(cedula)
                        .filter(e -> e instanceof Temporal)
                        .ifPresent(e -> e.salario.setDias(dias));
            }
        }
    }

    // ventas.csv:
    // COMISIONISTA;cedula;porcentaje;ventasQuincena
    public void aplicarVentas(Path rutaVentas) throws IOException {
        if (!Files.exists(rutaVentas)) return;

        List<String> lineas = Files.readAllLines(rutaVentas, StandardCharsets.UTF_8);
        for (String cruda : lineas) {
            String linea = cruda.trim();
            if (linea.isEmpty() || linea.startsWith("#")) continue;

            String[] cols = linea.split(";", -1);
            String tipo = cols[0].toUpperCase(Locale.ROOT);
            if (!"COMISIONISTA".equals(tipo)) continue;

            String cedula = cols[1];
            double porcentaje = Double.parseDouble(cols[2]);
            double ventas = Double.parseDouble(cols[3]);

            buscarEmpleadoPorCedula(cedula)
                    .filter(e -> e instanceof Comisionista)
                    .ifPresent(e -> {
                        Comisionista c = (Comisionista) e;
                        c.setPorcentaje_ventas(porcentaje);
                        c.setTotal_ventas(ventas);
                    });
        }
    }

    // --------------------------------------------------
    // Reportes
    // --------------------------------------------------
    public List<FilaPlanilla> generarPlanilla() {
        List<FilaPlanilla> filas = new ArrayList<>();

        // Empleados
        for (Empleado e : empleados) {
            String tipo = e.getClass().getSimpleName().toUpperCase(Locale.ROOT);
            double quincena = e.salarioQuincena();
            double bono = (e instanceof Bonificable)
                    ? ((Bonificable) e).bono()
                    : 0.0;
            filas.add(new FilaPlanilla(e.getCedula(), e.getNombre(), tipo, quincena, bono));
        }

        // Practicantes (no heredan de Empleado, sin bono)
        for (Practicante p : practicantes) {
            String tipo = "PRACTICANTE";
            double quincena = p.getAyuda();
            double bono = 0.0;
            filas.add(new FilaPlanilla(p.getCedula(), p.getNombre(), tipo, quincena, bono));
        }

        return filas;
    }

    public ResumenPlanilla resumirPlanilla(List<FilaPlanilla> filas) {
        Map<String, Double> totalPorTipo = filas.stream()
                .collect(Collectors.groupingBy(f -> f.tipo,
                        Collectors.summingDouble(f -> f.totalAPagar)));

        double totalGeneral = filas.stream().mapToDouble(f -> f.totalAPagar).sum();
        return new ResumenPlanilla(totalPorTipo, totalGeneral);
    }

    public void exportarPlanillaCsv(Path ruta, List<FilaPlanilla> filas) throws IOException {
        List<String> out = new ArrayList<>();
        out.add("cedula;nombre;tipo;salarioQuincena;bono;totalAPagar");
        for (FilaPlanilla f : filas) {
            out.add(String.format(Locale.US, "%s;%s;%s;%.2f;%.2f;%.2f",
                    f.cedula, f.nombre, f.tipo, f.salarioQuincena, f.bono, f.totalAPagar));
        }
        Path padre = ruta.getParent();
        if (padre != null) Files.createDirectories(padre);
        Files.write(ruta, out, StandardCharsets.UTF_8,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }
}
