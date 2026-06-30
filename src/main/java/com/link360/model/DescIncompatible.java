package com.link360.model;

import java.time.LocalDateTime;

public class DescIncompatible {
    private Integer codDescuento1;
    private Integer codDescuento2;
    private String estado;
    private String usuarioCreacion;
    private LocalDateTime fechaCreacion;
    private String usuarioModificacion;
    private LocalDateTime fechaModificacion;
    private String descripcionDesc1;
    private String descripcionDesc2;

    public Integer getCodDescuento1() { return codDescuento1; }
    public void setCodDescuento1(Integer codDescuento1) { this.codDescuento1 = codDescuento1; }
    public Integer getCodDescuento2() { return codDescuento2; }
    public void setCodDescuento2(Integer codDescuento2) { this.codDescuento2 = codDescuento2; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public String getUsuarioCreacion() { return usuarioCreacion; }
    public void setUsuarioCreacion(String u) { this.usuarioCreacion = u; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime f) { this.fechaCreacion = f; }
    public String getUsuarioModificacion() { return usuarioModificacion; }
    public void setUsuarioModificacion(String u) { this.usuarioModificacion = u; }
    public LocalDateTime getFechaModificacion() { return fechaModificacion; }
    public void setFechaModificacion(LocalDateTime f) { this.fechaModificacion = f; }
    public String getDescripcionDesc1() { return descripcionDesc1; }
    public void setDescripcionDesc1(String d) { this.descripcionDesc1 = d; }
    public String getDescripcionDesc2() { return descripcionDesc2; }
    public void setDescripcionDesc2(String d) { this.descripcionDesc2 = d; }
}