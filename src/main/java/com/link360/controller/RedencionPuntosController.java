package com.link360.controller;

import com.link360.exception.DatabaseException;
import com.link360.model.RedencionPuntos;
import com.link360.service.RedencionPuntosService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/redenciones")
public class RedencionPuntosController {

    private final RedencionPuntosService service;

    public RedencionPuntosController(RedencionPuntosService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("redenciones", service.getAll());
        model.addAttribute("activeMenu", "redenciones");
        return "redencion/list";
    }

    @GetMapping("/nuevo")
    public String newForm(Model model) {
        model.addAttribute("redencion", new RedencionPuntos());
        model.addAttribute("puntos", service.getPuntosForDropdown());
        model.addAttribute("modoEdicion", false);
        model.addAttribute("activeMenu", "redenciones");
        return "redencion/form";
    }

    @PostMapping("/nuevo")
    public String create(@ModelAttribute RedencionPuntos redencion, RedirectAttributes ra) {
        try {
            service.create(redencion);
            ra.addFlashAttribute("successMsg", "Redención registrada correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/redenciones/nuevo";
        }
        return "redirect:/redenciones";
    }

    @GetMapping("/editar/{cedula}/{numFactura}/{fecha}")
    public String editForm(@PathVariable String cedula, @PathVariable int numFactura,
            @PathVariable String fecha, Model model, RedirectAttributes ra) {
        String fechaDecoded = URLDecoder.decode(fecha, StandardCharsets.UTF_8);
        return service.getById(cedula, numFactura, fechaDecoded).map(r -> {
            model.addAttribute("redencion", r);
            model.addAttribute("puntos", service.getPuntosForDropdown());
            model.addAttribute("modoEdicion", true);
            model.addAttribute("activeMenu", "redenciones");
            return "redencion/form";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Redención no encontrada.");
            return "redirect:/redenciones";
        });
    }

    @PostMapping("/editar/{cedula}/{numFactura}/{fecha}")
    public String update(@PathVariable String cedula, @PathVariable int numFactura,
            @PathVariable String fecha, @ModelAttribute RedencionPuntos redencion,
            RedirectAttributes ra) {
        redencion.setCedula(cedula);
        redencion.setNumFactura(numFactura);
        try {
            service.update(redencion);
            ra.addFlashAttribute("successMsg", "Redención actualizada correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/redenciones";
    }

    @GetMapping("/eliminar/{cedula}/{numFactura}/{fecha}")
    public String deleteWarning(@PathVariable String cedula, @PathVariable int numFactura,
            @PathVariable String fecha, Model model, RedirectAttributes ra) {
        String fechaDecoded = URLDecoder.decode(fecha, StandardCharsets.UTF_8);
        return service.getById(cedula, numFactura, fechaDecoded).map(r -> {
            model.addAttribute("redencion", r);
            model.addAttribute("activeMenu", "redenciones");
            return "redencion/delete-confirm";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Redención no encontrada.");
            return "redirect:/redenciones";
        });
    }

    @PostMapping("/eliminar/{cedula}/{numFactura}/{fecha}")
    public String delete(@PathVariable String cedula, @PathVariable int numFactura,
            @PathVariable String fecha, RedirectAttributes ra) {
        try {
            service.delete(cedula, numFactura, URLDecoder.decode(fecha, StandardCharsets.UTF_8));
            ra.addFlashAttribute("successMsg", "Redención eliminada correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/redenciones";
    }
}
