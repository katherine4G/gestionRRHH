/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.java.proyecto2;

import java.util.Map;

/**
 *
 * @author isaacdesktop
 */
public class ResumenPlanilla {
    public final Map<String, Double> totalPorTipo;
    public final double totalGeneral;

    public ResumenPlanilla(Map<String, Double> totalPorTipo, double totalGeneral) {
        this.totalPorTipo = totalPorTipo;
        this.totalGeneral = totalGeneral;
    }
}
