package com.link360.repository;

import com.link360.model.PromoIncompatible;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class PromoIncompatibleRepository {

    private final JdbcTemplate jdbc;

    public PromoIncompatibleRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<PromoIncompatible> MAP = new RowMapper<PromoIncompatible>() {
        @Override
        public PromoIncompatible mapRow(ResultSet rs, int rowNum) throws SQLException {
            PromoIncompatible pi = new PromoIncompatible();
            pi.setCodPromocion1(rs.getInt("CodPromocion1"));
            pi.setCodPromocion2(rs.getInt("CodPromocion2"));
            pi.setEstado(rs.getString("Estado"));
            pi.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
            pi.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
            pi.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
            pi.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
            try {
                pi.setNombrePromocion1(rs.getString("NombrePromocion1"));
                pi.setNombrePromocion2(rs.getString("NombrePromocion2"));
            } catch (Exception ignored) {
            }
            return pi;
        }
    };

    public List<PromoIncompatible> findAll() {
        return jdbc.query(
                "SELECT pi.*, p1.Nombre AS NombrePromocion1, p2.Nombre AS NombrePromocion2 "
                + "FROM PROMOINCOMPATIBLE pi "
                + "INNER JOIN PROMOCION p1 ON pi.CodPromocion1 = p1.CodPromocion "
                + "INNER JOIN PROMOCION p2 ON pi.CodPromocion2 = p2.CodPromocion "
                + "ORDER BY p1.Nombre", MAP);
    }

    public Optional<PromoIncompatible> findById(int p1, int p2) {
        List<PromoIncompatible> r = jdbc.query(
                "SELECT pi.*, pr1.Nombre AS NombrePromocion1, pr2.Nombre AS NombrePromocion2 "
                + "FROM PROMOINCOMPATIBLE pi "
                + "INNER JOIN PROMOCION pr1 ON pi.CodPromocion1 = pr1.CodPromocion "
                + "INNER JOIN PROMOCION pr2 ON pi.CodPromocion2 = pr2.CodPromocion "
                + "WHERE pi.CodPromocion1=? AND pi.CodPromocion2=?", MAP, p1, p2);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public int insert(PromoIncompatible pi) {
        return jdbc.update("INSERT INTO PROMOINCOMPATIBLE(CodPromocion1,CodPromocion2) VALUES(?,?)",
                pi.getCodPromocion1(), pi.getCodPromocion2());
    }

    public int update(PromoIncompatible pi) {
        return jdbc.update("UPDATE PROMOINCOMPATIBLE SET Estado=? WHERE CodPromocion1=? AND CodPromocion2=?",
                pi.getEstado(), pi.getCodPromocion1(), pi.getCodPromocion2());
    }

    public int delete(int p1, int p2) {
        return jdbc.update("DELETE FROM PROMOINCOMPATIBLE WHERE CodPromocion1=? AND CodPromocion2=?", p1, p2);
    }
}
