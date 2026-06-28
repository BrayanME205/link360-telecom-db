package com.link360.repository;

import com.link360.model.TelefonoContacto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TelefonoContactoRepository {

    private final JdbcTemplate jdbc;
    public TelefonoContactoRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private static final RowMapper<TelefonoContacto> MAP = (rs, n) -> {
        TelefonoContacto t = new TelefonoContacto();
        t.setCedula(rs.getString("Cedula"));
        t.setNumero(rs.getString("Numero"));
        t.setEstado(rs.getString("Estado"));
        t.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
        t.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
        t.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
        t.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
        try { t.setNombreCliente(rs.getString("NombreCliente")); } catch (Exception ignored) {}
        return t;
    };

    public List<TelefonoContacto> findAll() {
        return jdbc.query(
            "SELECT t.*, c.Nombre + ' ' + c.PrimerApellido AS NombreCliente " +
            "FROM TELEFONOCONTACTO t INNER JOIN CLIENTE c ON t.Cedula = c.Cedula " +
            "ORDER BY c.PrimerApellido, t.Numero", MAP);
    }

    public Optional<TelefonoContacto> findById(String cedula, String numero) {
        List<TelefonoContacto> r = jdbc.query(
            "SELECT t.*, c.Nombre + ' ' + c.PrimerApellido AS NombreCliente " +
            "FROM TELEFONOCONTACTO t INNER JOIN CLIENTE c ON t.Cedula = c.Cedula " +
            "WHERE t.Cedula=? AND t.Numero=?", MAP, cedula, numero);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public int insert(TelefonoContacto t) {
        return jdbc.update(
            "INSERT INTO TELEFONOCONTACTO(Cedula,Numero) VALUES(?,?)",
            t.getCedula(), t.getNumero());
    }

    public int update(TelefonoContacto t) {
        return jdbc.update(
            "UPDATE TELEFONOCONTACTO SET Estado=? WHERE Cedula=? AND Numero=?",
            t.getEstado(), t.getCedula(), t.getNumero());
    }

    public int delete(String cedula, String numero) {
        return jdbc.update("DELETE FROM TELEFONOCONTACTO WHERE Cedula=? AND Numero=?", cedula, numero);
    }
}