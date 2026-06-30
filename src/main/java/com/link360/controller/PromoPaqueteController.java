package com.link360.controller;

import com.link360.exception.DatabaseException;
import com.link360.model.PromoPaquete;
import com.link360.service.PromoPaqueteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/promo-paquete")
public class PromoPaqueteController {

    private final PromoPaqueteService service;

    public PromoPaqueteController(PromoPaqueteService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("registros", service.getAll());
        model.addAttribute("activeMenu", "promo-paquete");
        return "promopaquete/list";
    }

    @GetMapping("/nuevo")
    public String newForm(Model model) {
        model.addAttribute("registro", new PromoPaquete());
        model.addAttribute("promociones", service.getPromocionesForDropdown());
        model.addAttribute("paquetes", service.getPaquetesForDropdown());
        model.addAttribute("activeMenu", "promo-paquete");
        return "promopaquete/form";
    }

    @PostMapping("/nuevo")
    public String create(@ModelAttribute PromoPaquete registro, RedirectAttributes ra) {
        try {
            service.create(registro);
            ra.addFlashAttribute("successMsg", "Asociación promoción-paquete registrada.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/promo-paquete/nuevo";
        }
        return "redirect:/promo-paquete";
    }

    @PostMapping("/eliminar/{codPromocion}/{codPaquete}")
    public String delete(@PathVariable int codPromocion, @PathVariable int codPaquete, RedirectAttributes ra) {
        try {
            service.delete(codPromocion, codPaquete);
            ra.addFlashAttribute("successMsg", "Asociación eliminada correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/promo-paquete";
    }
}
