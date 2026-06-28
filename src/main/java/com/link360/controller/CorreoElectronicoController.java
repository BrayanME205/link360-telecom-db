package com.link360.controller;

import com.link360.exception.DatabaseException;
import com.link360.model.CorreoElectronico;
import com.link360.service.CorreoElectronicoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/correos")
public class CorreoElectronicoController {

    private final CorreoElectronicoService service;

    public CorreoElectronicoController(CorreoElectronicoService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("correos", service.getAll());
        model.addAttribute("activeMenu", "correos");
        return "correo/list";
    }

    @GetMapping("/nuevo")
    public String newForm(Model model) {
        model.addAttribute("correo", new CorreoElectronico());
        model.addAttribute("clientes", service.getClientesForDropdown());
        model.addAttribute("modoEdicion", false);
        model.addAttribute("activeMenu", "correos");
        return "correo/form";
    }

    @PostMapping("/nuevo")
    public String create(@ModelAttribute CorreoElectronico correo, RedirectAttributes ra) {
        try {
            service.create(correo);
            ra.addFlashAttribute("successMsg", "Correo registrado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/correos/nuevo";
        }
        return "redirect:/correos";
    }

    @GetMapping("/editar/{cedula}/{email}")
    public String editForm(@PathVariable String cedula, @PathVariable String email,
            Model model, RedirectAttributes ra) {
        String emailDecoded = URLDecoder.decode(email, StandardCharsets.UTF_8);
        return service.getById(cedula, emailDecoded).map(c -> {
            model.addAttribute("correo", c);
            model.addAttribute("clientes", service.getClientesForDropdown());
            model.addAttribute("modoEdicion", true);
            model.addAttribute("activeMenu", "correos");
            return "correo/form";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Correo no encontrado.");
            return "redirect:/correos";
        });
    }

    @PostMapping("/editar/{cedula}/{email}")
    public String update(@PathVariable String cedula, @PathVariable String email,
            @ModelAttribute CorreoElectronico correo, RedirectAttributes ra) {
        correo.setCedula(cedula);
        correo.setEmail(URLDecoder.decode(email, StandardCharsets.UTF_8));
        try {
            service.update(correo);
            ra.addFlashAttribute("successMsg", "Correo actualizado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/correos";
    }

    @GetMapping("/eliminar/{cedula}/{email}")
    public String deleteWarning(@PathVariable String cedula, @PathVariable String email,
            Model model, RedirectAttributes ra) {
        String emailDecoded = URLDecoder.decode(email, StandardCharsets.UTF_8);
        return service.getById(cedula, emailDecoded).map(c -> {
            model.addAttribute("correo", c);
            model.addAttribute("activeMenu", "correos");
            return "correo/delete-confirm";
        }).orElseGet(() -> {
            ra.addFlashAttribute("errorMsg", "Correo no encontrado.");
            return "redirect:/correos";
        });
    }

    @PostMapping("/eliminar/{cedula}/{email}")
    public String delete(@PathVariable String cedula, @PathVariable String email, RedirectAttributes ra) {
        try {
            service.delete(cedula, URLDecoder.decode(email, StandardCharsets.UTF_8));
            ra.addFlashAttribute("successMsg", "Correo eliminado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/correos";
    }
}
