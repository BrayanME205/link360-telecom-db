package com.link360.model;

import java.time.LocalDateTime;

public class PuntosFidelizacion {

    private String cedula;
    private Integer numFactura;
    private Integer puntosAsignados;
    private Integer saldoActual;
    private String estado;
    private String usuarioCreacion;
    private LocalDateTime fechaCreacion;
    private String usuarioModificacion;
    private LocalDateTime fechaModificacion;
    private String nombreCliente;

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public Integer getNumFactura() {
        return numFactura;
    }

    public void setNumFactura(Integer numFactura) {
        this.numFactura = numFactura;
    }

    public Integer getPuntosAsignados() {
        return puntosAsignados;
    }

    public void setPuntosAsignados(Integer puntosAsignados) {
        this.puntosAsignados = puntosAsignados;
    }

    public Integer getSaldoActual() {
        return saldoActual;
    }

    public void setSaldoActual(Integer saldoActual) {
        this.saldoActual = saldoActual;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuarioCreacion() {
        return usuarioCreacion;
    }

    public void setUsuarioCreacion(String u) {
        this.usuarioCreacion = u;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime f) {
        this.fechaCreacion = f;
    }

    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }

    public void setUsuarioModificacion(String u) {
        this.usuarioModificacion = u;
    }

    public LocalDateTime getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(LocalDateTime f) {
        this.fechaModificacion = f;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String n) {
        this.nombreCliente = n;
    }
}
