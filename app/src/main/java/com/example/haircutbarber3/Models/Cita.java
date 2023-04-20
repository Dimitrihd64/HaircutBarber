package com.example.haircutbarber3.Models;

import java.util.ArrayList;

public class Cita {
    private String nombre;
    private String hora;
    private int dia;
    private int mes;
    private int año;
    private ArrayList<String> serviciosList;

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

    public ArrayList<String> getServiciosList() {
        return serviciosList;
    }

    public void setServiciosList(ArrayList<String> serviciosList) {
        this.serviciosList = serviciosList;
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
                "nombre='" + nombre + '\'' +
                ", hora='" + hora + '\'' +
                ", dia=" + dia +
                ", mes=" + mes +
                ", año=" + año +
                ", serviciosList=" + serviciosList +
                ", precio=" + precio +
                '}';
    }

    public Cita(String nombre, String hora, int dia, int mes, int año, ArrayList<String> serviciosList, double precio) {
        this.nombre = nombre;
        this.hora = hora;
        this.dia = dia;
        this.mes = mes;
        this.año = año;
        this.serviciosList = serviciosList;
        this.precio = precio;
    }

    public Cita() {
    }
}
