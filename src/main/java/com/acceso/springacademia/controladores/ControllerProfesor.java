package com.acceso.springacademia.controladores;

import com.acceso.springacademia.modelos.Asignatura;
import com.acceso.springacademia.modelos.Profesor;
import com.acceso.springacademia.repos.RepoAsignatura;
import com.acceso.springacademia.repos.RepoProfesor;
import com.acceso.springacademia.repos.RepoRol;
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
    @Autowired
    private RepoAsignatura repoAsignatura;

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

    @GetMapping("/{id}/asignaturas/add")
    public String profesorAddAsignaturaForm(@PathVariable @NonNull Long id, Model modelo) {
        Optional<Profesor> optProfesor = repoProfesor.findById(id);

        if (optProfesor.isEmpty()) {
            modelo.addAttribute("mensaje", "El profesor no existe");
            return "error";
        }
        Asignatura asignatura = new Asignatura();
        asignatura.setProfesor(optProfesor.get());

        modelo.addAttribute("asignatura", asignatura);
        modelo.addAttribute("profesor", optProfesor.get());

        return "profesores/asignaturas/add";
    }

    @PostMapping("/{id}/asignaturas/add")
    public String profesorAddAsignatura(
            @PathVariable @NonNull Long id,
            @ModelAttribute("asignatura") @NonNull Asignatura asignatura,
            Model modelo) {

        Optional<Profesor> optProfesor = repoProfesor.findById(id);

        if (optProfesor.isEmpty()) {
            modelo.addAttribute("mensaje", "El profesor no existe");
            return "error";
        }

        repoAsignatura.save(asignatura);
        return "redirect:/profesores/" + id + "/asignaturas";
    }

    @GetMapping("/{id}/asignaturas")
    public String getAsignaturaByProfesorId(@PathVariable @NonNull Long id, Model modelo) {

        Optional<Profesor> optProfesor = repoProfesor.findById(id);

        if (optProfesor.isEmpty()) {
            modelo.addAttribute("mensaje", "El profesor no existe");
            return "error";
        }

        modelo.addAttribute("profesores", repoProfesor.findAll());
        modelo.addAttribute("profesorActual", optProfesor.get());
        modelo.addAttribute("asignaturas", optProfesor.get().getAsignaturas());

        modelo.addAttribute("asignatura", new Asignatura());

        return "profesores/asignaturas/asignaturas";
    }


    @GetMapping("/{idProfesor}/asignaturas/{idAsig}/edit")
    public String editAsignaturasByprofesorIdForm(@PathVariable @NonNull Long idProfesor, @PathVariable @NonNull Long idAsig, Model modelo) {

        Optional<Profesor> optProfesor = repoProfesor.findById(idProfesor);
        Optional<Asignatura> optAsignatura = repoAsignatura.findById(idAsig);

        if (optProfesor.isEmpty() || optAsignatura.isEmpty()) {
            modelo.addAttribute("mensaje", "La asignatura o profesor no existen");
            return "error";
        }

        modelo.addAttribute("profesor", optProfesor.get());
        modelo.addAttribute("asignatura", optAsignatura.get());
        return "profesores/asignaturas/edit";
    }

    @GetMapping("/{idProfesor}/asignaturas/{idAsig}/delete")
    public String deleteAsignturasByProfesorIdForm(
            @PathVariable @NonNull Long idProfesor,
            @PathVariable @NonNull Long idAsig,
            Model modelo) {

        Optional<Profesor> optProfesor = repoProfesor.findById(idProfesor);
        Optional<Asignatura> optAsignatura = repoAsignatura.findById(idAsig);

        if (optProfesor.isEmpty() || optAsignatura.isEmpty()) {
            modelo.addAttribute("mensaje", "El teléfono o profesor no existen");
            return "error";
        }
        modelo.addAttribute("profesor", optProfesor.get());
        modelo.addAttribute("telefono", optAsignatura.get());

        return "profesores/asignaturas/delete";
    }

    @PostMapping("/{idProfesor}/asignaturas/{idAsig}/delete")
    public String deleteAsignaturaByProfesorId(
            @PathVariable @NonNull Long idProfesor,
            @PathVariable @NonNull Long idAsig, Model modelo) {
        Optional<Profesor> optProfesor = repoProfesor.findById(idProfesor);
        Optional<Asignatura> optAsignatura = repoAsignatura.findById(idAsig);
        if (optProfesor.isEmpty() || optAsignatura.isEmpty()) {
            modelo.addAttribute("mensaje", "El teléfono o profesor no existen");
            return "error";
        }
        if (optProfesor.get().getId() != optAsignatura.get().getProfesor().getId()) {
            modelo.addAttribute("mensaje", "El teléfono no pertenece al profesor");
            return "error";
        }
        repoAsignatura.delete(optAsignatura.get());
        return "redirect:/profesores/" + optProfesor.get().getId() + "/asignaturas";
    }
}