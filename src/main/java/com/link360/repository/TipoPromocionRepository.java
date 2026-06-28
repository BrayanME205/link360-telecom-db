package com.link360.repository;

import com.link360.model.TipoPromocion;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TipoPromocionRepository {

    private final JdbcTemplate jdbc;

    public TipoPromocionRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<TipoPromocion> MAP = (rs, n) -> {
        TipoPromocion t = new TipoPromocion();
        t.setTipoPromo(rs.getString("TipoPromo"));
        t.setNombreTipo(rs.getString("NombreTipo"));
        t.setPorcentajeMaximo(rs.getBigDecimal("PorcentajeMaximo"));
        t.setEstado(rs.getString("Estado"));
        t.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
        t.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
        t.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
        t.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
        return t;
    };

    public List<TipoPromocion> findAll() {
        return jdbc.query("SELECT * FROM TIPOPROMOCION ORDER BY TipoPromo", MAP);
    }

    public Optional<TipoPromocion> findById(String id) {
        List<TipoPromocion> r = jdbc.query("SELECT * FROM TIPOPROMOCION WHERE TipoPromo=?", MAP, id);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public int insert(TipoPromocion t) {
        return jdbc.update(
                "INSERT INTO TIPOPROMOCION(TipoPromo,NombreTipo,PorcentajeMaximo) VALUES(?,?,?)",
                t.getTipoPromo(), t.getNombreTipo(), t.getPorcentajeMaximo());
    }

    public int update(TipoPromocion t) {
        return jdbc.update(
                "UPDATE TIPOPROMOCION SET NombreTipo=?,PorcentajeMaximo=?,Estado=? WHERE TipoPromo=?",
                t.getNombreTipo(), t.getPorcentajeMaximo(), t.getEstado(), t.getTipoPromo());
    }

    public int delete(String id) {
        return jdbc.update("DELETE FROM TIPOPROMOCION WHERE TipoPromo=?", id);
    }

    public boolean hasPromociones(String id) {
        Integer n = jdbc.queryForObject("SELECT COUNT(*) FROM PROMOCION WHERE TipoPromo=?", Integer.class, id);
        return n != null && n > 0;
    }
}
