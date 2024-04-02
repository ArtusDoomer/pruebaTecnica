package com.front.demo;

public class CurrencyInfo {
    private String nombre;
    private String fecha;
    private double valor;

    public CurrencyInfo(String nombre, String fecha, double valorMoneda) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.valor = valor;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
