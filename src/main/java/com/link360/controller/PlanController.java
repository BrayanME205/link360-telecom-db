package com.link360.controller;

import com.link360.exception.DatabaseException;
import com.link360.model.Plan;
import com.link360.service.PlanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/planes")
public class PlanController {

    private final PlanService service;
    public PlanController(PlanService service) { this.service = service; }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("planes", service.getAll());
        model.addAttribute("activeMenu", "planes");
        return "plan/list";
    }

    @GetMapping("/nuevo")
    public String newForm(Model model) {
        model.addAttribute("plan", new Plan());
        model.addAttribute("categorias", service.getCategoriasForDropdown());
        model.addAttribute("modoEdicion", false);
        model.addAttribute("activeMenu", "planes");
        return "plan/form";
    }

    @PostMapping("/nuevo")
    public String create(@ModelAttribute Plan plan, RedirectAttributes ra) {
        try {
            service.create(plan);
            ra.addFlashAttribute("successMsg", "Plan registrado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/planes/nuevo";
        }
        return "redirect:/planes";
    }

    @GetMapping("/editar/{id}")
    public String editForm(@PathVariable int id, Model model, RedirectAttributes ra) {
        return service.getById(id).map(p -> {
            model.addAttribute("plan", p);
            model.addAttribute("categorias", service.getCategoriasForDropdown());
            model.addAttribute("modoEdicion", true);
            model.addAttribute("activeMenu", "planes");
            return "plan/form";
        }).orElseGet(() -> { ra.addFlashAttribute("errorMsg", "Plan no encontrado."); return "redirect:/planes"; });
    }

    @PostMapping("/editar/{id}")
    public String update(@PathVariable int id, @ModelAttribute Plan plan, RedirectAttributes ra) {
        plan.setCodPlan(id);
        try {
            service.update(plan);
            ra.addFlashAttribute("successMsg", "Plan actualizado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/planes/editar/" + id;
        }
        return "redirect:/planes";
    }

    @GetMapping("/eliminar/{id}")
    public String deleteWarning(@PathVariable int id, Model model, RedirectAttributes ra) {
        return service.getById(id).map(p -> {
            model.addAttribute("plan", p);
            model.addAttribute("cascadeWarning", service.getDeleteWarning(id));
            model.addAttribute("activeMenu", "planes");
            return "plan/delete-confirm";
        }).orElseGet(() -> { ra.addFlashAttribute("errorMsg", "Plan no encontrado."); return "redirect:/planes"; });
    }

    @PostMapping("/eliminar/{id}")
    public String delete(@PathVariable int id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("successMsg", "Plan eliminado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/planes";
    }
}