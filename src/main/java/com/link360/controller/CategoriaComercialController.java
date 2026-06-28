package com.link360.controller;

import com.link360.exception.DatabaseException;
import com.link360.model.CategoriaComercial;
import com.link360.service.CategoriaComercialService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/categorias")
public class CategoriaComercialController {

    private final CategoriaComercialService service;

    public CategoriaComercialController(CategoriaComercialService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("categorias", service.getAll());
        model.addAttribute("activeMenu", "categorias");
        return "categoria/list";
    }

    @GetMapping("/nuevo")
    public String newForm(Model model) {
        model.addAttribute("categoria", new CategoriaComercial());
        model.addAttribute("modoEdicion", false);
        model.addAttribute("activeMenu", "categorias");
        return "categoria/form";
    }

    @PostMapping("/nuevo")
    public String create(@ModelAttribute CategoriaComercial categoria, RedirectAttributes ra) {
        try {
            service.create(categoria);
            ra.addFlashAttribute("successMsg", "Categoría registrada correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/categorias/nuevo";
        }
        return "redirect:/categorias";
    }

    @GetMapping("/editar/{id}")
    public String editForm(@PathVariable int id, Model model, RedirectAttributes ra) {
        return service.getById(id).map(c -> {
            model.addAttribute("categoria", c);
            model.addAttribute("modoEdicion", true);
            model.addAttribute("activeMenu", "categorias");
            return "categoria/form";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Categoría no encontrada.");
            return "redirect:/categorias";
        });
    }

    @PostMapping("/editar/{id}")
    public String update(@PathVariable int id, @ModelAttribute CategoriaComercial categoria, RedirectAttributes ra) {
        categoria.setCodCategoria(id);
        try {
            service.update(categoria);
            ra.addFlashAttribute("successMsg", "Categoría actualizada correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/categorias/editar/" + id;
        }
        return "redirect:/categorias";
    }

    @GetMapping("/eliminar/{id}")
    public String deleteWarning(@PathVariable int id, Model model, RedirectAttributes ra) {
        return service.getById(id).map(c -> {
            model.addAttribute("categoria", c);
            model.addAttribute("cascadeWarning", service.getDeleteWarning(id));
            model.addAttribute("activeMenu", "categorias");
            return "categoria/delete-confirm";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Categoría no encontrada.");
            return "redirect:/categorias";
        });
    }

    @PostMapping("/eliminar/{id}")
    public String delete(@PathVariable int id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("successMsg", "Categoría eliminada correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/categorias";
    }
}
