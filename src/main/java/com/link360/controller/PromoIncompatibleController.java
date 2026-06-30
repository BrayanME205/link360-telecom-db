package com.link360.controller;

import com.link360.exception.DatabaseException;
import com.link360.model.PromoIncompatible;
import com.link360.service.PromoIncompatibleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/promo-incompatible")
public class PromoIncompatibleController {

    private final PromoIncompatibleService service;

    public PromoIncompatibleController(PromoIncompatibleService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("registros", service.getAll());
        model.addAttribute("activeMenu", "promo-incompatible");
        return "promoincompatible/list";
    }

    @GetMapping("/nuevo")
    public String newForm(Model model) {
        model.addAttribute("registro", new PromoIncompatible());
        model.addAttribute("promociones", service.getPromocionesForDropdown());
        model.addAttribute("activeMenu", "promo-incompatible");
        return "promoincompatible/form";
    }

    @PostMapping("/nuevo")
    public String create(@ModelAttribute PromoIncompatible registro, RedirectAttributes ra) {
        try {
            service.create(registro);
            ra.addFlashAttribute("successMsg", "Incompatibilidad registrada. Se generó automáticamente la relación inversa por simetría.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/promo-incompatible/nuevo";
        }
        return "redirect:/promo-incompatible";
    }

    @PostMapping("/eliminar/{p1}/{p2}")
    public String delete(@PathVariable int p1, @PathVariable int p2, RedirectAttributes ra) {
        try {
            service.delete(p1, p2);
            ra.addFlashAttribute("successMsg", "Incompatibilidad eliminada. Nota: la relación inversa generada por el trigger debe eliminarse por separado si aplica.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/promo-incompatible";
    }
}
