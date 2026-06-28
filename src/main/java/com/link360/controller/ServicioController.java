package com.link360.controller;

import com.link360.exception.DatabaseException;
import com.link360.model.Servicio;
import com.link360.service.ServicioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/servicios")
public class ServicioController {

    private final ServicioService service;

    public ServicioController(ServicioService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("servicios", service.getAll());
        model.addAttribute("activeMenu", "servicios");
        return "servicio/list";
    }

    @GetMapping("/nuevo")
    public String newForm(Model model) {
        model.addAttribute("servicio", new Servicio());
        model.addAttribute("modoEdicion", false);
        model.addAttribute("activeMenu", "servicios");
        return "servicio/form";
    }

    @PostMapping("/nuevo")
    public String create(@ModelAttribute Servicio servicio, RedirectAttributes ra) {
        try {
            service.create(servicio);
            ra.addFlashAttribute("successMsg", "Servicio registrado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/servicios/nuevo";
        }
        return "redirect:/servicios";
    }

    @GetMapping("/editar/{id}")
    public String editForm(@PathVariable int id, Model model, RedirectAttributes ra) {
        return service.getById(id).map(s -> {
            model.addAttribute("servicio", s);
            model.addAttribute("modoEdicion", true);
            model.addAttribute("activeMenu", "servicios");
            return "servicio/form";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Servicio no encontrado.");
            return "redirect:/servicios";
        });
    }

    @PostMapping("/editar/{id}")
    public String update(@PathVariable int id, @ModelAttribute Servicio servicio, RedirectAttributes ra) {
        servicio.setCodServicio(id);
        try {
            service.update(servicio);
            ra.addFlashAttribute("successMsg", "Servicio actualizado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/servicios/editar/" + id;
        }
        return "redirect:/servicios";
    }

    @GetMapping("/eliminar/{id}")
    public String deleteWarning(@PathVariable int id, Model model, RedirectAttributes ra) {
        return service.getById(id).map(s -> {
            model.addAttribute("servicio", s);
            model.addAttribute("cascadeWarning", service.getDeleteWarning(id));
            model.addAttribute("activeMenu", "servicios");
            return "servicio/delete-confirm";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Servicio no encontrado.");
            return "redirect:/servicios";
        });
    }

    @PostMapping("/eliminar/{id}")
    public String delete(@PathVariable int id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("successMsg", "Servicio eliminado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/servicios";
    }
}
