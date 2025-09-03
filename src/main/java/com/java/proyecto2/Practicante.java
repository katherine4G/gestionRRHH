/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.java.proyecto2;

/**
 *
 * @author katherine
 */
public final class Practicante  {
    String cedula;
    String nombre;
    int ayuda;

    public Practicante(String cedula, String nombre, int ayuda) {
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

    public int getAyuda() {
        return ayuda;
    }

    public void setAyuda(int ayuda) {
        this.ayuda = ayuda;
    }
    
}
