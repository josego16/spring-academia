package com.acceso.springacademia.controladores;

import com.acceso.springacademia.modelos.Alumno;
import com.acceso.springacademia.modelos.Telefono;
import com.acceso.springacademia.repos.RepoAlumno;
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
@RequestMapping("/alumnos")
public class ControllerAlumno {
    @Autowired
    private RepoAlumno repoAlumno;
    @Autowired
    private RepoTelefono repoTelefono;
    @Autowired
    private RepoRol repoRol;

    @GetMapping("/")
    public String findAll(Model modelo) {
        List<Alumno> listAlumno = repoAlumno.findAll();
        modelo.addAttribute("alumnos", listAlumno);
        return "alumnos/alumnos";
    }

    @GetMapping("")
    public String findAllv2(Model modelo) {
        return findAll(modelo);
    }

    @GetMapping("/add")
    public String addAlumno(Model modelo) {
        modelo.addAttribute("alumno", new Alumno());
        modelo.addAttribute("telefonos", repoTelefono.findAll());
        modelo.addAttribute("roles", repoRol.findAll());
        return "alumnos/add";
    }

    @PostMapping("/add")
    public String addAlumno(@ModelAttribute("alumno") @NonNull Alumno alumno) {
        List<Telefono> listTelefonos = alumno.getTelefonos();
        if (listTelefonos != null) {
            for (Telefono telefono : listTelefonos) {
                if (telefono != null) repoTelefono.save(telefono);
            }
        }
        repoAlumno.save(alumno);

        return "redirect:/alumnos";
    }

    @GetMapping("/delete/{id}")
    public String deleteAlumnoForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional<Alumno> optAlumno = repoAlumno.findById(id);
        if (optAlumno.isPresent()) modelo.addAttribute("alumno", optAlumno.get());
        else {
            modelo.addAttribute("mensaje", "El alumno no existe.");
            return "error";
        }
        return "alumnos/delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteAlumno(Model modelo, @PathVariable("id") @NonNull Long id) {
        try {
            repoAlumno.deleteById(id);
        } catch (Exception e) {
            modelo.addAttribute("mensaje", "El alumno no se puede eliminar");
            return "error";
        }

        return "redirect:/alumnos";
    }

    @GetMapping("/edit/{id}")
    public String editAlumnoForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional<Alumno> optAlumno = repoAlumno.findById(id);
        if (optAlumno.isPresent()) {
            modelo.addAttribute("alumno", optAlumno.get());
            modelo.addAttribute("roles", repoRol.findAll());
            return "alumnos/edit";
        } else {
            modelo.addAttribute("mensaje", "El alumno no existe.");
            return "error";
        }
    }

    @GetMapping("/{id}/telefonos/add")
    public String alumnoAddPhoneForm(@PathVariable @NonNull Long id, Model modelo) {

        Optional<Alumno> optAlumno = repoAlumno.findById(id);

        if (optAlumno.isEmpty()) {
            modelo.addAttribute("mensaje", "El alumno no existe");
            return "error";
        }

        Telefono telefono = new Telefono();

        modelo.addAttribute("telefono", telefono);
        modelo.addAttribute("alumno", optAlumno.get());

        return "alumnos/telefonos/add";
    }

    @PostMapping("/{id}/telefonos/add")
    public String alumnoAddPhone(@PathVariable @NonNull Long id, @ModelAttribute("telefono") @NonNull Telefono telefono, Model modelo) {

        Optional<Alumno> optAlumno = repoAlumno.findById(id);

        if (optAlumno.isEmpty()) {
            modelo.addAttribute("mensaje", "El alumno no existe");
            return "error";
        }
        telefono.setAlumno(optAlumno.get());

        repoTelefono.save(telefono);

        return "redirect:/alumnos";
    }

    @GetMapping("/{id}/telefonos")
    public String getPhonesByAlumnoId(@PathVariable @NonNull Long id, Model modelo) {

        Optional<Alumno> optAlumno = repoAlumno.findById(id);

        if (optAlumno.isEmpty()) {
            modelo.addAttribute("mensaje", "El alumno no existe");
            return "error";
        }

        modelo.addAttribute("alumnos", repoAlumno.findAll());
        modelo.addAttribute("alumnoActual", optAlumno.get());
        modelo.addAttribute("telefonos", optAlumno.get().getTelefonos());

        modelo.addAttribute("telefono", new Telefono());

        return "alumnos/telefonos/telefonos";
    }


    @GetMapping("/{idAlumno}/telefonos/{idTel}/edit")
    public String editPhonesByUserIdForm(@PathVariable @NonNull Long idAlumno, @PathVariable @NonNull Integer idTel, Model modelo) {

        Optional<Alumno> optAlumno = repoAlumno.findById(idAlumno);
        Optional<Telefono> oTelefono = repoTelefono.findById(idTel);

        if (optAlumno.isEmpty() || oTelefono.isEmpty()) {
            modelo.addAttribute("mensaje", "El teléfono o alumno no existen");
            return "error";
        }

        modelo.addAttribute("alumno", optAlumno.get());
        modelo.addAttribute("telefono", oTelefono.get());
        return "alumnos/telefonos/edit";
    }
}