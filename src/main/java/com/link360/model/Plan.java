package com.link360.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Plan {

    private Integer codPlan;
    private Integer codCategoria;
    private String nombre;
    private String descripcion;
    private BigDecimal cuotaMensual;
    private BigDecimal gb;
    private Integer minutos;
    private Integer mensajes;
    private BigDecimal costoExceso;
    private String estado;
    private String usuarioCreacion;
    private LocalDateTime fechaCreacion;
    private String usuarioModificacion;
    private LocalDateTime fechaModificacion;
    private String nombreCategoria;

    public Integer getCodPlan() {
        return codPlan;
    }

    public void setCodPlan(Integer codPlan) {
        this.codPlan = codPlan;
    }

    public Integer getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(Integer codCategoria) {
        this.codCategoria = codCategoria;
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

    public BigDecimal getCuotaMensual() {
        return cuotaMensual;
    }

    public void setCuotaMensual(BigDecimal cuotaMensual) {
        this.cuotaMensual = cuotaMensual;
    }

    public BigDecimal getGb() {
        return gb;
    }

    public void setGb(BigDecimal gb) {
        this.gb = gb;
    }

    public Integer getMinutos() {
        return minutos;
    }

    public void setMinutos(Integer minutos) {
        this.minutos = minutos;
    }

    public Integer getMensajes() {
        return mensajes;
    }

    public void setMensajes(Integer mensajes) {
        this.mensajes = mensajes;
    }

    public BigDecimal getCostoExceso() {
        return costoExceso;
    }

    public void setCostoExceso(BigDecimal costoExceso) {
        this.costoExceso = costoExceso;
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

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String n) {
        this.nombreCategoria = n;
    }
}
