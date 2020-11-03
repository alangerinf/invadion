package com.bitlicon.purolator.entities;

/**
 * Created by dianewalls on 06/07/2015.
 */
public class Resumen {

    private double TotalPurolator;
    private double TotalFiltech;
    private double Total;

    public double getTotalPurolator() {
        return TotalPurolator;
    }

    public void setTotalPurolator(double totalPurolator) {
        TotalPurolator = totalPurolator;
    }

    public double getTotal() {
        return Total;
    }

    public void setTotal(double total) {
        Total = total;
    }

    public double getTotalFiltech() {
        return TotalFiltech;
    }

    public void setTotalFiltech(double totalFiltech) {
        TotalFiltech = totalFiltech;
    }
}
