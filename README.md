#  Proyecto Programado – POO: Herencia y Polimorfismo  
**Universidad Nacional (UNA) – Campus Coto**  
**Curso:** Paradigmas de Programación  
**Docente:** M. Sc. José Pablo Noguera Espinoza  

---

##  Integrantes
- Katherine Guatemala  
- Ignacio Artavía  
- Joseph Quirós  

---

## Objetivo
Desarrollar un sistema de nómina simplificado para Recursos Humanos aplicando principios de Programación Orientada a Objetos: **herencia, polimorfismo, clases abstractas e interfaces**.  

- Este proyecto aplica principios de **herencia, polimorfismo, OCP y LSP**.  
- Usa `FabricaEmpleados` como **Factory**, y `Incentivo` como **Strategy** para bonificaciones.  
- Se puede extender fácilmente para nuevos tipos de empleados o incentivos.  


El sistema permite:  
- Gestionar distintos tipos de empleados (Asalariado, PorHora, Temporal, Comisionista, Practicante).  
- Aplicar filtros de **asistencia** y **ventas** para recalcular horas, días o comisiones.  
- Generar **planilla quincenal** detallada y **resumen por tipo de empleado**.  
- Exportar resultados a **CSV**.  


---

## 📂 Estructura del Proyecto
```
Proyecto2/
├── src/main/java/com/java/proyecto2/
│   ├── app/
│   │   ├── App.java
│   │   ├── PrimaryController.java
│   │   ├── SecondaryController.java
│   │   └── ResumenController.java
│   │
│   ├── modelo/
│   │   ├── Empleado.java
│   │   ├── Asalariado.java
│   │   ├── PorHora.java
│   │   ├── Temporal.java
│   │   ├── Comisionista.java
│   │   ├── Practicante.java
│   │   ├── Bonificable.java
│   │   ├── Incentivo.java
│   │   ├── IncentivoDesempeno.java
│   │   └── Salario.java
│   │
│   ├── servicios/
│       ├── FabricaEmpleados.java
│       ├── FilaPlanilla.java
│       └── ResumenPlanilla.java
│   
├── src/main/resources/com/java/proyecto2/
│   ├── primary.fxml
│   ├── secondary.fxml
│   └── resumen.fxml
│
├── data/
│   ├── empleados.csv
│   ├── asistencia.csv
│   └── ventas.csv
│
├── out/
│   └── planilla_quincena.csv
│
├── README.md
└── Diseño_POOUML.pdf

```

---

##  Requisitos
- **Java 11+** (probado en JDK 21).  
- **Maven** 3.6+.  
- Dependencias:  
  - JavaFX (controls, fxml).  

---

## Ejecución
Desde la raíz del proyecto:  

```bash
mvn clean javafx:run
```

Esto abrirá la interfaz gráfica de la aplicación.

---

## 🖥️ Uso de la aplicación
1. **Cargar empleados**  
   - Desde el menú **Archivo → Cargar empleados (CSV)**.  
   - El archivo `empleados.csv` debe estar en carpeta `data/`.  

   Ejemplo (`empleados.csv`):
   ```
   ASALARIADO;101;Ana Perez;1200.00
   PORHORAS;102;Luis Gomez;10.00;40
   TEMPORAL;103;Maria Ruiz;50.00;10
   COMISIONISTA;104;Carlos Lopez;300.00;0.0500;5000.00
   PRACTICANTE;105;Jose Soto;200.00
   ```

2. **Aplicar filtros opcionales**  
   - **Asistencia (`asistencia.csv`)** → actualiza horas (PorHora) y días (Temporal).  
   - **Ventas (`ventas.csv`)** → actualiza porcentaje y ventas (Comisionista).  

   Ejemplo (`asistencia.csv`):
   ```
   PORHORAS;102;45
   TEMPORAL;103;12
   ```

   Ejemplo (`ventas.csv`):
   ```
   COMISIONISTA;104;0.07;8000.00
   ```

3. **Generar planilla**  
   - Botón **Generar Planilla** (pantalla principal).  
   - Se crea `out/planilla_quincena.csv` con todos los empleados, quincena, bono y total a pagar.  

4. **Ver reportes en pantalla**  
   - **Planilla detallada** → menú Planilla → Ver planilla detallada.  
   - **Resumen por tipo** → menú Planilla → Ver resumen de planilla.  

---

## Ejemplo de salida (`planilla_quincena.csv`)
```
cedula;nombre;tipo;salarioQuincena;bono;totalAPagar
101;Ana Perez;ASALARIADO;600.00;600.00;1200.00
102;Luis Gomez;PORHORA;450.00;0.00;450.00
103;Maria Ruiz;TEMPORAL;600.00;0.00;600.00
104;Carlos Lopez;COMISIONISTA;860.00;0.00;860.00
105;Jose Soto;PRACTICANTE;200.00;0.00;200.00
```

---
