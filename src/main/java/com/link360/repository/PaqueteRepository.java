package com.link360.repository;

import com.link360.model.Paquete;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PaqueteRepository {

    private final JdbcTemplate jdbc;

    public PaqueteRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<Paquete> MAP = (rs, n) -> {
        Paquete p = new Paquete();
        p.setCodPaquete(rs.getInt("CodPaquete"));
        p.setNombre(rs.getString("Nombre"));
        p.setCapacidadGB(rs.getBigDecimal("CapacidadGB"));
        p.setPrecio(rs.getBigDecimal("Precio"));
        p.setVigencia(rs.getInt("Vigencia"));
        p.setEstado(rs.getString("Estado"));
        p.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
        p.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
        p.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
        p.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
        return p;
    };

    public List<Paquete> findAll() {
        return jdbc.query("SELECT * FROM PAQUETE ORDER BY Nombre", MAP);
    }

    public Optional<Paquete> findById(int id) {
        List<Paquete> r = jdbc.query("SELECT * FROM PAQUETE WHERE CodPaquete=?", MAP, id);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public int insert(Paquete p) {
        return jdbc.update(
                "INSERT INTO PAQUETE(CodPaquete,Nombre,CapacidadGB,Precio,Vigencia) VALUES(?,?,?,?,?)",
                p.getCodPaquete(), p.getNombre(), p.getCapacidadGB(), p.getPrecio(), p.getVigencia());
    }

    public int update(Paquete p) {
        return jdbc.update(
                "UPDATE PAQUETE SET Nombre=?,CapacidadGB=?,Precio=?,Vigencia=?,Estado=? WHERE CodPaquete=?",
                p.getNombre(), p.getCapacidadGB(), p.getPrecio(), p.getVigencia(), p.getEstado(), p.getCodPaquete());
    }

    public int delete(int id) {
        return jdbc.update("DELETE FROM PAQUETE WHERE CodPaquete=?", id);
    }

    public boolean hasHistorial(int id) {
        Integer n = jdbc.queryForObject("SELECT COUNT(*) FROM HISTORIALPAQUETE WHERE CodPaquete=?", Integer.class, id);
        return n != null && n > 0;
    }
}
