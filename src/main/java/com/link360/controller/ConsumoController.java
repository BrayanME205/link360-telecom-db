package com.link360.controller;

import com.link360.exception.DatabaseException;
import com.link360.model.*;
import com.link360.service.ConsumoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/consumos")
public class ConsumoController {

    private final ConsumoService service;
    public ConsumoController(ConsumoService service) { this.service = service; }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("consumos", service.getAll());
        model.addAttribute("activeMenu", "consumos");
        return "consumo/list";
    }

    @GetMapping("/nuevo")
    public String newForm(Model model) {
        model.addAttribute("consumo", new Consumo());
        model.addAttribute("lineas", service.getLineasForDropdown());
        model.addAttribute("descuentos", service.getDescuentosForDropdown());
        model.addAttribute("modoEdicion", false);
        model.addAttribute("activeMenu", "consumos");
        return "consumo/form";
    }

    @PostMapping("/nuevo")
    public String create(@ModelAttribute Consumo consumo, RedirectAttributes ra) {
        try {
            service.create(consumo);
            ra.addFlashAttribute("successMsg", "Consumo registrado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/consumos/nuevo";
        }
        return "redirect:/consumos";
    }

    @GetMapping("/editar/{id}")
    public String editForm(@PathVariable int id, Model model, RedirectAttributes ra) {
        return service.getById(id).map(c -> {
            model.addAttribute("consumo", c);
            model.addAttribute("lineas", service.getLineasForDropdown());
            model.addAttribute("descuentos", service.getDescuentosForDropdown());
            model.addAttribute("modoEdicion", true);
            model.addAttribute("activeMenu", "consumos");
            return "consumo/form";
        }).orElseGet(() -> { ra.addFlashAttribute("errorMsg", "Consumo no encontrado."); return "redirect:/consumos"; });
    }

    @PostMapping("/editar/{id}")
    public String update(@PathVariable int id, @ModelAttribute Consumo consumo, RedirectAttributes ra) {
        consumo.setCodConsumo(id);
        try {
            service.update(consumo);
            ra.addFlashAttribute("successMsg", "Consumo actualizado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/consumos/editar/" + id;
        }
        return "redirect:/consumos";
    }

    @GetMapping("/eliminar/{id}")
    public String deleteWarning(@PathVariable int id, Model model, RedirectAttributes ra) {
        return service.getById(id).map(c -> {
            model.addAttribute("consumo", c);
            model.addAttribute("cascadeWarning", service.getCascadeWarning(id));
            model.addAttribute("activeMenu", "consumos");
            return "consumo/delete-confirm";
        }).orElseGet(() -> { ra.addFlashAttribute("errorMsg", "Consumo no encontrado."); return "redirect:/consumos"; });
    }

    @PostMapping("/eliminar/{id}")
    public String delete(@PathVariable int id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("successMsg", "Consumo eliminado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/consumos";
    }

    @GetMapping("/voz")
    public String listVoz(Model model) {
        model.addAttribute("consumosVoz", service.getAllVoz());
        model.addAttribute("activeMenu", "consumos");
        return "consumo/list-voz";
    }

    @GetMapping("/voz/nuevo")
    public String newVozForm(Model model) {
        model.addAttribute("voz", new ConsumoVoz());
        model.addAttribute("consumos", service.getAllForDropdown());
        model.addAttribute("modoEdicion", false);
        model.addAttribute("activeMenu", "consumos");
        return "consumo/form-voz";
    }

    @PostMapping("/voz/nuevo")
    public String createVoz(@ModelAttribute ConsumoVoz voz, RedirectAttributes ra) {
        try {
            service.createVoz(voz);
            ra.addFlashAttribute("successMsg", "Consumo de voz registrado correctamente.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
            return "redirect:/consumos/voz/nuevo";
        }
        return "redirect:/consumos/voz";
    }

    @GetMapping("/voz/editar/{id}")
    public String editVozForm(@PathVariable int id, Model model, RedirectAttributes ra) {
        return service.getVozById(id).map(v -> {
            model.addAttribute("voz", v);
            model.addAttribute("consumos", service.getAllForDropdown());
            model.addAttribute("modoEdicion", true);
            model.addAttribute("activeMenu", "consumos");
            return "consumo/form-voz";
        }).orElseGet(() -> { ra.addFlashAttribute("errorMsg", "No encontrado."); return "redirect:/consumos/voz"; });
    }

    @PostMapping("/voz/editar/{id}")
    public String updateVoz(@PathVariable int id, @ModelAttribute ConsumoVoz voz, RedirectAttributes ra) {
        voz.setCodConsumo(id);
        try {
            service.updateVoz(voz);
            ra.addFlashAttribute("successMsg", "Consumo de voz actualizado.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/consumos/voz";
    }

    @PostMapping("/voz/eliminar/{id}")
    public String deleteVoz(@PathVariable int id, RedirectAttributes ra) {
        try {
            service.deleteVoz(id);
            ra.addFlashAttribute("successMsg", "Consumo de voz eliminado.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/consumos/voz";
    }

    @GetMapping("/sms")
    public String listSms(Model model) {
        model.addAttribute("consumosSms", service.getAllSms());
        model.addAttribute("subtipo", "SMS");
        model.addAttribute("activeMenu", "consumos");
        return "consumo/list-simple";
    }

    @GetMapping("/sms/nuevo")
    public String newSmsForm(Model model) {
        ConsumoSimple s = new ConsumoSimple();
        s.setTipoSubtipo("SMS");
        model.addAttribute("simple", s);
        model.addAttribute("consumos", service.getAllForDropdown());
        model.addAttribute("subtipo", "SMS");
        model.addAttribute("modoEdicion", false);
        model.addAttribute("activeMenu", "consumos");
        return "consumo/form-simple";
    }

    @PostMapping("/sms/nuevo")
    public String createSms(@ModelAttribute ConsumoSimple simple, RedirectAttributes ra) {
        try {
            service.createSimple(simple, "SMS");
            ra.addFlashAttribute("successMsg", "Consumo SMS registrado.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/consumos/sms";
    }

    @PostMapping("/sms/eliminar/{id}")
    public String deleteSms(@PathVariable int id, RedirectAttributes ra) {
        try {
            service.deleteSimple(id, "SMS");
            ra.addFlashAttribute("successMsg", "Consumo SMS eliminado.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/consumos/sms";
    }

    @GetMapping("/datos")
    public String listDatos(Model model) {
        model.addAttribute("consumosSms", service.getAllDatos());
        model.addAttribute("subtipo", "DATOS");
        model.addAttribute("activeMenu", "consumos");
        return "consumo/list-simple";
    }

    @GetMapping("/datos/nuevo")
    public String newDatosForm(Model model) {
        ConsumoSimple s = new ConsumoSimple();
        s.setTipoSubtipo("DATOS");
        model.addAttribute("simple", s);
        model.addAttribute("consumos", service.getAllForDropdown());
        model.addAttribute("subtipo", "DATOS");
        model.addAttribute("modoEdicion", false);
        model.addAttribute("activeMenu", "consumos");
        return "consumo/form-simple";
    }

    @PostMapping("/datos/nuevo")
    public String createDatos(@ModelAttribute ConsumoSimple simple, RedirectAttributes ra) {
        try {
            service.createSimple(simple, "DATOS");
            ra.addFlashAttribute("successMsg", "Consumo de datos registrado.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/consumos/datos";
    }

    @PostMapping("/datos/eliminar/{id}")
    public String deleteDatos(@PathVariable int id, RedirectAttributes ra) {
        try {
            service.deleteSimple(id, "DATOS");
            ra.addFlashAttribute("successMsg", "Consumo de datos eliminado.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/consumos/datos";
    }

    @GetMapping("/roaming")
    public String listRoaming(Model model) {
        model.addAttribute("consumosSms", service.getAllRoaming());
        model.addAttribute("subtipo", "ROAMING");
        model.addAttribute("activeMenu", "consumos");
        return "consumo/list-simple";
    }

    @GetMapping("/roaming/nuevo")
    public String newRoamingForm(Model model) {
        ConsumoSimple s = new ConsumoSimple();
        s.setTipoSubtipo("ROAMING");
        model.addAttribute("simple", s);
        model.addAttribute("consumos", service.getAllForDropdown());
        model.addAttribute("subtipo", "ROAMING");
        model.addAttribute("modoEdicion", false);
        model.addAttribute("activeMenu", "consumos");
        return "consumo/form-simple";
    }

    @PostMapping("/roaming/nuevo")
    public String createRoaming(@ModelAttribute ConsumoSimple simple, RedirectAttributes ra) {
        try {
            service.createSimple(simple, "ROAMING");
            ra.addFlashAttribute("successMsg", "Consumo roaming registrado.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/consumos/roaming";
    }

    @PostMapping("/roaming/eliminar/{id}")
    public String deleteRoaming(@PathVariable int id, RedirectAttributes ra) {
        try {
            service.deleteSimple(id, "ROAMING");
            ra.addFlashAttribute("successMsg", "Consumo roaming eliminado.");
        } catch (DatabaseException e) {
            ra.addFlashAttribute("errorMsg", e.getUserMessage());
        }
        return "redirect:/consumos/roaming";
    }
}