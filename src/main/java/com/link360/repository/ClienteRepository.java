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

    private final RowMapper<Cliente> MAP = new RowMapper<Cliente>() {
        @Override
        public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
            Cliente c = new Cliente();
            c.setCedula(rs.getString("Cedula"));
            c.setNombre(rs.getString("Nombre"));
            c.setPrimerApellido(rs.getString("PrimerApellido"));
            c.setSegundoApellido(rs.getString("SegundoApellido"));
            c.setFechaIngreso(rs.getDate("FechaIngreso") != null
                    ? rs.getDate("FechaIngreso").toLocalDate() : null);
            c.setTipoCliente(rs.getString("TipoCliente"));
            c.setEstado(rs.getString("Estado"));
            c.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
            c.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null
                    ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
            c.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
            c.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null
                    ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
            return c;
        }
    };

    public List<Cliente> findAll() {
        return jdbc.query(
                "SELECT * FROM CLIENTE ORDER BY PrimerApellido, Nombre", MAP);
    }

    public Optional<Cliente> findById(String cedula) {
        List<Cliente> r = jdbc.query(
                "SELECT * FROM CLIENTE WHERE Cedula = ?", MAP, cedula);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public int insert(Cliente c) {
        return jdbc.update(
                "INSERT INTO CLIENTE(Cedula, Nombre, PrimerApellido, SegundoApellido, "
                + "FechaIngreso, TipoCliente) VALUES(?,?,?,?,?,?)",
                c.getCedula(), c.getNombre(), c.getPrimerApellido(),
                c.getSegundoApellido(), c.getFechaIngreso(), c.getTipoCliente());
    }

    public int update(Cliente c) {
        return jdbc.update(
                "UPDATE CLIENTE SET Nombre=?, PrimerApellido=?, SegundoApellido=?, "
                + "FechaIngreso=?, TipoCliente=?, Estado=? WHERE Cedula=?",
                c.getNombre(), c.getPrimerApellido(), c.getSegundoApellido(),
                c.getFechaIngreso(), c.getTipoCliente(), c.getEstado(), c.getCedula());
    }

    public int delete(String cedula) {
        return jdbc.update("DELETE FROM CLIENTE WHERE Cedula=?", cedula);
    }

    public boolean hasLineas(String cedula) {
        Integer n = jdbc.queryForObject(
                "SELECT COUNT(*) FROM LINEAMOVIL WHERE Cedula=?", Integer.class, cedula);
        return n != null && n > 0;
    }

    public List<Cliente> findAllForDropdown() {
        return jdbc.query(
                "SELECT * FROM CLIENTE WHERE Estado = 'A' ORDER BY PrimerApellido, Nombre",
                MAP);
    }
}
