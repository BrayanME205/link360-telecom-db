package com.link360.repository;

import com.link360.model.Factura;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class FacturaRepository {

    private final JdbcTemplate jdbc;
    public FacturaRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

    private final RowMapper<Factura> MAP = new RowMapper<Factura>() {
        @Override
        public Factura mapRow(ResultSet rs, int rowNum) throws SQLException {
            Factura f = new Factura();
            f.setNumFactura(rs.getInt("NumFactura"));
            f.setNumeroTelefono(rs.getString("NumeroTelefono"));
            f.setFecha(rs.getDate("Fecha") != null ? rs.getDate("Fecha").toLocalDate() : null);
            f.setFechaVencimiento(rs.getDate("FechaVencimiento") != null ? rs.getDate("FechaVencimiento").toLocalDate() : null);
            f.setMonto(rs.getBigDecimal("Monto"));
            f.setImpuestos(rs.getBigDecimal("Impuestos"));
            f.setDescuentos(rs.getBigDecimal("Descuentos"));
            f.setPuntosRedimidos(rs.getBigDecimal("PuntosRedimidos"));
            f.setMontoFinal(rs.getBigDecimal("MontoFinal"));
            f.setFechaPago(rs.getDate("FechaPago") != null ? rs.getDate("FechaPago").toLocalDate() : null);
            f.setEstadoPago(rs.getString("EstadoPago"));
            f.setEstado(rs.getString("Estado"));
            f.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
            f.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
            f.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
            f.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
            return f;
        }
    };

    public List<Factura> findAll() {
        return jdbc.query("SELECT * FROM FACTURA ORDER BY Fecha DESC", MAP);
    }

    public Optional<Factura> findById(int id) {
        List<Factura> r = jdbc.query("SELECT * FROM FACTURA WHERE NumFactura=?", MAP, id);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public List<Factura> findAllForDropdown() {
        return jdbc.query("SELECT * FROM FACTURA WHERE Estado='A' ORDER BY NumFactura DESC", MAP);
    }

    public int insert(Factura f) {
        return jdbc.update(
            "INSERT INTO FACTURA(NumFactura,NumeroTelefono,Fecha,FechaVencimiento," +
            "Monto,Impuestos,Descuentos,PuntosRedimidos,MontoFinal,FechaPago,EstadoPago) " +
            "VALUES(?,?,?,?,?,?,?,?,?,?,?)",
            f.getNumFactura(), f.getNumeroTelefono(), f.getFecha(), f.getFechaVencimiento(),
            f.getMonto(), f.getImpuestos(), f.getDescuentos(), f.getPuntosRedimidos(),
            f.getMontoFinal(), f.getFechaPago(), f.getEstadoPago());
    }

    public int update(Factura f) {
        return jdbc.update(
            "UPDATE FACTURA SET NumeroTelefono=?,Fecha=?,FechaVencimiento=?,Monto=?," +
            "Impuestos=?,Descuentos=?,PuntosRedimidos=?,MontoFinal=?,FechaPago=?," +
            "EstadoPago=?,Estado=? WHERE NumFactura=?",
            f.getNumeroTelefono(), f.getFecha(), f.getFechaVencimiento(), f.getMonto(),
            f.getImpuestos(), f.getDescuentos(), f.getPuntosRedimidos(), f.getMontoFinal(),
            f.getFechaPago(), f.getEstadoPago(), f.getEstado(), f.getNumFactura());
    }

    public int delete(int id) {
        return jdbc.update("DELETE FROM FACTURA WHERE NumFactura=?", id);
    }

    public boolean hasConceptos(int id) {
        Integer n = jdbc.queryForObject(
            "SELECT COUNT(*) FROM CONCEPTOCOBRO WHERE NumFactura=?", Integer.class, id);
        return n != null && n > 0;
    }
}