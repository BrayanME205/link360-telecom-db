package com.link360.controller;

import com.link360.exception.DatabaseException;
import com.link360.model.ConceptoCobro;
import com.link360.service.ConceptoCobroService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/conceptos-cobro")
public class ConceptoCobroController {

    private final ConceptoCobroService service;

    public ConceptoCobroController(ConceptoCobroService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("conceptos", service.getAll());
        model.addAttribute("activeMenu", "conceptos-cobro");
        return "concepto/list";
    }

    @GetMapping("/nuevo")
    public String newForm(Model model) {
        model.addAttribute("concepto", new ConceptoCobro());
        model.addAttribute("facturas", service.getFacturasForDropdown());
        model.addAttribute("modoEdicion", false);
        model.addAttribute("activeMenu", "conceptos-cobro");
        return "concepto/form";
    }

    @PostMapping("/nuevo")
    public String create(@ModelAttribute ConceptoCobro concepto, RedirectAttributes ra) {
        try {
            service.create(concepto);
            ra.addFlashAttribute("successMsg", "Concepto de cobro registrado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/conceptos-cobro/nuevo";
        }
        return "redirect:/conceptos-cobro";
    }

    @GetMapping("/editar/{numFactura}/{tipo}")
    public String editForm(@PathVariable int numFactura, @PathVariable String tipo,
            Model model, RedirectAttributes ra) {
        String tipoDecoded = URLDecoder.decode(tipo, StandardCharsets.UTF_8);
        return service.getById(numFactura, tipoDecoded).map(c -> {
            model.addAttribute("concepto", c);
            model.addAttribute("facturas", service.getFacturasForDropdown());
            model.addAttribute("modoEdicion", true);
            model.addAttribute("activeMenu", "conceptos-cobro");
            return "concepto/form";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Concepto no encontrado.");
            return "redirect:/conceptos-cobro";
        });
    }

    @PostMapping("/editar/{numFactura}/{tipo}")
    public String update(@PathVariable int numFactura, @PathVariable String tipo,
            @ModelAttribute ConceptoCobro concepto, RedirectAttributes ra) {
        concepto.setNumFactura(numFactura);
        concepto.setTipoConcepto(URLDecoder.decode(tipo, StandardCharsets.UTF_8));
        try {
            service.update(concepto);
            ra.addFlashAttribute("successMsg", "Concepto actualizado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/conceptos-cobro";
    }

    @GetMapping("/eliminar/{numFactura}/{tipo}")
    public String deleteWarning(@PathVariable int numFactura, @PathVariable String tipo,
            Model model, RedirectAttributes ra) {
        String tipoDecoded = URLDecoder.decode(tipo, StandardCharsets.UTF_8);
        return service.getById(numFactura, tipoDecoded).map(c -> {
            model.addAttribute("concepto", c);
            model.addAttribute("activeMenu", "conceptos-cobro");
            return "concepto/delete-confirm";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Concepto no encontrado.");
            return "redirect:/conceptos-cobro";
        });
    }

    @PostMapping("/eliminar/{numFactura}/{tipo}")
    public String delete(@PathVariable int numFactura, @PathVariable String tipo, RedirectAttributes ra) {
        try {
            service.delete(numFactura, URLDecoder.decode(tipo, StandardCharsets.UTF_8));
            ra.addFlashAttribute("successMsg", "Concepto eliminado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/conceptos-cobro";
    }
}
