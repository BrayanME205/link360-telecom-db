package com.link360.repository;

import com.link360.model.PuntosFidelizacion;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class PuntosFidelizacionRepository {

    private final JdbcTemplate jdbc;

    public PuntosFidelizacionRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<PuntosFidelizacion> MAP = new RowMapper<PuntosFidelizacion>() {
        @Override
        public PuntosFidelizacion mapRow(ResultSet rs, int rowNum) throws SQLException {
            PuntosFidelizacion p = new PuntosFidelizacion();
            p.setCedula(rs.getString("Cedula"));
            p.setNumFactura(rs.getInt("NumFactura"));
            p.setPuntosAsignados(rs.getInt("PuntosAsignados"));
            p.setSaldoActual(rs.getInt("SaldoActual"));
            p.setEstado(rs.getString("Estado"));
            p.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
            p.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
            p.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
            p.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
            try {
                p.setNombreCliente(rs.getString("NombreCliente"));
            } catch (Exception ignored) {
            }
            return p;
        }
    };

    public List<PuntosFidelizacion> findAll() {
        return jdbc.query(
                "SELECT pf.*, c.Nombre + ' ' + c.PrimerApellido AS NombreCliente "
                + "FROM PUNTOSFIDELIZACION pf INNER JOIN CLIENTE c ON pf.Cedula = c.Cedula "
                + "ORDER BY pf.Cedula, pf.NumFactura DESC", MAP);
    }

    public Optional<PuntosFidelizacion> findById(String cedula, int numFactura) {
        List<PuntosFidelizacion> r = jdbc.query(
                "SELECT pf.*, c.Nombre + ' ' + c.PrimerApellido AS NombreCliente "
                + "FROM PUNTOSFIDELIZACION pf INNER JOIN CLIENTE c ON pf.Cedula = c.Cedula "
                + "WHERE pf.Cedula=? AND pf.NumFactura=?", MAP, cedula, numFactura);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public int insert(PuntosFidelizacion p) {
        return jdbc.update(
                "INSERT INTO PUNTOSFIDELIZACION(Cedula,NumFactura,PuntosAsignados,SaldoActual) VALUES(?,?,?,?)",
                p.getCedula(), p.getNumFactura(), p.getPuntosAsignados(), p.getSaldoActual());
    }

    public int update(PuntosFidelizacion p) {
        return jdbc.update(
                "UPDATE PUNTOSFIDELIZACION SET PuntosAsignados=?,SaldoActual=?,Estado=? WHERE Cedula=? AND NumFactura=?",
                p.getPuntosAsignados(), p.getSaldoActual(), p.getEstado(), p.getCedula(), p.getNumFactura());
    }

    public int delete(String cedula, int numFactura) {
        return jdbc.update("DELETE FROM PUNTOSFIDELIZACION WHERE Cedula=? AND NumFactura=?", cedula, numFactura);
    }

    public boolean hasRedenciones(String cedula, int numFactura) {
        Integer n = jdbc.queryForObject(
                "SELECT COUNT(*) FROM REDENCIONPUNTOS WHERE Cedula=? AND NumFactura=?",
                Integer.class, cedula, numFactura);
        return n != null && n > 0;
    }
}
