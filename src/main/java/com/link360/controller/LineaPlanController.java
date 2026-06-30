package com.link360.controller;

import com.link360.exception.DatabaseException;
import com.link360.model.LineaPlan;
import com.link360.service.LineaPlanService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/linea-plan")
public class LineaPlanController {

    private final LineaPlanService service;

    public LineaPlanController(LineaPlanService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("registros", service.getAll());
        model.addAttribute("activeMenu", "linea-plan");
        return "lineaplan/list";
    }

    @GetMapping("/nuevo")
    public String newForm(Model model) {
        model.addAttribute("registro", new LineaPlan());
        model.addAttribute("lineas", service.getLineasForDropdown());
        model.addAttribute("planes", service.getPlanesForDropdown());
        model.addAttribute("activeMenu", "linea-plan");
        return "lineaplan/form";
    }

    @PostMapping("/nuevo")
    public String create(@ModelAttribute LineaPlan registro, RedirectAttributes ra) {
        try {
            service.create(registro);
            ra.addFlashAttribute("successMsg", "Plan contratado registrado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/linea-plan/nuevo";
        }
        return "redirect:/linea-plan";
    }

    @PostMapping("/finalizar/{numero}/{codPlan}/{fecha}")
    public String finalizar(@PathVariable String numero, @PathVariable int codPlan,
            @PathVariable String fecha, RedirectAttributes ra) {
        return service.getById(numero, codPlan, fecha).map(r -> {
            r.setFechaFin(java.time.LocalDate.now());
            try {
                service.update(r);
                ra.addFlashAttribute("successMsg", "Plan finalizado correctamente.");
            } catch (DatabaseException e) {
                ra.addFlashAttribute("errorMsg", e.getUserMessage());
            }
            return "redirect:/linea-plan";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Registro no encontrado.");
            return "redirect:/linea-plan";
        });
    }

    @PostMapping("/eliminar/{numero}/{codPlan}/{fecha}")
    public String delete(@PathVariable String numero, @PathVariable int codPlan,
            @PathVariable String fecha, RedirectAttributes ra) {
        try {
            service.delete(numero, codPlan, fecha);
            ra.addFlashAttribute("successMsg", "Registro eliminado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/linea-plan";
    }
}
