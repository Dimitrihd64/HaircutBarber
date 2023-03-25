package com.example.haircutbarber3.Models;

public class Cita {

    int Dia;
    int Mes;
    int Año;

    public Cita() {
    }

    public Cita(int dia, int mes, int año) {
        Dia = dia;
        Mes = mes;
        Año = año;
    }

    public int getDia() {
        return Dia;
    }

    public void setDia(int dia) {
        Dia = dia;
    }

    public int getMes() {
        return Mes;
    }

    public void setMes(int mes) {
        Mes = mes;
    }

    public int getAño() {
        return Año;
    }

    public void setAño(int año) {
        Año = año;
    }
}
