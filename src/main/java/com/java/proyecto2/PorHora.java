
package com.java.proyecto2;

/**
 *
 * @author katherine
 */
public final class PorHora  extends Empleado implements Bonificable {
    private int horas_maximas;
    private Incentivo incentivo;
    public PorHora(int p_horas_maximas, Salario salario, String cedula, String nombre) {
        super(salario, cedula, nombre);
        this.horas_maximas = p_horas_maximas;
    }


    @Override
    public double salarioQuincena() {
        int horas_trabajadas = salario.getHoras();
        int horas_pagar = (horas_trabajadas<=this.horas_maximas) ? horas_trabajadas : this.horas_maximas;
        return salario.getTarifa() * horas_pagar;
    }

        @Override
    public double bono() {
        return (incentivo == null) ? 0.0 : incentivo.calcular(this);
    }

    public void setIncentivo(Incentivo incentivo) { this.incentivo = incentivo; }
};
