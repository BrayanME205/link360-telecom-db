package com.link360.repository;

import com.link360.model.Consumo;
import com.link360.model.ConsumoVoz;
import com.link360.model.ConsumoSimple;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class ConsumoRepository {

    private final JdbcTemplate jdbc;

    public ConsumoRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Consumo> MAP = new RowMapper<Consumo>() {
        @Override
        public Consumo mapRow(ResultSet rs, int rowNum) throws SQLException {
            Consumo c = new Consumo();
            c.setCodConsumo(rs.getInt("CodConsumo"));
            c.setNumeroTelefono(rs.getString("NumeroTelefono"));
            c.setCodDescuento(rs.getObject("CodDescuento") != null ? rs.getInt("CodDescuento") : null);
            c.setFechaHoraInicio(rs.getTimestamp("FechaHoraInicio") != null ? rs.getTimestamp("FechaHoraInicio").toLocalDateTime() : null);
            c.setFechaHoraFin(rs.getTimestamp("FechaHoraFin") != null ? rs.getTimestamp("FechaHoraFin").toLocalDateTime() : null);
            c.setTipoConsumo(rs.getString("TipoConsumo"));
            c.setCantidad(rs.getBigDecimal("Cantidad"));
            c.setCostoCalculado(rs.getBigDecimal("CostoCalculado"));
            c.setAmbito(rs.getString("Ambito"));
            c.setEstado(rs.getString("Estado"));
            c.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
            c.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
            c.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
            c.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
            return c;
        }
    };

    private final RowMapper<ConsumoVoz> MAP_VOZ = new RowMapper<ConsumoVoz>() {
        @Override
        public ConsumoVoz mapRow(ResultSet rs, int rowNum) throws SQLException {
            ConsumoVoz v = new ConsumoVoz();
            v.setCodConsumo(rs.getInt("CodConsumo"));
            v.setDescripcion(rs.getString("Descripcion"));
            v.setNumDestino(rs.getString("NumDestino"));
            v.setDuracion(rs.getInt("Duracion"));
            v.setPaisDestino(rs.getString("PaisDestino"));
            v.setEstado(rs.getString("Estado"));
            v.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
            v.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
            v.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
            v.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
            return v;
        }
    };

    private final RowMapper<ConsumoSimple> MAP_SIMPLE = new RowMapper<ConsumoSimple>() {
        @Override
        public ConsumoSimple mapRow(ResultSet rs, int rowNum) throws SQLException {
            ConsumoSimple s = new ConsumoSimple();
            s.setCodConsumo(rs.getInt("CodConsumo"));
            s.setDescripcion(rs.getString("Descripcion"));
            s.setEstado(rs.getString("Estado"));
            s.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
            s.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
            s.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
            s.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
            return s;
        }
    };

    public List<Consumo> findAll() {
        return jdbc.query("SELECT * FROM CONSUMO ORDER BY FechaHoraInicio DESC", MAP);
    }

    public Optional<Consumo> findById(int id) {
        List<Consumo> r = jdbc.query("SELECT * FROM CONSUMO WHERE CodConsumo=?", MAP, id);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public List<Consumo> findAllForDropdown() {
        return jdbc.query("SELECT * FROM CONSUMO WHERE Estado='A' ORDER BY CodConsumo DESC", MAP);
    }

    public int insert(Consumo c) {
        return jdbc.update(
                "INSERT INTO CONSUMO(CodConsumo,NumeroTelefono,CodDescuento,FechaHoraInicio,FechaHoraFin,TipoConsumo,Cantidad,CostoCalculado,Ambito) VALUES(?,?,?,?,?,?,?,?,?)",
                c.getCodConsumo(), c.getNumeroTelefono(), c.getCodDescuento(),
                c.getFechaHoraInicio(), c.getFechaHoraFin(), c.getTipoConsumo(),
                c.getCantidad(), c.getCostoCalculado(), c.getAmbito());
    }

    public int update(Consumo c) {
        return jdbc.update(
                "UPDATE CONSUMO SET NumeroTelefono=?,CodDescuento=?,FechaHoraInicio=?,FechaHoraFin=?,TipoConsumo=?,Cantidad=?,CostoCalculado=?,Ambito=?,Estado=? WHERE CodConsumo=?",
                c.getNumeroTelefono(), c.getCodDescuento(), c.getFechaHoraInicio(),
                c.getFechaHoraFin(), c.getTipoConsumo(), c.getCantidad(),
                c.getCostoCalculado(), c.getAmbito(), c.getEstado(), c.getCodConsumo());
    }

    public int delete(int id) {
        return jdbc.update("DELETE FROM CONSUMO WHERE CodConsumo=?", id);
    }

    public List<ConsumoVoz> findAllVoz() {
        return jdbc.query("SELECT * FROM CONSUMO_VOZ ORDER BY CodConsumo DESC", MAP_VOZ);
    }

    public Optional<ConsumoVoz> findVozById(int id) {
        List<ConsumoVoz> r = jdbc.query("SELECT * FROM CONSUMO_VOZ WHERE CodConsumo=?", MAP_VOZ, id);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public int insertVoz(ConsumoVoz v) {
        return jdbc.update(
                "INSERT INTO CONSUMO_VOZ(CodConsumo,Descripcion,NumDestino,Duracion,PaisDestino) VALUES(?,?,?,?,?)",
                v.getCodConsumo(), v.getDescripcion(), v.getNumDestino(), v.getDuracion(), v.getPaisDestino());
    }

    public int updateVoz(ConsumoVoz v) {
        return jdbc.update(
                "UPDATE CONSUMO_VOZ SET Descripcion=?,NumDestino=?,Duracion=?,PaisDestino=?,Estado=? WHERE CodConsumo=?",
                v.getDescripcion(), v.getNumDestino(), v.getDuracion(), v.getPaisDestino(), v.getEstado(), v.getCodConsumo());
    }

    public int deleteVoz(int id) {
        return jdbc.update("DELETE FROM CONSUMO_VOZ WHERE CodConsumo=?", id);
    }

    public List<ConsumoSimple> findAllSms() {
        return jdbc.query("SELECT * FROM CONSUMO_SMS ORDER BY CodConsumo DESC", MAP_SIMPLE);
    }

    public Optional<ConsumoSimple> findSmsById(int id) {
        List<ConsumoSimple> r = jdbc.query("SELECT * FROM CONSUMO_SMS WHERE CodConsumo=?", MAP_SIMPLE, id);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public int insertSms(ConsumoSimple s) {
        return jdbc.update("INSERT INTO CONSUMO_SMS(CodConsumo,Descripcion) VALUES(?,?)",
                s.getCodConsumo(), s.getDescripcion());
    }

    public int updateSms(ConsumoSimple s) {
        return jdbc.update("UPDATE CONSUMO_SMS SET Descripcion=?,Estado=? WHERE CodConsumo=?",
                s.getDescripcion(), s.getEstado(), s.getCodConsumo());
    }

    public int deleteSms(int id) {
        return jdbc.update("DELETE FROM CONSUMO_SMS WHERE CodConsumo=?", id);
    }

    public List<ConsumoSimple> findAllDatos() {
        return jdbc.query("SELECT * FROM CONSUMO_DATOS ORDER BY CodConsumo DESC", MAP_SIMPLE);
    }

    public Optional<ConsumoSimple> findDatosById(int id) {
        List<ConsumoSimple> r = jdbc.query("SELECT * FROM CONSUMO_DATOS WHERE CodConsumo=?", MAP_SIMPLE, id);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public int insertDatos(ConsumoSimple s) {
        return jdbc.update("INSERT INTO CONSUMO_DATOS(CodConsumo,Descripcion) VALUES(?,?)",
                s.getCodConsumo(), s.getDescripcion());
    }

    public int updateDatos(ConsumoSimple s) {
        return jdbc.update("UPDATE CONSUMO_DATOS SET Descripcion=?,Estado=? WHERE CodConsumo=?",
                s.getDescripcion(), s.getEstado(), s.getCodConsumo());
    }

    public int deleteDatos(int id) {
        return jdbc.update("DELETE FROM CONSUMO_DATOS WHERE CodConsumo=?", id);
    }

    public List<ConsumoSimple> findAllRoaming() {
        return jdbc.query("SELECT * FROM CONSUMO_ROAMING ORDER BY CodConsumo DESC", MAP_SIMPLE);
    }

    public Optional<ConsumoSimple> findRoamingById(int id) {
        List<ConsumoSimple> r = jdbc.query("SELECT * FROM CONSUMO_ROAMING WHERE CodConsumo=?", MAP_SIMPLE, id);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public int insertRoaming(ConsumoSimple s) {
        return jdbc.update("INSERT INTO CONSUMO_ROAMING(CodConsumo,Descripcion) VALUES(?,?)",
                s.getCodConsumo(), s.getDescripcion());
    }

    public int updateRoaming(ConsumoSimple s) {
        return jdbc.update("UPDATE CONSUMO_ROAMING SET Descripcion=?,Estado=? WHERE CodConsumo=?",
                s.getDescripcion(), s.getEstado(), s.getCodConsumo());
    }

    public int deleteRoaming(int id) {
        return jdbc.update("DELETE FROM CONSUMO_ROAMING WHERE CodConsumo=?", id);
    }
}
