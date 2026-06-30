package com.link360.controller;

import com.link360.exception.DatabaseException;
import com.link360.model.DescIncompatible;
import com.link360.service.DescIncompatibleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/desc-incompatible")
public class DescIncompatibleController {

    private final DescIncompatibleService service;

    public DescIncompatibleController(DescIncompatibleService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("registros", service.getAll());
        model.addAttribute("activeMenu", "desc-incompatible");
        return "descincompatible/list";
    }

    @GetMapping("/nuevo")
    public String newForm(Model model) {
        model.addAttribute("registro", new DescIncompatible());
        model.addAttribute("descuentos", service.getDescuentosForDropdown());
        model.addAttribute("activeMenu", "desc-incompatible");
        return "descincompatible/form";
    }

    @PostMapping("/nuevo")
    public String create(@ModelAttribute DescIncompatible registro, RedirectAttributes ra) {
        try {
            service.create(registro);
            ra.addFlashAttribute("successMsg", "Incompatibilidad registrada. Se generó automáticamente la relación inversa por simetría.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/desc-incompatible/nuevo";
        }
        return "redirect:/desc-incompatible";
    }

    @PostMapping("/eliminar/{d1}/{d2}")
    public String delete(@PathVariable int d1, @PathVariable int d2, RedirectAttributes ra) {
        try {
            service.delete(d1, d2);
            ra.addFlashAttribute("successMsg", "Incompatibilidad eliminada.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/desc-incompatible";
    }
}
