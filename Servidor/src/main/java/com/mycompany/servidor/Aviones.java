/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidor;

/**
 *
 * @author User
 */
public class Aviones {
    private String origen;
    private String destino;
    private String gasolinaDisponible;
    private String altitud;
    private String cantidadPasajeros;
    private String condicion;
    private String fecha;
    private String hora;

    public Aviones(String origen, String destino, String gasolinaDisponible, String altitud, String cantidadPasajeros, String condicion, String fecha, String hora) {
        this.origen = origen;
        this.destino = destino;
        this.gasolinaDisponible = gasolinaDisponible;
        this.altitud = altitud;
        this.cantidadPasajeros = cantidadPasajeros;
        this.condicion = condicion;
        this.fecha = fecha;
        this.hora = hora;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getGasolinaDisponible() {
        return gasolinaDisponible;
    }

    public void setGasolinaDisponible(String gasolinaDisponible) {
        this.gasolinaDisponible = gasolinaDisponible;
    }

    public String getAltitud() {
        return altitud;
    }

    public void setAltitud(String altitud) {
        this.altitud = altitud;
    }

    public String getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public void setCantidadPasajeros(String cantidadPasajeros) {
        this.cantidadPasajeros = cantidadPasajeros;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
    
    
}
