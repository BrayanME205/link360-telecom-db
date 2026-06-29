package com.link360.repository;

import com.link360.model.Promocion;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class PromocionRepository {

    private final JdbcTemplate jdbc;
    public PromocionRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private final RowMapper<Promocion> MAP = new RowMapper<Promocion>() {
        @Override
        public Promocion mapRow(ResultSet rs, int rowNum) throws SQLException {
            Promocion p = new Promocion();
            p.setCodPromocion(rs.getInt("CodPromocion"));
            p.setTipoPromo(rs.getString("TipoPromo"));
            p.setNombre(rs.getString("Nombre"));
            p.setDescripcion(rs.getString("Descripcion"));
            p.setFechaInicio(rs.getDate("FechaInicio") != null ? rs.getDate("FechaInicio").toLocalDate() : null);
            p.setFechaFin(rs.getDate("FechaFin") != null ? rs.getDate("FechaFin").toLocalDate() : null);
            p.setPorcDescuento(rs.getBigDecimal("PorcDescuento"));
            p.setEstado(rs.getString("Estado"));
            p.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
            p.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
            p.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
            p.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
            return p;
        }
    };

    public List<Promocion> findAll() {
        return jdbc.query("SELECT * FROM PROMOCION ORDER BY Nombre", MAP);
    }

    public Optional<Promocion> findById(int id) {
        List<Promocion> r = jdbc.query("SELECT * FROM PROMOCION WHERE CodPromocion=?", MAP, id);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public List<Promocion> findAllForDropdown() {
        return jdbc.query("SELECT * FROM PROMOCION WHERE Estado='A' ORDER BY Nombre", MAP);
    }

    public int insert(Promocion p) {
        return jdbc.update(
            "INSERT INTO PROMOCION(CodPromocion,TipoPromo,Nombre,Descripcion,FechaInicio,FechaFin,PorcDescuento) VALUES(?,?,?,?,?,?,?)",
            p.getCodPromocion(), p.getTipoPromo(), p.getNombre(), p.getDescripcion(),
            p.getFechaInicio(), p.getFechaFin(), p.getPorcDescuento());
    }

    public int update(Promocion p) {
        return jdbc.update(
            "UPDATE PROMOCION SET TipoPromo=?,Nombre=?,Descripcion=?,FechaInicio=?,FechaFin=?,PorcDescuento=?,Estado=? WHERE CodPromocion=?",
            p.getTipoPromo(), p.getNombre(), p.getDescripcion(), p.getFechaInicio(),
            p.getFechaFin(), p.getPorcDescuento(), p.getEstado(), p.getCodPromocion());
    }

    public int delete(int id) {
        return jdbc.update("DELETE FROM PROMOCION WHERE CodPromocion=?", id);
    }

    public boolean hasPlanes(int id) {
        Integer n = jdbc.queryForObject("SELECT COUNT(*) FROM PROMOPLAN WHERE CodPromocion=?", Integer.class, id);
        return n != null && n > 0;
    }
}