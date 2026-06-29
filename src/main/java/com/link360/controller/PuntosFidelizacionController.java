package com.link360.controller;

import com.link360.exception.DatabaseException;
import com.link360.model.PuntosFidelizacion;
import com.link360.service.PuntosFidelizacionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/puntos")
public class PuntosFidelizacionController {

    private final PuntosFidelizacionService service;

    public PuntosFidelizacionController(PuntosFidelizacionService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("puntos", service.getAll());
        model.addAttribute("activeMenu", "puntos");
        return "puntos/list";
    }

    @GetMapping("/nuevo")
    public String newForm(Model model) {
        model.addAttribute("punto", new PuntosFidelizacion());
        model.addAttribute("clientes", service.getClientesForDropdown());
        model.addAttribute("facturas", service.getFacturasForDropdown());
        model.addAttribute("modoEdicion", false);
        model.addAttribute("activeMenu", "puntos");
        return "puntos/form";
    }

    @PostMapping("/nuevo")
    public String create(@ModelAttribute PuntosFidelizacion punto, RedirectAttributes ra) {
        try {
            service.create(punto);
            ra.addFlashAttribute("successMsg", "Puntos registrados correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/puntos/nuevo";
        }
        return "redirect:/puntos";
    }

    @GetMapping("/editar/{cedula}/{numFactura}")
    public String editForm(@PathVariable String cedula, @PathVariable int numFactura,
            Model model, RedirectAttributes ra) {
        return service.getById(cedula, numFactura).map(p -> {
            model.addAttribute("punto", p);
            model.addAttribute("clientes", service.getClientesForDropdown());
            model.addAttribute("facturas", service.getFacturasForDropdown());
            model.addAttribute("modoEdicion", true);
            model.addAttribute("activeMenu", "puntos");
            return "puntos/form";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Registro no encontrado.");
            return "redirect:/puntos";
        });
    }

    @PostMapping("/editar/{cedula}/{numFactura}")
    public String update(@PathVariable String cedula, @PathVariable int numFactura,
            @ModelAttribute PuntosFidelizacion punto, RedirectAttributes ra) {
        punto.setCedula(cedula);
        punto.setNumFactura(numFactura);
        try {
            service.update(punto);
            ra.addFlashAttribute("successMsg", "Puntos actualizados correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/puntos";
    }

    @GetMapping("/eliminar/{cedula}/{numFactura}")
    public String deleteWarning(@PathVariable String cedula, @PathVariable int numFactura,
            Model model, RedirectAttributes ra) {
        return service.getById(cedula, numFactura).map(p -> {
            model.addAttribute("punto", p);
            model.addAttribute("cascadeWarning", service.getCascadeWarning(cedula, numFactura));
            model.addAttribute("activeMenu", "puntos");
            return "puntos/delete-confirm";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Registro no encontrado.");
            return "redirect:/puntos";
        });
    }

    @PostMapping("/eliminar/{cedula}/{numFactura}")
    public String delete(@PathVariable String cedula, @PathVariable int numFactura, RedirectAttributes ra) {
        try {
            service.delete(cedula, numFactura);
            ra.addFlashAttribute("successMsg", "Registro de puntos eliminado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/puntos";
    }
}
