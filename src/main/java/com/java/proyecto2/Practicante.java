package com.java.proyecto2;

/**
 *
 * @author katherine
 */
public final class Practicante  {
    private String cedula;
    private String nombre;
    private double ayuda;

    public Practicante(String cedula, String nombre, double ayuda) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.ayuda = ayuda;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getAyuda() {
        return ayuda;
    }

    public void setAyuda(double ayuda) {
        this.ayuda = ayuda;
    }
    
};
