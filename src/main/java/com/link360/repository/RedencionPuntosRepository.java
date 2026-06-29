package com.link360.repository;

import com.link360.model.RedencionPuntos;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class RedencionPuntosRepository {

    private final JdbcTemplate jdbc;

    public RedencionPuntosRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<RedencionPuntos> MAP = new RowMapper<RedencionPuntos>() {
        @Override
        public RedencionPuntos mapRow(ResultSet rs, int rowNum) throws SQLException {
            RedencionPuntos r = new RedencionPuntos();
            r.setCedula(rs.getString("Cedula"));
            r.setNumFactura(rs.getInt("NumFactura"));
            r.setFechaRedencion(rs.getTimestamp("FechaRedencion") != null ? rs.getTimestamp("FechaRedencion").toLocalDateTime() : null);
            r.setCantidadRedimida(rs.getInt("CantidadRedimida"));
            r.setSaldoResultante(rs.getInt("SaldoResultante"));
            r.setEstado(rs.getString("Estado"));
            r.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
            r.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
            r.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
            r.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
            return r;
        }
    };

    public List<RedencionPuntos> findAll() {
        return jdbc.query(
                "SELECT * FROM REDENCIONPUNTOS ORDER BY FechaRedencion DESC", MAP);
    }

    public Optional<RedencionPuntos> findById(String cedula, int numFactura, String fechaRedencion) {
        List<RedencionPuntos> r = jdbc.query(
                "SELECT * FROM REDENCIONPUNTOS WHERE Cedula=? AND NumFactura=? AND FechaRedencion=?",
                MAP, cedula, numFactura, fechaRedencion);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public int insert(RedencionPuntos r) {
        return jdbc.update(
                "INSERT INTO REDENCIONPUNTOS(Cedula,NumFactura,FechaRedencion,CantidadRedimida,SaldoResultante) VALUES(?,?,?,?,?)",
                r.getCedula(), r.getNumFactura(), r.getFechaRedencion(),
                r.getCantidadRedimida(), r.getSaldoResultante());
    }

    public int update(RedencionPuntos r) {
        return jdbc.update(
                "UPDATE REDENCIONPUNTOS SET CantidadRedimida=?,SaldoResultante=?,Estado=? WHERE Cedula=? AND NumFactura=? AND FechaRedencion=?",
                r.getCantidadRedimida(), r.getSaldoResultante(), r.getEstado(),
                r.getCedula(), r.getNumFactura(), r.getFechaRedencion());
    }

    public int delete(String cedula, int numFactura, String fechaRedencion) {
        return jdbc.update(
                "DELETE FROM REDENCIONPUNTOS WHERE Cedula=? AND NumFactura=? AND FechaRedencion=?",
                cedula, numFactura, fechaRedencion);
    }
}
