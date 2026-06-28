package com.link360.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TipoPromocion {

    private String tipoPromo;
    private String nombreTipo;
    private BigDecimal porcentajeMaximo;
    private String estado;
    private String usuarioCreacion;
    private LocalDateTime fechaCreacion;
    private String usuarioModificacion;
    private LocalDateTime fechaModificacion;

    public String getTipoPromo() {
        return tipoPromo;
    }

    public void setTipoPromo(String tipoPromo) {
        this.tipoPromo = tipoPromo;
    }

    public String getNombreTipo() {
        return nombreTipo;
    }

    public void setNombreTipo(String nombreTipo) {
        this.nombreTipo = nombreTipo;
    }

    public BigDecimal getPorcentajeMaximo() {
        return porcentajeMaximo;
    }

    public void setPorcentajeMaximo(BigDecimal porcentajeMaximo) {
        this.porcentajeMaximo = porcentajeMaximo;
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
