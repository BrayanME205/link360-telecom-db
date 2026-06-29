package com.link360.model;

import java.time.LocalDateTime;

public class ConsumoVoz {

    private Integer codConsumo;
    private String descripcion;
    private String numDestino;
    private Integer duracion;
    private String paisDestino;
    private String estado;
    private String usuarioCreacion;
    private LocalDateTime fechaCreacion;
    private String usuarioModificacion;
    private LocalDateTime fechaModificacion;

    public Integer getCodConsumo() {
        return codConsumo;
    }

    public void setCodConsumo(Integer codConsumo) {
        this.codConsumo = codConsumo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNumDestino() {
        return numDestino;
    }

    public void setNumDestino(String numDestino) {
        this.numDestino = numDestino;
    }

    public Integer getDuracion() {
        return duracion;
    }

    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    public String getPaisDestino() {
        return paisDestino;
    }

    public void setPaisDestino(String paisDestino) {
        this.paisDestino = paisDestino;
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
