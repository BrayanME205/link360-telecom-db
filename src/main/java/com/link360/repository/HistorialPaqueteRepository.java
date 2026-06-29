package com.link360.repository;

import com.link360.model.HistorialPaquete;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class HistorialPaqueteRepository {

    private final JdbcTemplate jdbc;

    public HistorialPaqueteRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<HistorialPaquete> MAP = new RowMapper<HistorialPaquete>() {
        @Override
        public HistorialPaquete mapRow(ResultSet rs, int rowNum) throws SQLException {
            HistorialPaquete h = new HistorialPaquete();
            h.setNumeroTelefono(rs.getString("NumeroTelefono"));
            h.setCodPaquete(rs.getInt("CodPaquete"));
            h.setFechaAdquisicion(rs.getDate("FechaAdquisicion") != null ? rs.getDate("FechaAdquisicion").toLocalDate() : null);
            h.setFechaVencimiento(rs.getDate("FechaVencimiento") != null ? rs.getDate("FechaVencimiento").toLocalDate() : null);
            h.setEstado(rs.getString("Estado"));
            h.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
            h.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
            h.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
            h.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
            try {
                h.setNombrePaquete(rs.getString("NombrePaquete"));
            } catch (Exception ignored) {
            }
            return h;
        }
    };

    public List<HistorialPaquete> findAll() {
        return jdbc.query(
                "SELECT h.*, p.Nombre AS NombrePaquete "
                + "FROM HISTORIALPAQUETE h INNER JOIN PAQUETE p ON h.CodPaquete = p.CodPaquete "
                + "ORDER BY h.FechaAdquisicion DESC", MAP);
    }

    public Optional<HistorialPaquete> findById(String numeroTelefono, int codPaquete, String fechaAdquisicion) {
        List<HistorialPaquete> r = jdbc.query(
                "SELECT h.*, p.Nombre AS NombrePaquete "
                + "FROM HISTORIALPAQUETE h INNER JOIN PAQUETE p ON h.CodPaquete = p.CodPaquete "
                + "WHERE h.NumeroTelefono=? AND h.CodPaquete=? AND h.FechaAdquisicion=?",
                MAP, numeroTelefono, codPaquete, fechaAdquisicion);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public int insert(HistorialPaquete h) {
        return jdbc.update(
                "INSERT INTO HISTORIALPAQUETE(NumeroTelefono,CodPaquete,FechaAdquisicion,FechaVencimiento) VALUES(?,?,?,?)",
                h.getNumeroTelefono(), h.getCodPaquete(), h.getFechaAdquisicion(), h.getFechaVencimiento());
    }

    public int update(HistorialPaquete h) {
        return jdbc.update(
                "UPDATE HISTORIALPAQUETE SET FechaVencimiento=?,Estado=? WHERE NumeroTelefono=? AND CodPaquete=? AND FechaAdquisicion=?",
                h.getFechaVencimiento(), h.getEstado(),
                h.getNumeroTelefono(), h.getCodPaquete(), h.getFechaAdquisicion());
    }

    public int delete(String numeroTelefono, int codPaquete, String fechaAdquisicion) {
        return jdbc.update(
                "DELETE FROM HISTORIALPAQUETE WHERE NumeroTelefono=? AND CodPaquete=? AND FechaAdquisicion=?",
                numeroTelefono, codPaquete, fechaAdquisicion);
    }
}
