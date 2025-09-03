/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.java.proyecto2;

/**
 *
 * @author katherine
 */
public class Salario {
    double tarifa;
    int días;
    int horas;

    public Salario(double tarifa, int días, int horas) {
        this.tarifa = tarifa;
        this.días = días;
        this.horas = horas;
    }

    public double getTarifa() {
        return tarifa;
    }

    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }

    public int getDías() {
        return días;
    }

    public void setDías(int días) {
        this.días = días;
    }

    public int getHoras() {
        return horas;
    }

    public void setHoras(int horas) {
        this.horas = horas;
    }
    public void reporteSalario() {
        
    }
    
}
