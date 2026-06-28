package com.link360.repository;

import com.link360.model.CategoriaComercial;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoriaComercialRepository {

    private final JdbcTemplate jdbc;

    public CategoriaComercialRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<CategoriaComercial> MAP = (rs, n) -> {
        CategoriaComercial c = new CategoriaComercial();
        c.setCodCategoria(rs.getInt("CodCategoria"));
        c.setDescripcion(rs.getString("Descripcion"));
        c.setVelocidadMaxima(rs.getString("VelocidadMaxima"));
        c.setEstado(rs.getString("Estado"));
        c.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
        c.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
        c.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
        c.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
        return c;
    };

    public List<CategoriaComercial> findAll() {
        return jdbc.query("SELECT * FROM CATEGORIACOMERCIAL ORDER BY Descripcion", MAP);
    }

    public Optional<CategoriaComercial> findById(int id) {
        List<CategoriaComercial> r = jdbc.query("SELECT * FROM CATEGORIACOMERCIAL WHERE CodCategoria=?", MAP, id);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public int insert(CategoriaComercial c) {
        return jdbc.update(
                "INSERT INTO CATEGORIACOMERCIAL(CodCategoria,Descripcion,VelocidadMaxima) VALUES(?,?,?)",
                c.getCodCategoria(), c.getDescripcion(), c.getVelocidadMaxima());
    }

    public int update(CategoriaComercial c) {
        return jdbc.update(
                "UPDATE CATEGORIACOMERCIAL SET Descripcion=?,VelocidadMaxima=?,Estado=? WHERE CodCategoria=?",
                c.getDescripcion(), c.getVelocidadMaxima(), c.getEstado(), c.getCodCategoria());
    }

    public int delete(int id) {
        return jdbc.update("DELETE FROM CATEGORIACOMERCIAL WHERE CodCategoria=?", id);
    }

    public boolean hasPlanes(int id) {
        Integer n = jdbc.queryForObject("SELECT COUNT(*) FROM [PLAN] WHERE CodCategoria=?", Integer.class, id);
        return n != null && n > 0;
    }
}
