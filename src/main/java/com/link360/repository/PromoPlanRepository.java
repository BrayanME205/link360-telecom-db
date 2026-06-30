package com.link360.repository;

import com.link360.model.PromoPlan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class PromoPlanRepository {

    private final JdbcTemplate jdbc;

    public PromoPlanRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<PromoPlan> MAP = new RowMapper<PromoPlan>() {
        @Override
        public PromoPlan mapRow(ResultSet rs, int rowNum) throws SQLException {
            PromoPlan pp = new PromoPlan();
            pp.setCodPromocion(rs.getInt("CodPromocion"));
            pp.setCodPlan(rs.getInt("CodPlan"));
            pp.setEstado(rs.getString("Estado"));
            pp.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
            pp.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
            pp.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
            pp.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
            try {
                pp.setNombrePromocion(rs.getString("NombrePromocion"));
                pp.setNombrePlan(rs.getString("NombrePlan"));
            } catch (Exception ignored) {
            }
            return pp;
        }
    };

    public List<PromoPlan> findAll() {
        return jdbc.query(
                "SELECT pp.*, pr.Nombre AS NombrePromocion, p.Nombre AS NombrePlan "
                + "FROM PROMOPLAN pp "
                + "INNER JOIN PROMOCION pr ON pp.CodPromocion = pr.CodPromocion "
                + "INNER JOIN [PLAN] p ON pp.CodPlan = p.CodPlan "
                + "ORDER BY pr.Nombre", MAP);
    }

    public Optional<PromoPlan> findById(int codPromocion, int codPlan) {
        List<PromoPlan> r = jdbc.query(
                "SELECT pp.*, pr.Nombre AS NombrePromocion, p.Nombre AS NombrePlan "
                + "FROM PROMOPLAN pp "
                + "INNER JOIN PROMOCION pr ON pp.CodPromocion = pr.CodPromocion "
                + "INNER JOIN [PLAN] p ON pp.CodPlan = p.CodPlan "
                + "WHERE pp.CodPromocion=? AND pp.CodPlan=?", MAP, codPromocion, codPlan);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public int insert(PromoPlan pp) {
        return jdbc.update("INSERT INTO PROMOPLAN(CodPromocion,CodPlan) VALUES(?,?)",
                pp.getCodPromocion(), pp.getCodPlan());
    }

    public int update(PromoPlan pp) {
        return jdbc.update("UPDATE PROMOPLAN SET Estado=? WHERE CodPromocion=? AND CodPlan=?",
                pp.getEstado(), pp.getCodPromocion(), pp.getCodPlan());
    }

    public int delete(int codPromocion, int codPlan) {
        return jdbc.update("DELETE FROM PROMOPLAN WHERE CodPromocion=? AND CodPlan=?", codPromocion, codPlan);
    }
}
