package com.link360.controller;

import com.link360.exception.DatabaseException;
import com.link360.model.PromoServicio;
import com.link360.service.PromoServicioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/promo-servicio")
public class PromoServicioController {

    private final PromoServicioService service;

    public PromoServicioController(PromoServicioService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("registros", service.getAll());
        model.addAttribute("activeMenu", "promo-servicio");
        return "promoservicio/list";
    }

    @GetMapping("/nuevo")
    public String newForm(Model model) {
        model.addAttribute("registro", new PromoServicio());
        model.addAttribute("promociones", service.getPromocionesForDropdown());
        model.addAttribute("servicios", service.getServiciosForDropdown());
        model.addAttribute("activeMenu", "promo-servicio");
        return "promoservicio/form";
    }

    @PostMapping("/nuevo")
    public String create(@ModelAttribute PromoServicio registro, RedirectAttributes ra) {
        try {
            service.create(registro);
            ra.addFlashAttribute("successMsg", "Asociación promoción-servicio registrada.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/promo-servicio/nuevo";
        }
        return "redirect:/promo-servicio";
    }

    @PostMapping("/eliminar/{codPromocion}/{codServicio}")
    public String delete(@PathVariable int codPromocion, @PathVariable int codServicio, RedirectAttributes ra) {
        try {
            service.delete(codPromocion, codServicio);
            ra.addFlashAttribute("successMsg", "Asociación eliminada correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/promo-servicio";
    }
}
