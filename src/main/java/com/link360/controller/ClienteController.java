package com.link360.controller;

import com.link360.exception.DatabaseException;
import com.link360.model.Cliente;
import com.link360.service.ClienteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("clientes", service.getAll());
        model.addAttribute("activeMenu", "clientes");
        return "cliente/list";
    }

    @GetMapping("/nuevo")
    public String newForm(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("modoEdicion", false);
        model.addAttribute("activeMenu", "clientes");
        return "cliente/form";
    }

    @PostMapping("/nuevo")
    public String create(@ModelAttribute Cliente cliente,
            RedirectAttributes redirectAttrs) {
        try {
            service.create(cliente);
            redirectAttrs.addFlashAttribute("successMsg",
                    "Cliente " + cliente.getCedula() + " registrado correctamente.");
        } catch (DatabaseException e) {
            redirectAttrs.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/clientes/nuevo";
        }
        return "redirect:/clientes";
    }

    @GetMapping("/editar/{cedula}")
    public String editForm(@PathVariable String cedula, Model model,
            RedirectAttributes redirectAttrs) {
        Optional<Cliente> opt = service.getById(cedula);
        if (opt.isEmpty()) {
            redirectAttrs.addFlashAttribute("errorMsg", "Cliente no encontrado.");
            return "redirect:/clientes";
        }
        model.addAttribute("cliente", opt.get());
        model.addAttribute("modoEdicion", true);
        model.addAttribute("activeMenu", "clientes");
        return "cliente/form";
    }

    @PostMapping("/editar/{cedula}")
    public String update(@PathVariable String cedula,
            @ModelAttribute Cliente cliente,
            RedirectAttributes redirectAttrs) {
        cliente.setCedula(cedula);
        try {
            service.update(cliente);
            redirectAttrs.addFlashAttribute("successMsg",
                    "Cliente actualizado correctamente.");
        } catch (DatabaseException e) {
            redirectAttrs.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/clientes/editar/" + cedula;
        }
        return "redirect:/clientes";
    }

    @GetMapping("/eliminar/{cedula}")
    public String deleteWarning(@PathVariable String cedula, Model model,
            RedirectAttributes redirectAttrs) {
        Optional<Cliente> opt = service.getById(cedula);
        if (opt.isEmpty()) {
            redirectAttrs.addFlashAttribute("errorMsg", "Cliente no encontrado.");
            return "redirect:/clientes";
        }
        String warning = service.getCascadeWarning(cedula);
        model.addAttribute("cliente", opt.get());
        model.addAttribute("cascadeWarning", warning);
        model.addAttribute("activeMenu", "clientes");
        return "cliente/delete-confirm";
    }

    @PostMapping("/eliminar/{cedula}")
    public String delete(@PathVariable String cedula,
            RedirectAttributes redirectAttrs) {
        try {
            service.delete(cedula);
            redirectAttrs.addFlashAttribute("successMsg",
                    "Cliente eliminado correctamente.");
        } catch (DatabaseException e) {
            redirectAttrs.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/clientes";
    }
}
