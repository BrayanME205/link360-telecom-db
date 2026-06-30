package com.link360.repository;

import com.link360.model.PromoServicio;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class PromoServicioRepository {

    private final JdbcTemplate jdbc;

    public PromoServicioRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<PromoServicio> MAP = new RowMapper<PromoServicio>() {
        @Override
        public PromoServicio mapRow(ResultSet rs, int rowNum) throws SQLException {
            PromoServicio ps = new PromoServicio();
            ps.setCodPromocion(rs.getInt("CodPromocion"));
            ps.setCodServicio(rs.getInt("CodServicio"));
            ps.setEstado(rs.getString("Estado"));
            ps.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
            ps.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
            ps.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
            ps.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
            try {
                ps.setNombrePromocion(rs.getString("NombrePromocion"));
                ps.setNombreServicio(rs.getString("NombreServicio"));
            } catch (Exception ignored) {
            }
            return ps;
        }
    };

    public List<PromoServicio> findAll() {
        return jdbc.query(
                "SELECT ps.*, pr.Nombre AS NombrePromocion, s.Nombre AS NombreServicio "
                + "FROM PROMOSERVICIO ps "
                + "INNER JOIN PROMOCION pr ON ps.CodPromocion = pr.CodPromocion "
                + "INNER JOIN SERVICIO s ON ps.CodServicio = s.CodServicio "
                + "ORDER BY pr.Nombre", MAP);
    }

    public Optional<PromoServicio> findById(int codPromocion, int codServicio) {
        List<PromoServicio> r = jdbc.query(
                "SELECT ps.*, pr.Nombre AS NombrePromocion, s.Nombre AS NombreServicio "
                + "FROM PROMOSERVICIO ps "
                + "INNER JOIN PROMOCION pr ON ps.CodPromocion = pr.CodPromocion "
                + "INNER JOIN SERVICIO s ON ps.CodServicio = s.CodServicio "
                + "WHERE ps.CodPromocion=? AND ps.CodServicio=?", MAP, codPromocion, codServicio);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public int insert(PromoServicio ps) {
        return jdbc.update("INSERT INTO PROMOSERVICIO(CodPromocion,CodServicio) VALUES(?,?)",
                ps.getCodPromocion(), ps.getCodServicio());
    }

    public int update(PromoServicio ps) {
        return jdbc.update("UPDATE PROMOSERVICIO SET Estado=? WHERE CodPromocion=? AND CodServicio=?",
                ps.getEstado(), ps.getCodPromocion(), ps.getCodServicio());
    }

    public int delete(int codPromocion, int codServicio) {
        return jdbc.update("DELETE FROM PROMOSERVICIO WHERE CodPromocion=? AND CodServicio=?", codPromocion, codServicio);
    }
}
