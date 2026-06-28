package com.link360.repository;

import com.link360.model.Descuento;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DescuentoRepository {

    private final JdbcTemplate jdbc;

    public DescuentoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<Descuento> MAP = (rs, n) -> {
        Descuento d = new Descuento();
        d.setCodDescuento(rs.getInt("CodDescuento"));
        d.setDescripcion(rs.getString("Descripcion"));
        d.setHoraInicio(rs.getTime("HoraInicio") != null ? rs.getTime("HoraInicio").toLocalTime() : null);
        d.setHoraFin(rs.getTime("HoraFin") != null ? rs.getTime("HoraFin").toLocalTime() : null);
        d.setNumDias(rs.getInt("NumDias"));
        d.setEstado(rs.getString("Estado"));
        d.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
        d.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
        d.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
        d.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
        return d;
    };

    public List<Descuento> findAll() {
        return jdbc.query("SELECT * FROM DESCUENTO ORDER BY Descripcion", MAP);
    }

    public Optional<Descuento> findById(int id) {
        List<Descuento> r = jdbc.query("SELECT * FROM DESCUENTO WHERE CodDescuento=?", MAP, id);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public int insert(Descuento d) {
        return jdbc.update(
                "INSERT INTO DESCUENTO(CodDescuento,Descripcion,HoraInicio,HoraFin,NumDias) VALUES(?,?,?,?,?)",
                d.getCodDescuento(), d.getDescripcion(), d.getHoraInicio(), d.getHoraFin(), d.getNumDias());
    }

    public int update(Descuento d) {
        return jdbc.update(
                "UPDATE DESCUENTO SET Descripcion=?,HoraInicio=?,HoraFin=?,NumDias=?,Estado=? WHERE CodDescuento=?",
                d.getDescripcion(), d.getHoraInicio(), d.getHoraFin(), d.getNumDias(), d.getEstado(), d.getCodDescuento());
    }

    public int delete(int id) {
        return jdbc.update("DELETE FROM DESCUENTO WHERE CodDescuento=?", id);
    }

    public boolean hasConsumos(int id) {
        Integer n = jdbc.queryForObject("SELECT COUNT(*) FROM CONSUMO WHERE CodDescuento=?", Integer.class, id);
        return n != null && n > 0;
    }
}
