package com.link360.repository;

import com.link360.model.Direccion;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DireccionRepository {

    private final JdbcTemplate jdbc;

    public DireccionRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<Direccion> MAP = (rs, n) -> {
        Direccion d = new Direccion();
        d.setCedula(rs.getString("Cedula"));
        d.setDireccion(rs.getString("Direccion"));
        d.setEstado(rs.getString("Estado"));
        d.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
        d.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
        d.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
        d.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
        try {
            d.setNombreCliente(rs.getString("NombreCliente"));
        } catch (Exception ignored) {
        }
        return d;
    };

    public List<Direccion> findAll() {
        return jdbc.query(
                "SELECT d.*, c.Nombre + ' ' + c.PrimerApellido AS NombreCliente "
                + "FROM DIRECCION d INNER JOIN CLIENTE c ON d.Cedula = c.Cedula "
                + "ORDER BY c.PrimerApellido", MAP);
    }

    public Optional<Direccion> findById(String cedula) {
        List<Direccion> r = jdbc.query(
                "SELECT d.*, c.Nombre + ' ' + c.PrimerApellido AS NombreCliente "
                + "FROM DIRECCION d INNER JOIN CLIENTE c ON d.Cedula = c.Cedula "
                + "WHERE d.Cedula=?", MAP, cedula);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public int insert(Direccion d) {
        return jdbc.update(
                "INSERT INTO DIRECCION(Cedula,Direccion) VALUES(?,?)",
                d.getCedula(), d.getDireccion());
    }

    public int update(Direccion d) {
        return jdbc.update(
                "UPDATE DIRECCION SET Direccion=?,Estado=? WHERE Cedula=?",
                d.getDireccion(), d.getEstado(), d.getCedula());
    }

    public int delete(String cedula) {
        return jdbc.update("DELETE FROM DIRECCION WHERE Cedula=?", cedula);
    }
}
