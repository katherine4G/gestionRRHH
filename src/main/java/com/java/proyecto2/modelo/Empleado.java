package com.java.proyecto2.modelo;

public abstract class Empleado {
    
    Salario salario;
    
    private final String cedula;

    private final String nombre;

    public Empleado(Salario salario, String cedula, String nombre) {
        this.salario = salario;
        this.cedula = cedula;
        this.nombre = nombre;
    }

    public Salario getSalario() {
        return salario;
    }

    public void setSalario(Salario salario) {
        this.salario = salario;
    }


    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public abstract double salarioQuincena();
};
