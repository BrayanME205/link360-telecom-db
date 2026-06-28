package com.link360.repository;

import com.link360.model.CorreoElectronico;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CorreoElectronicoRepository {

    private final JdbcTemplate jdbc;

    public CorreoElectronicoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<CorreoElectronico> MAP = (rs, n) -> {
        CorreoElectronico c = new CorreoElectronico();
        c.setCedula(rs.getString("Cedula"));
        c.setEmail(rs.getString("Email"));
        c.setEstado(rs.getString("Estado"));
        c.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
        c.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
        c.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
        c.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
        try {
            c.setNombreCliente(rs.getString("NombreCliente"));
        } catch (Exception ignored) {
        }
        return c;
    };

    public List<CorreoElectronico> findAll() {
        return jdbc.query(
                "SELECT ce.*, c.Nombre + ' ' + c.PrimerApellido AS NombreCliente "
                + "FROM CORREOELECTRONICO ce INNER JOIN CLIENTE c ON ce.Cedula = c.Cedula "
                + "ORDER BY c.PrimerApellido, ce.Email", MAP);
    }

    public Optional<CorreoElectronico> findById(String cedula, String email) {
        List<CorreoElectronico> r = jdbc.query(
                "SELECT ce.*, c.Nombre + ' ' + c.PrimerApellido AS NombreCliente "
                + "FROM CORREOELECTRONICO ce INNER JOIN CLIENTE c ON ce.Cedula = c.Cedula "
                + "WHERE ce.Cedula=? AND ce.Email=?", MAP, cedula, email);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public int insert(CorreoElectronico c) {
        return jdbc.update(
                "INSERT INTO CORREOELECTRONICO(Cedula,Email) VALUES(?,?)",
                c.getCedula(), c.getEmail());
    }

    public int update(CorreoElectronico c) {
        return jdbc.update(
                "UPDATE CORREOELECTRONICO SET Estado=? WHERE Cedula=? AND Email=?",
                c.getEstado(), c.getCedula(), c.getEmail());
    }

    public int delete(String cedula, String email) {
        return jdbc.update("DELETE FROM CORREOELECTRONICO WHERE Cedula=? AND Email=?", cedula, email);
    }
}
