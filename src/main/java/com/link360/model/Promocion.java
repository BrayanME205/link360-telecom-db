package com.link360.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Promocion {

    private Integer codPromocion;
    private String tipoPromo;
    private String nombre;
    private String descripcion;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private BigDecimal porcDescuento;
    private String estado;
    private String usuarioCreacion;
    private LocalDateTime fechaCreacion;
    private String usuarioModificacion;
    private LocalDateTime fechaModificacion;

    public Integer getCodPromocion() {
        return codPromocion;
    }

    public void setCodPromocion(Integer codPromocion) {
        this.codPromocion = codPromocion;
    }

    public String getTipoPromo() {
        return tipoPromo;
    }

    public void setTipoPromo(String tipoPromo) {
        this.tipoPromo = tipoPromo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public BigDecimal getPorcDescuento() {
        return porcDescuento;
    }

    public void setPorcDescuento(BigDecimal porcDescuento) {
        this.porcDescuento = porcDescuento;
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
