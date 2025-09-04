package com.java.proyecto2;

/**
 *
 * @author katherine
 */
public final class Comisionista  extends Empleado implements Bonificable {
    private double porcentaje_ventas;
    private double total_ventas; 
    private Incentivo incentivo;
    public Comisionista(Salario salario, String cedula, String nombre, double p_porcentaje_ventas, double p_total_ventas) {
        super(salario, cedula, nombre);
        this.porcentaje_ventas = p_porcentaje_ventas;
        this.total_ventas = p_total_ventas;
    }

    @Override
    public double salarioQuincena() {
       return salario.getTarifa() + (this.porcentaje_ventas * this.total_ventas);
    }

    @Override
    public double bono() {
        return (incentivo == null) ? 0.0 : incentivo.calcular(this);
    }

    public void setIncentivo(Incentivo incentivo) { this.incentivo = incentivo; }
    
};
