package com.link360.controller;

import com.link360.exception.DatabaseException;
import com.link360.model.LineaServicio;
import com.link360.service.LineaServicioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/linea-servicio")
public class LineaServicioController {

    private final LineaServicioService service;

    public LineaServicioController(LineaServicioService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("registros", service.getAll());
        model.addAttribute("activeMenu", "linea-servicio");
        return "lineaservicio/list";
    }

    @GetMapping("/nuevo")
    public String newForm(Model model) {
        model.addAttribute("registro", new LineaServicio());
        model.addAttribute("lineas", service.getLineasForDropdown());
        model.addAttribute("servicios", service.getServiciosForDropdown());
        model.addAttribute("activeMenu", "linea-servicio");
        return "lineaservicio/form";
    }

    @PostMapping("/nuevo")
    public String create(@ModelAttribute LineaServicio registro, RedirectAttributes ra) {
        try {
            service.create(registro);
            ra.addFlashAttribute("successMsg", "Servicio contratado registrado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/linea-servicio/nuevo";
        }
        return "redirect:/linea-servicio";
    }

    @PostMapping("/eliminar/{numero}/{codServicio}")
    public String delete(@PathVariable String numero, @PathVariable int codServicio, RedirectAttributes ra) {
        try {
            service.delete(numero, codServicio);
            ra.addFlashAttribute("successMsg", "Servicio desvinculado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/linea-servicio";
    }
}
