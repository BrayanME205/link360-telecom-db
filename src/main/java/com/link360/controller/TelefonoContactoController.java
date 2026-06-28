package com.link360.controller;

import com.link360.exception.DatabaseException;
import com.link360.model.TelefonoContacto;
import com.link360.service.TelefonoContactoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/telefonos")
public class TelefonoContactoController {

    private final TelefonoContactoService service;

    public TelefonoContactoController(TelefonoContactoService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("telefonos", service.getAll());
        model.addAttribute("activeMenu", "telefonos");
        return "telefono/list";
    }

    @GetMapping("/nuevo")
    public String newForm(Model model) {
        model.addAttribute("telefono", new TelefonoContacto());
        model.addAttribute("clientes", service.getClientesForDropdown());
        model.addAttribute("modoEdicion", false);
        model.addAttribute("activeMenu", "telefonos");
        return "telefono/form";
    }

    @PostMapping("/nuevo")
    public String create(@ModelAttribute TelefonoContacto telefono, RedirectAttributes ra) {
        try {
            service.create(telefono);
            ra.addFlashAttribute("successMsg", "Teléfono registrado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/telefonos/nuevo";
        }
        return "redirect:/telefonos";
    }

    @GetMapping("/editar/{cedula}/{numero}")
    public String editForm(@PathVariable String cedula, @PathVariable String numero,
            Model model, RedirectAttributes ra) {
        String numDecoded = URLDecoder.decode(numero, StandardCharsets.UTF_8);
        return service.getById(cedula, numDecoded).map(t -> {
            model.addAttribute("telefono", t);
            model.addAttribute("clientes", service.getClientesForDropdown());
            model.addAttribute("modoEdicion", true);
            model.addAttribute("activeMenu", "telefonos");
            return "telefono/form";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Teléfono no encontrado.");
            return "redirect:/telefonos";
        });
    }

    @PostMapping("/editar/{cedula}/{numero}")
    public String update(@PathVariable String cedula, @PathVariable String numero,
            @ModelAttribute TelefonoContacto telefono, RedirectAttributes ra) {
        telefono.setCedula(cedula);
        telefono.setNumero(URLDecoder.decode(numero, StandardCharsets.UTF_8));
        try {
            service.update(telefono);
            ra.addFlashAttribute("successMsg", "Teléfono actualizado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/telefonos";
    }

    @GetMapping("/eliminar/{cedula}/{numero}")
    public String deleteWarning(@PathVariable String cedula, @PathVariable String numero,
            Model model, RedirectAttributes ra) {
        String numDecoded = URLDecoder.decode(numero, StandardCharsets.UTF_8);
        return service.getById(cedula, numDecoded).map(t -> {
            model.addAttribute("telefono", t);
            model.addAttribute("activeMenu", "telefonos");
            return "telefono/delete-confirm";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Teléfono no encontrado.");
            return "redirect:/telefonos";
        });
    }

    @PostMapping("/eliminar/{cedula}/{numero}")
    public String delete(@PathVariable String cedula, @PathVariable String numero, RedirectAttributes ra) {
        try {
            service.delete(cedula, URLDecoder.decode(numero, StandardCharsets.UTF_8));
            ra.addFlashAttribute("successMsg", "Teléfono eliminado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/telefonos";
    }
}
