package com.link360.repository;

import com.link360.model.LineaServicio;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class LineaServicioRepository {

    private final JdbcTemplate jdbc;

    public LineaServicioRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<LineaServicio> MAP = new RowMapper<LineaServicio>() {
        @Override
        public LineaServicio mapRow(ResultSet rs, int rowNum) throws SQLException {
            LineaServicio ls = new LineaServicio();
            ls.setNumeroTelefono(rs.getString("NumeroTelefono"));
            ls.setCodServicio(rs.getInt("CodServicio"));
            ls.setFechaContratacion(rs.getDate("FechaContratacion") != null ? rs.getDate("FechaContratacion").toLocalDate() : null);
            ls.setEstado(rs.getString("Estado"));
            ls.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
            ls.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
            ls.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
            ls.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
            try {
                ls.setNombreServicio(rs.getString("NombreServicio"));
            } catch (Exception ignored) {
            }
            return ls;
        }
    };

    public List<LineaServicio> findAll() {
        return jdbc.query(
                "SELECT ls.*, s.Nombre AS NombreServicio "
                + "FROM LINEASERVICIO ls INNER JOIN SERVICIO s ON ls.CodServicio = s.CodServicio "
                + "ORDER BY ls.FechaContratacion DESC", MAP);
    }

    public Optional<LineaServicio> findById(String numero, int codServicio) {
        List<LineaServicio> r = jdbc.query(
                "SELECT ls.*, s.Nombre AS NombreServicio "
                + "FROM LINEASERVICIO ls INNER JOIN SERVICIO s ON ls.CodServicio = s.CodServicio "
                + "WHERE ls.NumeroTelefono=? AND ls.CodServicio=?", MAP, numero, codServicio);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public int insert(LineaServicio ls) {
        return jdbc.update(
                "INSERT INTO LINEASERVICIO(NumeroTelefono,CodServicio,FechaContratacion) VALUES(?,?,?)",
                ls.getNumeroTelefono(), ls.getCodServicio(), ls.getFechaContratacion());
    }

    public int update(LineaServicio ls) {
        return jdbc.update(
                "UPDATE LINEASERVICIO SET Estado=? WHERE NumeroTelefono=? AND CodServicio=?",
                ls.getEstado(), ls.getNumeroTelefono(), ls.getCodServicio());
    }

    public int delete(String numero, int codServicio) {
        return jdbc.update("DELETE FROM LINEASERVICIO WHERE NumeroTelefono=? AND CodServicio=?", numero, codServicio);
    }
}
