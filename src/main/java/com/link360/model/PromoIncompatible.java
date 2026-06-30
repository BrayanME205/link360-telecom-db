package com.link360.model;

import java.time.LocalDateTime;

public class PromoIncompatible {

    private Integer codPromocion1;
    private Integer codPromocion2;
    private String estado;
    private String usuarioCreacion;
    private LocalDateTime fechaCreacion;
    private String usuarioModificacion;
    private LocalDateTime fechaModificacion;
    private String nombrePromocion1;
    private String nombrePromocion2;

    public Integer getCodPromocion1() {
        return codPromocion1;
    }

    public void setCodPromocion1(Integer codPromocion1) {
        this.codPromocion1 = codPromocion1;
    }

    public Integer getCodPromocion2() {
        return codPromocion2;
    }

    public void setCodPromocion2(Integer codPromocion2) {
        this.codPromocion2 = codPromocion2;
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

    public String getNombrePromocion1() {
        return nombrePromocion1;
    }

    public void setNombrePromocion1(String n) {
        this.nombrePromocion1 = n;
    }

    public String getNombrePromocion2() {
        return nombrePromocion2;
    }

    public void setNombrePromocion2(String n) {
        this.nombrePromocion2 = n;
    }
}
