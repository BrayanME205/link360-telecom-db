package com.link360.repository;

import com.link360.model.ConceptoCobro;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ConceptoCobroRepository {

    private final JdbcTemplate jdbc;

    public ConceptoCobroRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<ConceptoCobro> MAP = new RowMapper<ConceptoCobro>() {
        @Override
        public ConceptoCobro mapRow(ResultSet rs, int rowNum) throws SQLException {
            ConceptoCobro c = new ConceptoCobro();
            c.setNumFactura(rs.getInt("NumFactura"));
            c.setTipoConcepto(rs.getString("TipoConcepto"));
            c.setDescripcion(rs.getString("Descripcion"));
            c.setMontoCobro(rs.getBigDecimal("MontoCobro"));
            c.setEstado(rs.getString("Estado"));
            c.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
            c.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
            c.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
            c.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
            return c;
        }
    };

    public List<ConceptoCobro> findAll() {
        return jdbc.query("SELECT * FROM CONCEPTOCOBRO ORDER BY NumFactura DESC, TipoConcepto", MAP);
    }

    public Optional<ConceptoCobro> findById(int numFactura, String tipoConcepto) {
        List<ConceptoCobro> r = jdbc.query(
                "SELECT * FROM CONCEPTOCOBRO WHERE NumFactura=? AND TipoConcepto=?",
                MAP, numFactura, tipoConcepto);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public int insert(ConceptoCobro c) {
        return jdbc.update(
                "INSERT INTO CONCEPTOCOBRO(NumFactura,TipoConcepto,Descripcion,MontoCobro) VALUES(?,?,?,?)",
                c.getNumFactura(), c.getTipoConcepto(), c.getDescripcion(), c.getMontoCobro());
    }

    public int update(ConceptoCobro c) {
        return jdbc.update(
                "UPDATE CONCEPTOCOBRO SET Descripcion=?,MontoCobro=?,Estado=? WHERE NumFactura=? AND TipoConcepto=?",
                c.getDescripcion(), c.getMontoCobro(), c.getEstado(), c.getNumFactura(), c.getTipoConcepto());
    }

    public int delete(int numFactura, String tipoConcepto) {
        return jdbc.update("DELETE FROM CONCEPTOCOBRO WHERE NumFactura=? AND TipoConcepto=?",
                numFactura, tipoConcepto);
    }
}
