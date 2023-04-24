package com.example.haircutbarber3.Models;

import java.util.ArrayList;

public class Cita {
    private String Nombre;
    private String Hora;
    private int Dia;
    private int Mes;
    private int Año;
    private ArrayList<String> Servicios;

    private double Precio;

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        this.Nombre = nombre;
    }

    public String getHora() {
        return Hora;
    }

    public void setHora(String hora) {
        this.Hora = hora;
    }

    public int getDia() {
        return Dia;
    }

    public void setDia(int dia) {
        this.Dia = dia;
    }

    public int getMes() {
        return Mes;
    }

    public void setMes(int mes) {
        this.Mes = mes;
    }

    public int getAño() {
        return Año;
    }

    public void setAño(int año) {
        this.Año = año;
    }

    public ArrayList<String> getServiciosList() {
        return Servicios;
    }

    public void setServiciosList(ArrayList<String> serviciosList) {
        this.Servicios = serviciosList;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double precio) {
        this.Precio = precio;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "nombre='" + Nombre + '\'' +
                ", hora='" + Hora + '\'' +
                ", dia=" + Dia +
                ", mes=" + Mes +
                ", año=" + Año +
                ", servicios=" + Servicios +
                ", precio=" + Precio +
                '}';
    }

    public Cita(String nombre, String hora, int dia, int mes, int año, ArrayList<String> serviciosList, double precio) {
        this.Nombre = nombre;
        this.Hora = hora;
        this.Dia = dia;
        this.Mes = mes;
        this.Año = año;
        this.Servicios = serviciosList;
        this.Precio = precio;
    }

    public Cita() {
    }
}
