package com.link360.controller;

import com.link360.exception.DatabaseException;
import com.link360.model.TipoPromocion;
import com.link360.service.TipoPromocionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tipos-promocion")
public class TipoPromocionController {

    private final TipoPromocionService service;

    public TipoPromocionController(TipoPromocionService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("tipos", service.getAll());
        model.addAttribute("activeMenu", "tipos-promocion");
        return "tipopromo/list";
    }

    @GetMapping("/nuevo")
    public String newForm(Model model) {
        model.addAttribute("tipo", new TipoPromocion());
        model.addAttribute("modoEdicion", false);
        model.addAttribute("activeMenu", "tipos-promocion");
        return "tipopromo/form";
    }

    @PostMapping("/nuevo")
    public String create(@ModelAttribute TipoPromocion tipo, RedirectAttributes ra) {
        try {
            service.create(tipo);
            ra.addFlashAttribute("successMsg", "Tipo de promoción registrado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/tipos-promocion/nuevo";
        }
        return "redirect:/tipos-promocion";
    }

    @GetMapping("/editar/{id}")
    public String editForm(@PathVariable String id, Model model, RedirectAttributes ra) {
        return service.getById(id).map(t -> {
            model.addAttribute("tipo", t);
            model.addAttribute("modoEdicion", true);
            model.addAttribute("activeMenu", "tipos-promocion");
            return "tipopromo/form";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Tipo no encontrado.");
            return "redirect:/tipos-promocion";
        });
    }

    @PostMapping("/editar/{id}")
    public String update(@PathVariable String id, @ModelAttribute TipoPromocion tipo, RedirectAttributes ra) {
        tipo.setTipoPromo(id);
        try {
            service.update(tipo);
            ra.addFlashAttribute("successMsg", "Tipo de promoción actualizado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/tipos-promocion/editar/" + id;
        }
        return "redirect:/tipos-promocion";
    }

    @GetMapping("/eliminar/{id}")
    public String deleteWarning(@PathVariable String id, Model model, RedirectAttributes ra) {
        return service.getById(id).map(t -> {
            model.addAttribute("tipo", t);
            model.addAttribute("cascadeWarning", service.getDeleteWarning(id));
            model.addAttribute("activeMenu", "tipos-promocion");
            return "tipopromo/delete-confirm";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Tipo no encontrado.");
            return "redirect:/tipos-promocion";
        });
    }

    @PostMapping("/eliminar/{id}")
    public String delete(@PathVariable String id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("successMsg", "Tipo de promoción eliminado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/tipos-promocion";
    }
}
