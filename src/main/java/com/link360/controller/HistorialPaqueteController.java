package com.link360.controller;

import com.link360.exception.DatabaseException;
import com.link360.model.HistorialPaquete;
import com.link360.service.HistorialPaqueteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/historial-paquetes")
public class HistorialPaqueteController {

    private final HistorialPaqueteService service;

    public HistorialPaqueteController(HistorialPaqueteService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("historiales", service.getAll());
        model.addAttribute("activeMenu", "historial-paquetes");
        return "historial/list";
    }

    @GetMapping("/nuevo")
    public String newForm(Model model) {
        model.addAttribute("historial", new HistorialPaquete());
        model.addAttribute("lineas", service.getLineasForDropdown());
        model.addAttribute("paquetes", service.getPaquetesForDropdown());
        model.addAttribute("modoEdicion", false);
        model.addAttribute("activeMenu", "historial-paquetes");
        return "historial/form";
    }

    @PostMapping("/nuevo")
    public String create(@ModelAttribute HistorialPaquete historial, RedirectAttributes ra) {
        try {
            service.create(historial);
            ra.addFlashAttribute("successMsg", "Historial registrado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/historial-paquetes/nuevo";
        }
        return "redirect:/historial-paquetes";
    }

    @GetMapping("/editar/{numero}/{codPaquete}/{fecha}")
    public String editForm(@PathVariable String numero, @PathVariable int codPaquete,
            @PathVariable String fecha, Model model, RedirectAttributes ra) {
        return service.getById(numero, codPaquete, fecha).map(h -> {
            model.addAttribute("historial", h);
            model.addAttribute("lineas", service.getLineasForDropdown());
            model.addAttribute("paquetes", service.getPaquetesForDropdown());
            model.addAttribute("modoEdicion", true);
            model.addAttribute("activeMenu", "historial-paquetes");
            return "historial/form";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Historial no encontrado.");
            return "redirect:/historial-paquetes";
        });
    }

    @PostMapping("/editar/{numero}/{codPaquete}/{fecha}")
    public String update(@PathVariable String numero, @PathVariable int codPaquete,
            @PathVariable String fecha, @ModelAttribute HistorialPaquete historial,
            RedirectAttributes ra) {
        historial.setNumeroTelefono(numero);
        historial.setCodPaquete(codPaquete);
        historial.setFechaAdquisicion(java.time.LocalDate.parse(fecha));
        try {
            service.update(historial);
            ra.addFlashAttribute("successMsg", "Historial actualizado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/historial-paquetes";
    }

    @GetMapping("/eliminar/{numero}/{codPaquete}/{fecha}")
    public String deleteWarning(@PathVariable String numero, @PathVariable int codPaquete,
            @PathVariable String fecha, Model model, RedirectAttributes ra) {
        return service.getById(numero, codPaquete, fecha).map(h -> {
            model.addAttribute("historial", h);
            model.addAttribute("activeMenu", "historial-paquetes");
            return "historial/delete-confirm";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Historial no encontrado.");
            return "redirect:/historial-paquetes";
        });
    }

    @PostMapping("/eliminar/{numero}/{codPaquete}/{fecha}")
    public String delete(@PathVariable String numero, @PathVariable int codPaquete,
            @PathVariable String fecha, RedirectAttributes ra) {
        try {
            service.delete(numero, codPaquete, fecha);
            ra.addFlashAttribute("successMsg", "Historial eliminado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/historial-paquetes";
    }
}
