package com.java.proyecto2.modelo;

import com.java.proyecto2.modelo.Incentivo;
import com.java.proyecto2.modelo.Empleado;

/**
 *
 * @author isaacdesktop
 */
public class IncentivoDesempeno implements Incentivo{
    private double puntaje;
    private double base; 

    public IncentivoDesempeno(double puntaje, double base) {
        this.puntaje = puntaje;
        this.base = base;
    }
    
    @Override
    public double calcular(Empleado empleado) {
        return empleado.salarioQuincena() * base * puntaje;
    }

    public double getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(double puntaje) {
        this.puntaje = puntaje;
    }

    public double getBase() {
        return base;
    }

    public void setBase(double base) {
        this.base = base;
    }
};
