package com.link360.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class ConsultasRepository {

    private final JdbcTemplate jdbc;

    public ConsultasRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    public List<Map<String, Object>> resumenMensualCliente() {
        return jdbc.queryForList("SELECT * FROM VW_RESUMEN_MENSUAL_CLIENTE");
    }

    public List<Map<String, Object>> consumoPorLinea() {
        return jdbc.queryForList(
                "SELECT l.NumeroTelefono, c.Nombre + ' ' + c.PrimerApellido AS Cliente, "
                + "COUNT(co.CodConsumo) AS TotalConsumos, "
                + "SUM(co.CostoCalculado) AS CostoTotal, "
                + "AVG(co.CostoCalculado) AS CostoPromedio, "
                + "l.TipoLinea, l.Tecnologia "
                + "FROM LINEAMOVIL l "
                + "INNER JOIN CLIENTE c ON l.Cedula = c.Cedula "
                + "LEFT JOIN CONSUMO co ON l.NumeroTelefono = co.NumeroTelefono "
                + "GROUP BY l.NumeroTelefono, c.Nombre, c.PrimerApellido, l.TipoLinea, l.Tecnologia "
                + "ORDER BY CostoTotal DESC");
    }

    public List<Map<String, Object>> facturasPendientes() {
        return jdbc.queryForList(
                "SELECT f.NumFactura, c.Nombre + ' ' + c.PrimerApellido AS Cliente, c.TipoCliente, "
                + "l.NumeroTelefono, l.TipoLinea, "
                + "f.Fecha, f.FechaVencimiento, f.MontoFinal, f.EstadoPago, "
                + "DATEDIFF(day, f.FechaVencimiento, GETDATE()) AS DiasVencida "
                + "FROM FACTURA f "
                + "INNER JOIN LINEAMOVIL l ON f.NumeroTelefono = l.NumeroTelefono "
                + "INNER JOIN CLIENTE c ON l.Cedula = c.Cedula "
                + "WHERE f.EstadoPago IN ('Pendiente','Vencida') AND f.Estado = 'A' "
                + "ORDER BY DiasVencida DESC");
    }

    public List<Map<String, Object>> planesActivosConPromociones() {
        return jdbc.queryForList(
                "SELECT l.NumeroTelefono, c.Nombre + ' ' + c.PrimerApellido AS Cliente, "
                + "p.Nombre AS Plan, p.CuotaMensual, cat.Descripcion AS Categoria, cat.VelocidadMaxima, "
                + "lp.FechaInicio AS InicioContrato, "
                + "STRING_AGG(pr.Nombre, ', ') AS Promociones "
                + "FROM LINEAPLAN lp "
                + "INNER JOIN LINEAMOVIL l ON lp.NumeroTelefono = l.NumeroTelefono "
                + "INNER JOIN CLIENTE c ON l.Cedula = c.Cedula "
                + "INNER JOIN [PLAN] p ON lp.CodPlan = p.CodPlan "
                + "INNER JOIN CATEGORIACOMERCIAL cat ON p.CodCategoria = cat.CodCategoria "
                + "LEFT JOIN PROMOPLAN pp ON p.CodPlan = pp.CodPlan "
                + "LEFT JOIN PROMOCION pr ON pp.CodPromocion = pr.CodPromocion AND pr.Estado = 'A' "
                + "WHERE lp.FechaFin IS NULL AND lp.Estado = 'A' "
                + "GROUP BY l.NumeroTelefono, c.Nombre, c.PrimerApellido, p.Nombre, p.CuotaMensual, "
                + "cat.Descripcion, cat.VelocidadMaxima, lp.FechaInicio "
                + "ORDER BY Cliente");
    }

    public List<Map<String, Object>> topClientesPorPuntos() {
        return jdbc.queryForList(
                "SELECT TOP 10 c.Cedula, c.Nombre + ' ' + c.PrimerApellido AS Cliente, c.TipoCliente, "
                + "SUM(pf.PuntosAsignados) AS TotalPuntosAsignados, "
                + "MAX(pf.SaldoActual) AS SaldoActual, "
                + "COUNT(pf.NumFactura) AS CantidadFacturas, "
                + "FORMAT(SUM(pf.PuntosAsignados), 'N0') AS PuntosFormateados "
                + "FROM PUNTOSFIDELIZACION pf "
                + "INNER JOIN CLIENTE c ON pf.Cedula = c.Cedula "
                + "WHERE pf.Estado = 'A' "
                + "GROUP BY c.Cedula, c.Nombre, c.PrimerApellido, c.TipoCliente "
                + "ORDER BY TotalPuntosAsignados DESC");
    }

    public List<Map<String, Object>> consumosVozInternacionales() {
        return jdbc.queryForList(
                "SELECT co.CodConsumo, l.NumeroTelefono, c.Nombre + ' ' + c.PrimerApellido AS Cliente, "
                + "cv.PaisDestino, cv.NumDestino, cv.Duracion, "
                + "co.CostoCalculado, co.Ambito, co.FechaHoraInicio, "
                + "d.Descripcion AS DescuentoAplicado "
                + "FROM CONSUMO_VOZ cv "
                + "INNER JOIN CONSUMO co ON cv.CodConsumo = co.CodConsumo "
                + "INNER JOIN LINEAMOVIL l ON co.NumeroTelefono = l.NumeroTelefono "
                + "INNER JOIN CLIENTE c ON l.Cedula = c.Cedula "
                + "LEFT JOIN DESCUENTO d ON co.CodDescuento = d.CodDescuento "
                + "WHERE co.Ambito <> 'Nacional' "
                + "ORDER BY co.FechaHoraInicio DESC");
    }

    public List<Map<String, Object>> auditoria() {
        return jdbc.queryForList(
                "SELECT 'CLIENTE' AS Tabla, Cedula AS Identificador, "
                + "Nombre + ' ' + PrimerApellido AS Descripcion, "
                + "UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion, Estado "
                + "FROM CLIENTE WHERE FechaModificacion IS NOT NULL "
                + "UNION ALL "
                + "SELECT 'LINEAMOVIL', NumeroTelefono, 'Línea ' + TipoLinea, "
                + "UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion, Estado "
                + "FROM LINEAMOVIL WHERE FechaModificacion IS NOT NULL "
                + "UNION ALL "
                + "SELECT 'FACTURA', CAST(NumFactura AS VARCHAR), 'Factura $' + CAST(MontoFinal AS VARCHAR), "
                + "UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion, Estado "
                + "FROM FACTURA WHERE FechaModificacion IS NOT NULL "
                + "UNION ALL "
                + "SELECT 'PROMOCION', CAST(CodPromocion AS VARCHAR), Nombre, "
                + "UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion, Estado "
                + "FROM PROMOCION WHERE FechaModificacion IS NOT NULL "
                + "ORDER BY FechaModificacion DESC");
    }

    public List<Map<String, Object>> auditoriaCompleta() {
        return jdbc.queryForList(
                "SELECT 'CLIENTE' AS Tabla, Cedula AS Identificador, "
                + "Nombre + ' ' + PrimerApellido AS Descripcion, "
                + "UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion, Estado "
                + "FROM CLIENTE "
                + "UNION ALL "
                + "SELECT 'LINEAMOVIL', NumeroTelefono, 'Línea ' + TipoLinea, "
                + "UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion, Estado "
                + "FROM LINEAMOVIL "
                + "UNION ALL "
                + "SELECT 'FACTURA', CAST(NumFactura AS VARCHAR), 'Factura $' + CAST(MontoFinal AS VARCHAR), "
                + "UsuarioCreacion, FechaCreacion, UsuarioModificacion, FechaModificacion, Estado "
                + "FROM FACTURA "
                + "ORDER BY FechaCreacion DESC");
    }
}
