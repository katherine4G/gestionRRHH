/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.java.proyecto2;

/**
 *
 * @author isaacdesktop
 */
public class FilaPlanilla {
    public final String cedula;
    public final String nombre;
    public final String tipo;
    public final double salarioQuincena;
    public final double bono;
    public final double totalAPagar;

    public FilaPlanilla(String cedula, String nombre, String tipo,
                        double salarioQuincena, double bono) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.tipo = tipo;
        this.salarioQuincena = salarioQuincena;
        this.bono = bono;
        this.totalAPagar = salarioQuincena + bono;
    }
}
