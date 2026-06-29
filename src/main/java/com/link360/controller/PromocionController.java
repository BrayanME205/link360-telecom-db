package com.link360.controller;

import com.link360.exception.DatabaseException;
import com.link360.model.Promocion;
import com.link360.service.PromocionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/promociones")
public class PromocionController {

    private final PromocionService service;
    public PromocionController(PromocionService service) { this.service = service; }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("promociones", service.getAll());
        model.addAttribute("activeMenu", "promociones");
        return "promocion/list";
    }

    @GetMapping("/nuevo")
    public String newForm(Model model) {
        model.addAttribute("promocion", new Promocion());
        model.addAttribute("tipos", service.getTiposForDropdown());
        model.addAttribute("modoEdicion", false);
        model.addAttribute("activeMenu", "promociones");
        return "promocion/form";
    }

    @PostMapping("/nuevo")
    public String create(@ModelAttribute Promocion promocion, RedirectAttributes ra) {
        try {
            service.create(promocion);
            ra.addFlashAttribute("successMsg", "Promoción registrada correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/promociones/nuevo";
        }
        return "redirect:/promociones";
    }

    @GetMapping("/editar/{id}")
    public String editForm(@PathVariable int id, Model model, RedirectAttributes ra) {
        return service.getById(id).map(p -> {
            model.addAttribute("promocion", p);
            model.addAttribute("tipos", service.getTiposForDropdown());
            model.addAttribute("modoEdicion", true);
            model.addAttribute("activeMenu", "promociones");
            return "promocion/form";
        }).orElseGet(() -> { ra.addFlashAttribute("errorMsg", "Promoción no encontrada."); return "redirect:/promociones"; });
    }

    @PostMapping("/editar/{id}")
    public String update(@PathVariable int id, @ModelAttribute Promocion promocion, RedirectAttributes ra) {
        promocion.setCodPromocion(id);
        try {
            service.update(promocion);
            ra.addFlashAttribute("successMsg", "Promoción actualizada correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/promociones/editar/" + id;
        }
        return "redirect:/promociones";
    }

    @GetMapping("/eliminar/{id}")
    public String deleteWarning(@PathVariable int id, Model model, RedirectAttributes ra) {
        return service.getById(id).map(p -> {
            model.addAttribute("promocion", p);
            model.addAttribute("cascadeWarning", service.getDeleteWarning(id));
            model.addAttribute("activeMenu", "promociones");
            return "promocion/delete-confirm";
        }).orElseGet(() -> { ra.addFlashAttribute("errorMsg", "Promoción no encontrada."); return "redirect:/promociones"; });
    }

    @PostMapping("/eliminar/{id}")
    public String delete(@PathVariable int id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("successMsg", "Promoción eliminada correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/promociones";
    }
}