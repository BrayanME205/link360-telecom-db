package com.link360.controller;

import com.link360.exception.DatabaseException;
import com.link360.model.LineaMovil;
import com.link360.service.LineaMovilService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/lineas")
public class LineaMovilController {

    private final LineaMovilService service;
    public LineaMovilController(LineaMovilService service) { this.service = service; }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("lineas", service.getAll());
        model.addAttribute("activeMenu", "lineas");
        return "linea/list";
    }

    @GetMapping("/nuevo")
    public String newForm(Model model) {
        model.addAttribute("linea", new LineaMovil());
        model.addAttribute("clientes", service.getClientesForDropdown());
        model.addAttribute("modoEdicion", false);
        model.addAttribute("activeMenu", "lineas");
        return "linea/form";
    }

    @PostMapping("/nuevo")
    public String create(@ModelAttribute LineaMovil linea, RedirectAttributes ra) {
        try {
            service.create(linea);
            ra.addFlashAttribute("successMsg", "Línea móvil registrada correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/lineas/nuevo";
        }
        return "redirect:/lineas";
    }

    @GetMapping("/editar/{id}")
    public String editForm(@PathVariable String id, Model model, RedirectAttributes ra) {
        return service.getById(id).map(l -> {
            model.addAttribute("linea", l);
            model.addAttribute("clientes", service.getClientesForDropdown());
            model.addAttribute("modoEdicion", true);
            model.addAttribute("activeMenu", "lineas");
            return "linea/form";
        }).orElseGet(() -> { ra.addFlashAttribute("errorMsg", "Línea no encontrada."); return "redirect:/lineas"; });
    }

    @PostMapping("/editar/{id}")
    public String update(@PathVariable String id, @ModelAttribute LineaMovil linea, RedirectAttributes ra) {
        linea.setNumeroTelefono(id);
        try {
            service.update(linea);
            ra.addFlashAttribute("successMsg", "Línea actualizada correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/lineas/editar/" + id;
        }
        return "redirect:/lineas";
    }

    @GetMapping("/eliminar/{id}")
    public String deleteWarning(@PathVariable String id, Model model, RedirectAttributes ra) {
        return service.getById(id).map(l -> {
            model.addAttribute("linea", l);
            model.addAttribute("cascadeWarning", service.getCascadeWarning(id));
            model.addAttribute("activeMenu", "lineas");
            return "linea/delete-confirm";
        }).orElseGet(() -> { ra.addFlashAttribute("errorMsg", "Línea no encontrada."); return "redirect:/lineas"; });
    }

    @PostMapping("/eliminar/{id}")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("successMsg", "Línea eliminada correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/lineas";
    }
}