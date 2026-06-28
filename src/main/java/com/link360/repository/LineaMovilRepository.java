package com.link360.repository;

import com.link360.model.LineaMovil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class LineaMovilRepository {

    private final JdbcTemplate jdbc;

    public LineaMovilRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<LineaMovil> MAP = (rs, n) -> {
        LineaMovil l = new LineaMovil();
        l.setNumeroTelefono(rs.getString("NumeroTelefono"));
        l.setCedula(rs.getString("Cedula"));
        l.setTipoLinea(rs.getString("TipoLinea"));
        l.setTecnologia(rs.getString("Tecnologia"));
        l.setFechaActivacion(rs.getDate("FechaActivacion") != null ? rs.getDate("FechaActivacion").toLocalDate() : null);
        l.setEstadoLinea(rs.getString("EstadoLinea"));
        l.setTipoSIM(rs.getString("TipoSIM"));
        l.setEstado(rs.getString("Estado"));
        l.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
        l.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
        l.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
        l.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
        try {
            l.setNombreCliente(rs.getString("NombreCliente"));
        } catch (Exception ignored) {
        }
        return l;
    };

    public List<LineaMovil> findAll() {
        return jdbc.query(
                "SELECT l.*, c.Nombre + ' ' + c.PrimerApellido AS NombreCliente "
                + "FROM LINEAMOVIL l INNER JOIN CLIENTE c ON l.Cedula = c.Cedula "
                + "ORDER BY l.NumeroTelefono", MAP);
    }

    public Optional<LineaMovil> findById(String id) {
        List<LineaMovil> r = jdbc.query(
                "SELECT l.*, c.Nombre + ' ' + c.PrimerApellido AS NombreCliente "
                + "FROM LINEAMOVIL l INNER JOIN CLIENTE c ON l.Cedula = c.Cedula "
                + "WHERE l.NumeroTelefono = ?", MAP, id);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public List<LineaMovil> findAllForDropdown() {
        return jdbc.query(
                "SELECT l.*, c.Nombre + ' ' + c.PrimerApellido AS NombreCliente "
                + "FROM LINEAMOVIL l INNER JOIN CLIENTE c ON l.Cedula = c.Cedula "
                + "WHERE l.Estado = 'A' ORDER BY l.NumeroTelefono", MAP);
    }

    public int insert(LineaMovil l) {
        return jdbc.update(
                "INSERT INTO LINEAMOVIL(NumeroTelefono,Cedula,TipoLinea,Tecnologia,FechaActivacion,EstadoLinea,TipoSIM) "
                + "VALUES(?,?,?,?,?,?,?)",
                l.getNumeroTelefono(), l.getCedula(), l.getTipoLinea(), l.getTecnologia(),
                l.getFechaActivacion(), l.getEstadoLinea(), l.getTipoSIM());
    }

    public int update(LineaMovil l) {
        return jdbc.update(
                "UPDATE LINEAMOVIL SET Cedula=?,TipoLinea=?,Tecnologia=?,FechaActivacion=?,"
                + "EstadoLinea=?,TipoSIM=?,Estado=? WHERE NumeroTelefono=?",
                l.getCedula(), l.getTipoLinea(), l.getTecnologia(), l.getFechaActivacion(),
                l.getEstadoLinea(), l.getTipoSIM(), l.getEstado(), l.getNumeroTelefono());
    }

    public int delete(String id) {
        return jdbc.update("DELETE FROM LINEAMOVIL WHERE NumeroTelefono=?", id);
    }

    public boolean hasFacturas(String id) {
        Integer n = jdbc.queryForObject("SELECT COUNT(*) FROM FACTURA WHERE NumeroTelefono=?", Integer.class, id);
        return n != null && n > 0;
    }

    public boolean hasConsumos(String id) {
        Integer n = jdbc.queryForObject("SELECT COUNT(*) FROM CONSUMO WHERE NumeroTelefono=?", Integer.class, id);
        return n != null && n > 0;
    }
}
