package com.example.haircutbarber3.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Cita implements Serializable {
    private String Usuario;
    private String Id;
    private String Nombre;
    private String Hora;
    private String Fecha;

    private ArrayList<String> Servicios;

    private double Precio;

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String usuario) {
        Usuario = usuario;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

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

    public String getFecha() {
        return Fecha;
    }

    public void setFecha(String fecha) {
        Fecha = fecha;
    }

    public ArrayList<String> getServicios() {
        return Servicios;
    }

    public void setServicios(ArrayList<String> servicios) {
        Servicios = servicios;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double precio) {
        this.Precio = precio;
    }

    public Cita(String usuario, String id, String nombre, String hora, String fecha, ArrayList<String> servicios, double precio) {
        Usuario = usuario;
        Id = id;
        Nombre = nombre;
        Hora = hora;
        Fecha = fecha;
        Servicios = servicios;
        Precio = precio;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "Usuario='" + Usuario + '\'' +
                ", Id='" + Id + '\'' +
                ", Nombre='" + Nombre + '\'' +
                ", Hora='" + Hora + '\'' +
                ", Fecha='" + Fecha + '\'' +
                ", Servicios=" + Servicios +
                ", Precio=" + Precio +
                '}';
    }

    public Cita() {
    }
}
