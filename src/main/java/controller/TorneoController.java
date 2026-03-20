package controller;

import exception.BusinessException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import model.Torneo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import service.EquipoService;
import service.TorneoServiceImpl;

@Controller
@RequestMapping("/torneos")
@RequiredArgsConstructor
public class TorneoController {

    private final TorneoServiceImpl torneoService;
    private final EquipoService equipoService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("torneos", torneoService.obtenerTorneosConEquipoCampeon());
        return "torneos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("torneo", new Torneo());
        model.addAttribute("equipos", equipoService.obtenerTodos());
        return "nuevo-torneo";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Torneo torneo,
                          BindingResult bindingResult,
                          Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("equipos", equipoService.obtenerTodos());
            return "nuevo-torneo";
        }

        try {
            torneoService.guardar(torneo);
            return "redirect:/torneos";
        } catch (BusinessException e) {
            model.addAttribute("equipos", equipoService.obtenerTodos());
            model.addAttribute("errorNegocio", e.getMessage());
            return "nuevo-torneo";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("torneo", torneoService.buscarPorId(id));
        model.addAttribute("equipos", equipoService.obtenerTodos());
        return "editar-torneo";
    }

    @PostMapping("/actualizar")
    public String actualizar(@Valid @ModelAttribute Torneo torneo,
                             BindingResult bindingResult,
                             Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("equipos", equipoService.obtenerTodos());
            return "editar-torneo";
        }

        try {
            torneoService.actualizar(torneo);
            return "redirect:/torneos";
        } catch (BusinessException e) {
            model.addAttribute("equipos", equipoService.obtenerTodos());
            model.addAttribute("errorNegocio", e.getMessage());
            return "editar-torneo";
        }
    }

    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id) {
        torneoService.borrar(id);
        return "redirect:/torneos";
    }
}

