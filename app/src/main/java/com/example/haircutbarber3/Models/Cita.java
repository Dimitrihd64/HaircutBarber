package com.example.haircutbarber3.Models;

import java.util.Date;

public class Cita {
    private Date fecha;


    public Cita(Date fecha) {
        this.fecha = fecha;

    }

    public Cita() {
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
