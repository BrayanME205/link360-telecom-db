package com.link360.controller;

import com.link360.exception.DatabaseException;
import com.link360.model.Descuento;
import com.link360.service.DescuentoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/descuentos")
public class DescuentoController {

    private final DescuentoService service;

    public DescuentoController(DescuentoService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("descuentos", service.getAll());
        model.addAttribute("activeMenu", "descuentos");
        return "descuento/list";
    }

    @GetMapping("/nuevo")
    public String newForm(Model model) {
        model.addAttribute("descuento", new Descuento());
        model.addAttribute("modoEdicion", false);
        model.addAttribute("activeMenu", "descuentos");
        return "descuento/form";
    }

    @PostMapping("/nuevo")
    public String create(@ModelAttribute Descuento descuento, RedirectAttributes ra) {
        try {
            service.create(descuento);
            ra.addFlashAttribute("successMsg", "Descuento registrado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/descuentos/nuevo";
        }
        return "redirect:/descuentos";
    }

    @GetMapping("/editar/{id}")
    public String editForm(@PathVariable int id, Model model, RedirectAttributes ra) {
        return service.getById(id).map(d -> {
            model.addAttribute("descuento", d);
            model.addAttribute("modoEdicion", true);
            model.addAttribute("activeMenu", "descuentos");
            return "descuento/form";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Descuento no encontrado.");
            return "redirect:/descuentos";
        });
    }

    @PostMapping("/editar/{id}")
    public String update(@PathVariable int id, @ModelAttribute Descuento descuento, RedirectAttributes ra) {
        descuento.setCodDescuento(id);
        try {
            service.update(descuento);
            ra.addFlashAttribute("successMsg", "Descuento actualizado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/descuentos/editar/" + id;
        }
        return "redirect:/descuentos";
    }

    @GetMapping("/eliminar/{id}")
    public String deleteWarning(@PathVariable int id, Model model, RedirectAttributes ra) {
        return service.getById(id).map(d -> {
            model.addAttribute("descuento", d);
            model.addAttribute("cascadeWarning", service.getDeleteWarning(id));
            model.addAttribute("activeMenu", "descuentos");
            return "descuento/delete-confirm";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Descuento no encontrado.");
            return "redirect:/descuentos";
        });
    }

    @PostMapping("/eliminar/{id}")
    public String delete(@PathVariable int id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("successMsg", "Descuento eliminado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/descuentos";
    }
}
