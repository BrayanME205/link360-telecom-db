package com.link360.controller;

import com.link360.exception.DatabaseException;
import com.link360.model.Paquete;
import com.link360.service.PaqueteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/paquetes")
public class PaqueteController {

    private final PaqueteService service;

    public PaqueteController(PaqueteService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("paquetes", service.getAll());
        model.addAttribute("activeMenu", "paquetes");
        return "paquete/list";
    }

    @GetMapping("/nuevo")
    public String newForm(Model model) {
        model.addAttribute("paquete", new Paquete());
        model.addAttribute("modoEdicion", false);
        model.addAttribute("activeMenu", "paquetes");
        return "paquete/form";
    }

    @PostMapping("/nuevo")
    public String create(@ModelAttribute Paquete paquete, RedirectAttributes ra) {
        try {
            service.create(paquete);
            ra.addFlashAttribute("successMsg", "Paquete registrado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/paquetes/nuevo";
        }
        return "redirect:/paquetes";
    }

    @GetMapping("/editar/{id}")
    public String editForm(@PathVariable int id, Model model, RedirectAttributes ra) {
        return service.getById(id).map(p -> {
            model.addAttribute("paquete", p);
            model.addAttribute("modoEdicion", true);
            model.addAttribute("activeMenu", "paquetes");
            return "paquete/form";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Paquete no encontrado.");
            return "redirect:/paquetes";
        });
    }

    @PostMapping("/editar/{id}")
    public String update(@PathVariable int id, @ModelAttribute Paquete paquete, RedirectAttributes ra) {
        paquete.setCodPaquete(id);
        try {
            service.update(paquete);
            ra.addFlashAttribute("successMsg", "Paquete actualizado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/paquetes/editar/" + id;
        }
        return "redirect:/paquetes";
    }

    @GetMapping("/eliminar/{id}")
    public String deleteWarning(@PathVariable int id, Model model, RedirectAttributes ra) {
        return service.getById(id).map(p -> {
            model.addAttribute("paquete", p);
            model.addAttribute("cascadeWarning", service.getDeleteWarning(id));
            model.addAttribute("activeMenu", "paquetes");
            return "paquete/delete-confirm";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Paquete no encontrado.");
            return "redirect:/paquetes";
        });
    }

    @PostMapping("/eliminar/{id}")
    public String delete(@PathVariable int id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("successMsg", "Paquete eliminado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/paquetes";
    }
}
