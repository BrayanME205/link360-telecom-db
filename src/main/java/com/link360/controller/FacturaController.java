package com.link360.controller;

import com.link360.exception.DatabaseException;
import com.link360.model.Factura;
import com.link360.service.FacturaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/facturas")
public class FacturaController {

    private final FacturaService service;

    public FacturaController(FacturaService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("facturas", service.getAll());
        model.addAttribute("activeMenu", "facturas");
        return "factura/list";
    }

    @GetMapping("/nuevo")
    public String newForm(Model model) {
        model.addAttribute("factura", new Factura());
        model.addAttribute("lineas", service.getLineasForDropdown());
        model.addAttribute("modoEdicion", false);
        model.addAttribute("activeMenu", "facturas");
        return "factura/form";
    }

    @PostMapping("/nuevo")
    public String create(@ModelAttribute Factura factura, RedirectAttributes ra) {
        try {
            service.create(factura);
            ra.addFlashAttribute("successMsg", "Factura registrada correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/facturas/nuevo";
        }
        return "redirect:/facturas";
    }

    @GetMapping("/editar/{id}")
    public String editForm(@PathVariable int id, Model model, RedirectAttributes ra) {
        return service.getById(id).map(f -> {
            model.addAttribute("factura", f);
            model.addAttribute("lineas", service.getLineasForDropdown());
            model.addAttribute("modoEdicion", true);
            model.addAttribute("activeMenu", "facturas");
            return "factura/form";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Factura no encontrada.");
            return "redirect:/facturas";
        });
    }

    @PostMapping("/editar/{id}")
    public String update(@PathVariable int id, @ModelAttribute Factura factura, RedirectAttributes ra) {
        factura.setNumFactura(id);
        try {
            service.update(factura);
            ra.addFlashAttribute("successMsg", "Factura actualizada correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/facturas/editar/" + id;
        }
        return "redirect:/facturas";
    }

    @GetMapping("/eliminar/{id}")
    public String deleteWarning(@PathVariable int id, Model model, RedirectAttributes ra) {
        return service.getById(id).map(f -> {
            model.addAttribute("factura", f);
            model.addAttribute("cascadeWarning", service.getCascadeWarning(id));
            model.addAttribute("activeMenu", "facturas");
            return "factura/delete-confirm";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Factura no encontrada.");
            return "redirect:/facturas";
        });
    }

    @PostMapping("/eliminar/{id}")
    public String delete(@PathVariable int id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("successMsg", "Factura eliminada correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/facturas";
    }
}
