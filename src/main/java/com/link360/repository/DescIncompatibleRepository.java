package com.link360.repository;

import com.link360.model.DescIncompatible;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class DescIncompatibleRepository {

    private final JdbcTemplate jdbc;

    public DescIncompatibleRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<DescIncompatible> MAP = new RowMapper<DescIncompatible>() {
        @Override
        public DescIncompatible mapRow(ResultSet rs, int rowNum) throws SQLException {
            DescIncompatible di = new DescIncompatible();
            di.setCodDescuento1(rs.getInt("CodDescuento1"));
            di.setCodDescuento2(rs.getInt("CodDescuento2"));
            di.setEstado(rs.getString("Estado"));
            di.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
            di.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
            di.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
            di.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
            try {
                di.setDescripcionDesc1(rs.getString("DescripcionDesc1"));
                di.setDescripcionDesc2(rs.getString("DescripcionDesc2"));
            } catch (Exception ignored) {
            }
            return di;
        }
    };

    public List<DescIncompatible> findAll() {
        return jdbc.query(
                "SELECT di.*, d1.Descripcion AS DescripcionDesc1, d2.Descripcion AS DescripcionDesc2 "
                + "FROM DESCINCOMPATIBLE di "
                + "INNER JOIN DESCUENTO d1 ON di.CodDescuento1 = d1.CodDescuento "
                + "INNER JOIN DESCUENTO d2 ON di.CodDescuento2 = d2.CodDescuento "
                + "ORDER BY d1.Descripcion", MAP);
    }

    public Optional<DescIncompatible> findById(int d1, int d2) {
        List<DescIncompatible> r = jdbc.query(
                "SELECT di.*, dd1.Descripcion AS DescripcionDesc1, dd2.Descripcion AS DescripcionDesc2 "
                + "FROM DESCINCOMPATIBLE di "
                + "INNER JOIN DESCUENTO dd1 ON di.CodDescuento1 = dd1.CodDescuento "
                + "INNER JOIN DESCUENTO dd2 ON di.CodDescuento2 = dd2.CodDescuento "
                + "WHERE di.CodDescuento1=? AND di.CodDescuento2=?", MAP, d1, d2);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public int insert(DescIncompatible di) {
        return jdbc.update("INSERT INTO DESCINCOMPATIBLE(CodDescuento1,CodDescuento2) VALUES(?,?)",
                di.getCodDescuento1(), di.getCodDescuento2());
    }

    public int update(DescIncompatible di) {
        return jdbc.update("UPDATE DESCINCOMPATIBLE SET Estado=? WHERE CodDescuento1=? AND CodDescuento2=?",
                di.getEstado(), di.getCodDescuento1(), di.getCodDescuento2());
    }

    public int delete(int d1, int d2) {
        return jdbc.update("DELETE FROM DESCINCOMPATIBLE WHERE CodDescuento1=? AND CodDescuento2=?", d1, d2);
    }
}
