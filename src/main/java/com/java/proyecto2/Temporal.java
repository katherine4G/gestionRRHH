package com.java.proyecto2;

/**
 *
 * @author katherine
 */
public final class Temporal  extends Empleado implements Bonificable {
    private Incentivo incentivo;
    public Temporal(Salario salario, String cedula, String nombre) {
        super(salario, cedula, nombre);
    }
    

    @Override
    public double salarioQuincena() {
        return salario.getTarifa() * salario.getDías();
    }

    @Override
    public double bono() {
        return (incentivo == null) ? 0.0 : incentivo.calcular(this);
    }

    public void setIncentivo(Incentivo incentivo) { this.incentivo = incentivo; }
    
};
