package com.link360.controller;

import com.link360.service.ConsultasService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/consultas")
public class ConsultasController {

    private final ConsultasService service;

    public ConsultasController(ConsultasService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("activeMenu", "consultas");
        return "consultas/index";
    }

    @GetMapping("/resumen-mensual")
    public String resumenMensual(Model model) {
        model.addAttribute("resultados", service.getResumenMensual());
        model.addAttribute("activeMenu", "consultas");
        model.addAttribute("titulo", "Resumen Mensual del Cliente (Vista VW_RESUMEN_MENSUAL_CLIENTE)");
        model.addAttribute("descripcion", "Consolida en una sola consulta los datos de cliente, línea, plan, categoría comercial, última factura y puntos de fidelización. Responde la pregunta de negocio: '¿Cuál es el estado de cuenta integral de cada cliente?'");
        return "consultas/resultado";
    }

    @GetMapping("/consumo-por-linea")
    public String consumoPorLinea(Model model) {
        model.addAttribute("resultados", service.getConsumoPorLinea());
        model.addAttribute("activeMenu", "consultas");
        model.addAttribute("titulo", "Consumo Total por Línea");
        model.addAttribute("descripcion", "Usa SUM, AVG y COUNT (operadores estudiados en clase) para totalizar el costo de consumos por línea móvil, agrupado con JOIN entre LINEAMOVIL, CLIENTE y CONSUMO.");
        return "consultas/resultado";
    }

    @GetMapping("/facturas-pendientes")
    public String facturasPendientes(Model model) {
        model.addAttribute("resultados", service.getFacturasPendientes());
        model.addAttribute("activeMenu", "consultas");
        model.addAttribute("titulo", "Facturas Pendientes o Vencidas");
        model.addAttribute("descripcion", "Usa DATEDIFF para calcular días de atraso. INNER JOIN entre FACTURA, LINEAMOVIL y CLIENTE.");
        return "consultas/resultado";
    }

    @GetMapping("/planes-promociones")
    public String planesConPromociones(Model model) {
        model.addAttribute("resultados", service.getPlanesConPromociones());
        model.addAttribute("activeMenu", "consultas");
        model.addAttribute("titulo", "Planes Activos con Promociones Aplicadas");
        model.addAttribute("descripcion", "Usa STRING_AGG (operador NO estudiado en clase) para concatenar todas las promociones de un plan en una sola celda. Múltiples JOINs entre LINEAPLAN, LINEAMOVIL, CLIENTE, PLAN, CATEGORIACOMERCIAL, PROMOPLAN y PROMOCION.");
        return "consultas/resultado";
    }

    @GetMapping("/top-puntos")
    public String topClientesPuntos(Model model) {
        model.addAttribute("resultados", service.getTopClientesPuntos());
        model.addAttribute("activeMenu", "consultas");
        model.addAttribute("titulo", "Top 10 Clientes por Puntos de Fidelización");
        model.addAttribute("descripcion", "Usa FORMAT (operador NO estudiado en clase) para formatear números con separador de miles, junto con SUM, MAX y COUNT.");
        return "consultas/resultado";
    }

    @GetMapping("/voz-internacional")
    public String consumosVozInternacionales(Model model) {
        model.addAttribute("resultados", service.getConsumosVozInternacionales());
        model.addAttribute("activeMenu", "consultas");
        model.addAttribute("titulo", "Consumos de Voz Internacionales y Roaming");
        model.addAttribute("descripcion", "Consulta sobre la especialización CONSUMO_VOZ con LEFT JOIN hacia DESCUENTO para mostrar si el consumo tuvo descuento aplicado.");
        return "consultas/resultado";
    }

    @GetMapping("/auditoria")
    public String auditoria(Model model) {
        model.addAttribute("resultados", service.getAuditoriaCompleta());
        model.addAttribute("activeMenu", "auditoria");
        model.addAttribute("titulo", "Pistas de Auditoría");
        model.addAttribute("descripcion", "Consulta UNION ALL que consolida los registros de auditoría (usuario y fecha de creación/modificación) de las tablas CLIENTE, LINEAMOVIL y FACTURA en una sola vista.");
        return "consultas/auditoria";
    }
}
