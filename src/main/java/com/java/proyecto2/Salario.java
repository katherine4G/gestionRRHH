package com.java.proyecto2;

/**
 *
 * @author katherine
 */
public class Salario {
    double tarifa;
    int dias;
    int horas;

    public Salario(double tarifa, int dias, int horas) {
        this.tarifa = tarifa;
        this.dias = dias;
        this.horas = horas;
    }

    public double getTarifa() {
        return tarifa;
    }

    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }

    public int getDias() {
        return dias;
    }

    public void setDias(int dias) {
        this.dias = dias;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }
    public void reporteSalario() {
        
    }
    
};
