package com.java.proyecto2.modelo;

import com.java.proyecto2.modelo.Empleado;

public final class Asalariado extends Empleado implements Bonificable {
    private final double salarioMensual;

    public Asalariado(double salarioMensual, Salario salario, String cedula, String nombre) {
        super(salario, cedula, nombre);
        this.salarioMensual = salarioMensual;
    }


    @Override
    public double salarioQuincena() {
        return salarioMensual / 2.0;
    }

    @Override
    public double bono() {
        return salarioMensual * 0.50;
    }

};
