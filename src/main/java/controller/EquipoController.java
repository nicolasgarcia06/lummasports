package controller;

import exception.BusinessException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import model.Equipo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import service.EquipoService;

@Controller
@RequestMapping("/equipos")
@RequiredArgsConstructor
public class EquipoController {

    private final EquipoService service;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("equipos", service.obtenerTodos());
        return "equipos";
    }

    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("equipo", new Equipo());
        return "nuevo-equipo";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute Equipo equipo,
                          BindingResult bindingResult,
                          Model model) {

        if (bindingResult.hasErrors()) {
            return "nuevo-equipo";
        }

        try {
            service.guardar(equipo);
            return "redirect:/equipos";
        } catch (BusinessException e) {
            model.addAttribute("errorNegocio", e.getMessage());
            return "nuevo-equipo";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("equipo", service.buscarPorId(id));
        return "editar-equipo";
    }

    @PostMapping("/actualizar")
    public String actualizar(@Valid @ModelAttribute Equipo equipo,
                             BindingResult bindingResult,
                             Model model) {

        if (bindingResult.hasErrors()) {
            return "editar-equipo";
        }

        try {
            service.actualizar(equipo);
            return "redirect:/equipos";
        } catch (BusinessException e) {
            model.addAttribute("errorNegocio", e.getMessage());
            return "editar-equipo";
        }
    }

    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable Long id, Model model) {
        try {
            service.borrar(id);
            return "redirect:/equipos";
        } catch (BusinessException e) {
            model.addAttribute("equipos", service.obtenerTodos());
            model.addAttribute("errorListado", e.getMessage());
            return "equipos";
        }
    }
}

