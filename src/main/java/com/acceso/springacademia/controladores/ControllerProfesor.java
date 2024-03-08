package com.acceso.springacademia.controladores;

import com.acceso.springacademia.modelos.Profesor;
import com.acceso.springacademia.repos.RepoProfesor;
import com.acceso.springacademia.repos.RepoRol;
import com.acceso.springacademia.repos.RepoTelefono;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("profesores")
public class ControllerProfesor {
    @Autowired
    private RepoProfesor repoProfesor;
    @Autowired
    private RepoRol repoRol;

    @GetMapping("/")
    public String findAll(Model modelo) {
        List<Profesor> listProfesor = repoProfesor.findAll();
        modelo.addAttribute("profesores", listProfesor);
        return "profesores/profesores";
    }

    @GetMapping("")
    public String findAllv2(Model modelo) {
        return findAll(modelo);
    }

    @GetMapping("/add")
    public String addprofesor(Model modelo) {
        modelo.addAttribute("profesor", new Profesor());
        modelo.addAttribute("roles", repoRol.findAll());
        return "profesores/add";
    }

    @PostMapping("/add")
    public String addProfesor(@ModelAttribute("profesor") @NonNull Profesor profesor) {
        repoProfesor.save(profesor);

        return "redirect:/profesores";
    }

    @GetMapping("/delete/{id}")
    public String deleteprofesorForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional<Profesor> optprofesor = repoProfesor.findById(id);
        if (optprofesor.isPresent()) modelo.addAttribute("profesor", optprofesor.get());
        else {
            modelo.addAttribute("mensaje", "El profesor no existe.");
            return "error";
        }
        return "profesores/delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteProfesor(Model modelo, @PathVariable("id") @NonNull Long id) {
        try {
            repoProfesor.deleteById(id);
        } catch (Exception e) {
            modelo.addAttribute("mensaje", "El profesor no se puede eliminar");
            return "error";
        }

        return "redirect:/profesores";
    }

    @GetMapping("/edit/{id}")
    public String editprofesorForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional<Profesor> optprofesor = repoProfesor.findById(id);
        if (optprofesor.isPresent()) {
            modelo.addAttribute("profesor", optprofesor.get());
            modelo.addAttribute("roles", repoRol.findAll());
            return "profesores/edit";
        } else {
            modelo.addAttribute("mensaje", "El profesor no existe.");
            return "error";
        }
    }
}