package com.link360.repository;

import com.link360.model.Cliente;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ClienteRepository {

    private final JdbcTemplate jdbc;

    public ClienteRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<Cliente> ROW_MAPPER = (rs, rowNum) -> {
        Cliente c = new Cliente();
        c.setCedula(rs.getString("Cedula"));
        c.setNombre(rs.getString("Nombre"));
        c.setPrimerApellido(rs.getString("PrimerApellido"));
        c.setSegundoApellido(rs.getString("SegundoApellido"));
        c.setFechaIngreso(rs.getDate("FechaIngreso") != null
                ? rs.getDate("FechaIngreso").toLocalDate() : null);
        c.setTipoCliente(rs.getString("TipoCliente"));
        c.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
        c.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null
                ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
        c.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
        c.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null
                ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
        c.setEstado(rs.getString("Estado"));
        return c;
    };

    public List<Cliente> findAll() {
        String sql = "SELECT * FROM CLIENTE ORDER BY PrimerApellido, Nombre";
        return jdbc.query(sql, ROW_MAPPER);
    }

    public Optional<Cliente> findById(String cedula) {
        String sql = "SELECT * FROM CLIENTE WHERE Cedula = ?";
        List<Cliente> result = jdbc.query(sql, ROW_MAPPER, cedula);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public int insert(Cliente c) {
        String sql = """
            INSERT INTO CLIENTE (Cedula, Nombre, PrimerApellido, SegundoApellido,
                                 FechaIngreso, TipoCliente)
            VALUES (?, ?, ?, ?, ?, ?)
            """;
        return jdbc.update(sql,
                c.getCedula(),
                c.getNombre(),
                c.getPrimerApellido(),
                c.getSegundoApellido(),
                c.getFechaIngreso(),
                c.getTipoCliente());
    }

    public int update(Cliente c) {
        String sql = """
            UPDATE CLIENTE
            SET Nombre              = ?,
                PrimerApellido      = ?,
                SegundoApellido     = ?,
                FechaIngreso        = ?,
                TipoCliente         = ?,
                Estado              = ?
            WHERE Cedula = ?
            """;
        return jdbc.update(sql,
                c.getNombre(),
                c.getPrimerApellido(),
                c.getSegundoApellido(),
                c.getFechaIngreso(),
                c.getTipoCliente(),
                c.getEstado(),
                c.getCedula());
    }

    public int delete(String cedula) {
        return jdbc.update("DELETE FROM CLIENTE WHERE Cedula = ?", cedula);
    }

    public boolean hasLineas(String cedula) {
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM LINEAMOVIL WHERE Cedula = ?", Integer.class, cedula);
        return count != null && count > 0;
    }
}
