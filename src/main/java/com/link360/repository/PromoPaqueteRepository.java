package com.link360.repository;

import com.link360.model.PromoPaquete;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class PromoPaqueteRepository {

    private final JdbcTemplate jdbc;

    public PromoPaqueteRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<PromoPaquete> MAP = new RowMapper<PromoPaquete>() {
        @Override
        public PromoPaquete mapRow(ResultSet rs, int rowNum) throws SQLException {
            PromoPaquete pp = new PromoPaquete();
            pp.setCodPromocion(rs.getInt("CodPromocion"));
            pp.setCodPaquete(rs.getInt("CodPaquete"));
            pp.setEstado(rs.getString("Estado"));
            pp.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
            pp.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
            pp.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
            pp.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
            try {
                pp.setNombrePromocion(rs.getString("NombrePromocion"));
                pp.setNombrePaquete(rs.getString("NombrePaquete"));
            } catch (Exception ignored) {
            }
            return pp;
        }
    };

    public List<PromoPaquete> findAll() {
        return jdbc.query(
                "SELECT pp.*, pr.Nombre AS NombrePromocion, pa.Nombre AS NombrePaquete "
                + "FROM PROMOPAQUETE pp "
                + "INNER JOIN PROMOCION pr ON pp.CodPromocion = pr.CodPromocion "
                + "INNER JOIN PAQUETE pa ON pp.CodPaquete = pa.CodPaquete "
                + "ORDER BY pr.Nombre", MAP);
    }

    public Optional<PromoPaquete> findById(int codPromocion, int codPaquete) {
        List<PromoPaquete> r = jdbc.query(
                "SELECT pp.*, pr.Nombre AS NombrePromocion, pa.Nombre AS NombrePaquete "
                + "FROM PROMOPAQUETE pp "
                + "INNER JOIN PROMOCION pr ON pp.CodPromocion = pr.CodPromocion "
                + "INNER JOIN PAQUETE pa ON pp.CodPaquete = pa.CodPaquete "
                + "WHERE pp.CodPromocion=? AND pp.CodPaquete=?", MAP, codPromocion, codPaquete);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public int insert(PromoPaquete pp) {
        return jdbc.update("INSERT INTO PROMOPAQUETE(CodPromocion,CodPaquete) VALUES(?,?)",
                pp.getCodPromocion(), pp.getCodPaquete());
    }

    public int update(PromoPaquete pp) {
        return jdbc.update("UPDATE PROMOPAQUETE SET Estado=? WHERE CodPromocion=? AND CodPaquete=?",
                pp.getEstado(), pp.getCodPromocion(), pp.getCodPaquete());
    }

    public int delete(int codPromocion, int codPaquete) {
        return jdbc.update("DELETE FROM PROMOPAQUETE WHERE CodPromocion=? AND CodPaquete=?", codPromocion, codPaquete);
    }
}
