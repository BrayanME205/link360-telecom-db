package com.link360.repository;

import com.link360.model.Servicio;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ServicioRepository {

    private final JdbcTemplate jdbc;

    public ServicioRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<Servicio> MAP = (rs, n) -> {
        Servicio s = new Servicio();
        s.setCodServicio(rs.getInt("CodServicio"));
        s.setNombre(rs.getString("Nombre"));
        s.setDescripcion(rs.getString("Descripcion"));
        s.setCostoMensual(rs.getBigDecimal("CostoMensual"));
        s.setCategoria(rs.getString("Categoria"));
        s.setEstado(rs.getString("Estado"));
        s.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
        s.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
        s.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
        s.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
        return s;
    };

    public List<Servicio> findAll() {
        return jdbc.query("SELECT * FROM SERVICIO ORDER BY Nombre", MAP);
    }

    public Optional<Servicio> findById(int id) {
        List<Servicio> r = jdbc.query("SELECT * FROM SERVICIO WHERE CodServicio=?", MAP, id);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public int insert(Servicio s) {
        return jdbc.update(
                "INSERT INTO SERVICIO(CodServicio,Nombre,Descripcion,CostoMensual,Categoria) VALUES(?,?,?,?,?)",
                s.getCodServicio(), s.getNombre(), s.getDescripcion(), s.getCostoMensual(), s.getCategoria());
    }

    public int update(Servicio s) {
        return jdbc.update(
                "UPDATE SERVICIO SET Nombre=?,Descripcion=?,CostoMensual=?,Categoria=?,Estado=? WHERE CodServicio=?",
                s.getNombre(), s.getDescripcion(), s.getCostoMensual(), s.getCategoria(), s.getEstado(), s.getCodServicio());
    }

    public int delete(int id) {
        return jdbc.update("DELETE FROM SERVICIO WHERE CodServicio=?", id);
    }

    public boolean hasLineas(int id) {
        Integer n = jdbc.queryForObject("SELECT COUNT(*) FROM LINEASERVICIO WHERE CodServicio=?", Integer.class, id);
        return n != null && n > 0;
    }
}
