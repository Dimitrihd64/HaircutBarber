package com.example.haircutbarber3.Models;

public class Cita {
    private String nombre;
    private String hora;
    private int dia;
    private int mes;
    private int año;
    private double precio;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAño() {
        return año;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "hora=" + hora +
                ", dia=" + dia +
                ", mes=" + mes +
                ", año=" + año +
                ", precio=" + precio +
                '}';
    }

    public Cita(String hora, int dia, int mes, int año, double precio) {
        this.hora = hora;
        this.dia = dia;
        this.mes = mes;
        this.año = año;
        this.precio = precio;
    }

    public Cita() {
    }
}
