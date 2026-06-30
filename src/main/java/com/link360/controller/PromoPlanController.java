package com.link360.controller;

import com.link360.exception.DatabaseException;
import com.link360.model.PromoPlan;
import com.link360.service.PromoPlanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/promo-plan")
public class PromoPlanController {

    private final PromoPlanService service;

    public PromoPlanController(PromoPlanService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("registros", service.getAll());
        model.addAttribute("activeMenu", "promo-plan");
        return "promoplan/list";
    }

    @GetMapping("/nuevo")
    public String newForm(Model model) {
        model.addAttribute("registro", new PromoPlan());
        model.addAttribute("promociones", service.getPromocionesForDropdown());
        model.addAttribute("planes", service.getPlanesForDropdown());
        model.addAttribute("activeMenu", "promo-plan");
        return "promoplan/form";
    }

    @PostMapping("/nuevo")
    public String create(@ModelAttribute PromoPlan registro, RedirectAttributes ra) {
        try {
            service.create(registro);
            ra.addFlashAttribute("successMsg", "Asociación promoción-plan registrada.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/promo-plan/nuevo";
        }
        return "redirect:/promo-plan";
    }

    @PostMapping("/eliminar/{codPromocion}/{codPlan}")
    public String delete(@PathVariable int codPromocion, @PathVariable int codPlan, RedirectAttributes ra) {
        try {
            service.delete(codPromocion, codPlan);
            ra.addFlashAttribute("successMsg", "Asociación eliminada correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/promo-plan";
    }
}
