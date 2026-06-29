package com.link360.model;

import java.time.LocalDateTime;

public class RedencionPuntos {

    private String cedula;
    private Integer numFactura;
    private LocalDateTime fechaRedencion;
    private Integer cantidadRedimida;
    private Integer saldoResultante;
    private String estado;
    private String usuarioCreacion;
    private LocalDateTime fechaCreacion;
    private String usuarioModificacion;
    private LocalDateTime fechaModificacion;

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

    public LocalDateTime getFechaRedencion() {
        return fechaRedencion;
    }

    public void setFechaRedencion(LocalDateTime fechaRedencion) {
        this.fechaRedencion = fechaRedencion;
    }

    public Integer getCantidadRedimida() {
        return cantidadRedimida;
    }

    public void setCantidadRedimida(Integer cantidadRedimida) {
        this.cantidadRedimida = cantidadRedimida;
    }

    public Integer getSaldoResultante() {
        return saldoResultante;
    }

    public void setSaldoResultante(Integer saldoResultante) {
        this.saldoResultante = saldoResultante;
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
}
