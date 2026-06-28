package com.link360.controller;

import com.link360.exception.DatabaseException;
import com.link360.model.Direccion;
import com.link360.service.DireccionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/direcciones")
public class DireccionController {

    private final DireccionService service;

    public DireccionController(DireccionService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("direcciones", service.getAll());
        model.addAttribute("activeMenu", "direcciones");
        return "direccion/list";
    }

    @GetMapping("/nuevo")
    public String newForm(Model model) {
        model.addAttribute("direccion", new Direccion());
        model.addAttribute("clientes", service.getClientesForDropdown());
        model.addAttribute("modoEdicion", false);
        model.addAttribute("activeMenu", "direcciones");
        return "direccion/form";
    }

    @PostMapping("/nuevo")
    public String create(@ModelAttribute Direccion direccion, RedirectAttributes ra) {
        try {
            service.create(direccion);
            ra.addFlashAttribute("successMsg", "Dirección registrada correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/direcciones/nuevo";
        }
        return "redirect:/direcciones";
    }

    @GetMapping("/editar/{cedula}")
    public String editForm(@PathVariable String cedula, Model model, RedirectAttributes ra) {
        return service.getById(cedula).map(d -> {
            model.addAttribute("direccion", d);
            model.addAttribute("clientes", service.getClientesForDropdown());
            model.addAttribute("modoEdicion", true);
            model.addAttribute("activeMenu", "direcciones");
            return "direccion/form";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Dirección no encontrada.");
            return "redirect:/direcciones";
        });
    }

    @PostMapping("/editar/{cedula}")
    public String update(@PathVariable String cedula, @ModelAttribute Direccion direccion, RedirectAttributes ra) {
        direccion.setCedula(cedula);
        try {
            service.update(direccion);
            ra.addFlashAttribute("successMsg", "Dirección actualizada correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/direcciones/editar/" + cedula;
        }
        return "redirect:/direcciones";
    }

    @GetMapping("/eliminar/{cedula}")
    public String deleteWarning(@PathVariable String cedula, Model model, RedirectAttributes ra) {
        return service.getById(cedula).map(d -> {
            model.addAttribute("direccion", d);
            model.addAttribute("activeMenu", "direcciones");
            return "direccion/delete-confirm";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Dirección no encontrada.");
            return "redirect:/direcciones";
        });
    }

    @PostMapping("/eliminar/{cedula}")
    public String delete(@PathVariable String cedula, RedirectAttributes ra) {
        try {
            service.delete(cedula);
            ra.addFlashAttribute("successMsg", "Dirección eliminada correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/direcciones";
    }
}
