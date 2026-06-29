package com.link360.repository;

import com.link360.model.Plan;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public class PlanRepository {

    private final JdbcTemplate jdbc;

    public PlanRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private final RowMapper<Plan> MAP = new RowMapper<Plan>() {
        @Override
        public Plan mapRow(ResultSet rs, int rowNum) throws SQLException {
            Plan p = new Plan();
            p.setCodPlan(rs.getInt("CodPlan"));
            p.setCodCategoria(rs.getInt("CodCategoria"));
            p.setNombre(rs.getString("Nombre"));
            p.setDescripcion(rs.getString("Descripcion"));
            p.setCuotaMensual(rs.getBigDecimal("CuotaMensual"));
            p.setGb(rs.getBigDecimal("GB"));
            p.setMinutos(rs.getInt("Minutos"));
            p.setMensajes(rs.getInt("Mensajes"));
            p.setCostoExceso(rs.getBigDecimal("CostoExceso"));
            p.setEstado(rs.getString("Estado"));
            p.setUsuarioCreacion(rs.getString("UsuarioCreacion"));
            p.setFechaCreacion(rs.getTimestamp("FechaCreacion") != null ? rs.getTimestamp("FechaCreacion").toLocalDateTime() : null);
            p.setUsuarioModificacion(rs.getString("UsuarioModificacion"));
            p.setFechaModificacion(rs.getTimestamp("FechaModificacion") != null ? rs.getTimestamp("FechaModificacion").toLocalDateTime() : null);
            try {
                p.setNombreCategoria(rs.getString("NombreCategoria"));
            } catch (Exception ignored) {
            }
            return p;
        }
    };

    public List<Plan> findAll() {
        return jdbc.query(
                "SELECT p.*, c.Descripcion AS NombreCategoria "
                + "FROM [PLAN] p INNER JOIN CATEGORIACOMERCIAL c ON p.CodCategoria = c.CodCategoria "
                + "ORDER BY p.Nombre", MAP);
    }

    public Optional<Plan> findById(int id) {
        List<Plan> r = jdbc.query(
                "SELECT p.*, c.Descripcion AS NombreCategoria "
                + "FROM [PLAN] p INNER JOIN CATEGORIACOMERCIAL c ON p.CodCategoria = c.CodCategoria "
                + "WHERE p.CodPlan=?", MAP, id);
        return r.isEmpty() ? Optional.empty() : Optional.of(r.get(0));
    }

    public List<Plan> findAllForDropdown() {
        return jdbc.query(
                "SELECT p.*, c.Descripcion AS NombreCategoria "
                + "FROM [PLAN] p INNER JOIN CATEGORIACOMERCIAL c ON p.CodCategoria = c.CodCategoria "
                + "WHERE p.Estado='A' ORDER BY p.Nombre", MAP);
    }

    public int insert(Plan p) {
        return jdbc.update(
                "INSERT INTO [PLAN](CodPlan,CodCategoria,Nombre,Descripcion,CuotaMensual,GB,Minutos,Mensajes,CostoExceso) VALUES(?,?,?,?,?,?,?,?,?)",
                p.getCodPlan(), p.getCodCategoria(), p.getNombre(), p.getDescripcion(),
                p.getCuotaMensual(), p.getGb(), p.getMinutos(), p.getMensajes(), p.getCostoExceso());
    }

    public int update(Plan p) {
        return jdbc.update(
                "UPDATE [PLAN] SET CodCategoria=?,Nombre=?,Descripcion=?,CuotaMensual=?,GB=?,Minutos=?,Mensajes=?,CostoExceso=?,Estado=? WHERE CodPlan=?",
                p.getCodCategoria(), p.getNombre(), p.getDescripcion(), p.getCuotaMensual(),
                p.getGb(), p.getMinutos(), p.getMensajes(), p.getCostoExceso(), p.getEstado(), p.getCodPlan());
    }

    public int delete(int id) {
        return jdbc.update("DELETE FROM [PLAN] WHERE CodPlan=?", id);
    }

    public boolean hasLineas(int id) {
        Integer n = jdbc.queryForObject("SELECT COUNT(*) FROM LINEAPLAN WHERE CodPlan=?", Integer.class, id);
        return n != null && n > 0;
    }
}
