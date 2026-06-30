package com.link360.repository;

import com.link360.model.LineaPlan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class LineaPlanRepository {

    private final JdbcTemplate jdbc;

    public LineaPlanRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<LineaPlan> MAP = new RowMapper<LineaPlan>() {
        @Override
        public LineaPlan mapRow(ResultSet rs, int rowNum) throws SQLException {
            LineaPlan lp = new LineaPlan();
            lp.setNumeroTelefono(rs.getString("NumeroTelefono"));
            lp.setCodPlan(rs.getInt("CodPlan"));
            lp.setFechaInicio(rs.getDate("FechaInicio") != null ? rs.getDate("FechaInicio").toLocalDate() : null);
            lp.setFechaFin(rs.getDate("FechaFin") != null ? rs.getDate("FechaFin").toLocalDate() : null);
            lp.setEstado(rs.getString("Estado"));
            lp.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
            lp.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
            lp.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
            lp.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
            try {
                lp.setNombrePlan(rs.getString("NombrePlan"));
            } catch (Exception ignored) {
            }
            return lp;
        }
    };

    public List<LineaPlan> findAll() {
        return jdbc.query(
                "SELECT lp.*, p.Nombre AS NombrePlan "
                + "FROM LINEAPLAN lp INNER JOIN [PLAN] p ON lp.CodPlan = p.CodPlan "
                + "ORDER BY lp.FechaInicio DESC", MAP);
    }

    public Optional<LineaPlan> findById(String numero, int codPlan, String fechaInicio) {
        List<LineaPlan> r = jdbc.query(
                "SELECT lp.*, p.Nombre AS NombrePlan "
                + "FROM LINEAPLAN lp INNER JOIN [PLAN] p ON lp.CodPlan = p.CodPlan "
                + "WHERE lp.NumeroTelefono=? AND lp.CodPlan=? AND lp.FechaInicio=?",
                MAP, numero, codPlan, fechaInicio);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public int insert(LineaPlan lp) {
        return jdbc.update(
                "INSERT INTO LINEAPLAN(NumeroTelefono,CodPlan,FechaInicio,FechaFin) VALUES(?,?,?,?)",
                lp.getNumeroTelefono(), lp.getCodPlan(), lp.getFechaInicio(), lp.getFechaFin());
    }

    public int update(LineaPlan lp) {
        return jdbc.update(
                "UPDATE LINEAPLAN SET FechaFin=?,Estado=? WHERE NumeroTelefono=? AND CodPlan=? AND FechaInicio=?",
                lp.getFechaFin(), lp.getEstado(), lp.getNumeroTelefono(), lp.getCodPlan(), lp.getFechaInicio());
    }

    public int delete(String numero, int codPlan, String fechaInicio) {
        return jdbc.update(
                "DELETE FROM LINEAPLAN WHERE NumeroTelefono=? AND CodPlan=? AND FechaInicio=?",
                numero, codPlan, fechaInicio);
    }
}
