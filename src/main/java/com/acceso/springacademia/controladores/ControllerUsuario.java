package com.acceso.springacademia.controladores;

import com.acceso.springacademia.modelos.Telefono;
import com.acceso.springacademia.modelos.Usuario;
import com.acceso.springacademia.repos.RepoRol;
import com.acceso.springacademia.repos.RepoTelefono;
import com.acceso.springacademia.repos.RepoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
public class ControllerUsuario {

    @Autowired
    private RepoUsuario repoUsuario;

    @Autowired
    private RepoTelefono repoTelefono;

    @Autowired
    private RepoRol repoRol;

    @GetMapping(path = "/")
    public String findAll(Model modelo) {
        List<Usuario> listUsuario = repoUsuario.findAll();
        modelo.addAttribute("usuarios", listUsuario);
        return "usuarios/usuarios";
    }

    @GetMapping("")
    public String findAllBis(Model modelo) {
        return findAll(modelo);
    }

    @GetMapping("/add")
    public String addUsuario(Model modelo) {
        modelo.addAttribute("usuario", new Usuario());
        modelo.addAttribute("telefonos", repoTelefono.findAll());
        modelo.addAttribute("roles", repoRol.findAll());
        return "usuarios/add";
    }

    @PostMapping("/add")
    public String addUsuario(
            @ModelAttribute("usuario") @NonNull Usuario usuario) {
        List<Telefono> listTelefonos = usuario.getTelefonos();
        if (listTelefonos != null) {
            for (Telefono telefono : listTelefonos) {
                if (telefono != null)
                    repoTelefono.save(telefono);
            }
        }
        repoUsuario.save(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/delete/{id}")
    public String deleteUsuarioForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional<Usuario> optUsuario = repoUsuario.findById(id);
        if (optUsuario.isPresent())
            modelo.addAttribute(
                    "usuario", optUsuario.get());
        else {
            modelo.addAttribute(
                    "mensaje", "El usuario consultado no existe.");
            return "error";
        }
        return "usuarios/delete";
    }

    @PostMapping("/delete/{id}")
    public String deleteUsuario(
            Model modelo,
            @PathVariable("id") @NonNull Long id) {
        try {
            repoUsuario.deleteById(id);
        } catch (Exception e) {
            modelo.addAttribute(
                    "mensaje", "El usuario no se puede eliminar porque tiene compras ya realizadas.");
            return "error";
        }
        return "redirect:/usuarios";
    }

    @GetMapping("/edit/{id}")
    public String editUsuarioForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional<Usuario> optUsuario = repoUsuario.findById(id);
        if (optUsuario.isPresent()) {
            modelo.addAttribute(
                    "usuario", optUsuario.get());
            modelo.addAttribute(
                    "roles", repoRol.findAll());
            return "usuarios/edit";
        } else {
            modelo.addAttribute("mensaje", "El usuario consultado no existe.");
            return "error";
        }
    }

    @GetMapping("/{id}/telefonos/add")
    public String usuarioAddPhoneForm(
            @PathVariable @NonNull Long id,
            Model modelo) {

        Optional<Usuario> optUsuario = repoUsuario.findById(id);

        if (optUsuario.isEmpty()) {
            modelo.addAttribute(
                    "mensaje", "El usuario no existe");
            return "error";
        }

        Telefono telefono = new Telefono();
        telefono.setUsuario(optUsuario.get());

        modelo.addAttribute(
                "telefono", telefono);
        modelo.addAttribute("usuario", optUsuario.get());

        return "usuarios/telefonos/add";
    }

    @PostMapping("/{id}/telefonos/add")
    public String usuarioAddPhone(
            @PathVariable @NonNull Long id,
            @ModelAttribute("telefono") @NonNull Telefono telefono,
            Model modelo) {

        Optional<Usuario> optUsuario = repoUsuario.findById(id);

        if (optUsuario.isEmpty()) {
            modelo.addAttribute(
                    "mensaje", "El usuario no existe");
            return "error";
        }

        repoTelefono.save(telefono);
        return "redirect:/usuarios/" + id + "/telefonos";
    }

    @GetMapping("/{id}/telefonos")
    public String getPhonesByUserId(
            @PathVariable @NonNull Long id,
            Model modelo) {

        Optional<Usuario> optUsuario = repoUsuario.findById(id);

        if (optUsuario.isEmpty()) {
            modelo.addAttribute(
                    "mensaje", "El usuario no existe");
            return "error";
        }

        modelo.addAttribute(
                "usuarios", repoUsuario.findAll());
        modelo.addAttribute(
                "usuarioActual", optUsuario.get());
        modelo.addAttribute(
                "telefonos", optUsuario.get().getTelefonos());
        return "usuarios/telefonos/telefonos";
    }

    @GetMapping("/{idUser}/telefonos/{idTel}/edit")
    public String editPhonesByUserIdForm(
            @PathVariable @NonNull Long idUser,
            @PathVariable @NonNull Integer idTel,
            Model modelo) {

        Optional<Usuario> optUsuario = repoUsuario.findById(idUser);
        Optional<Telefono> oTelefono = repoTelefono.findById(idTel);

        if (optUsuario.isEmpty() ||
                oTelefono.isEmpty()) {
            modelo.addAttribute(
                    "mensaje", "El teléfono o usuario no existen");
            return "error";
        }

        modelo.addAttribute("usuario", optUsuario.get());
        modelo.addAttribute("telefono", oTelefono.get());
        return "usuarios/telefonos/edit";
    }

    @GetMapping("/{idUser}/telefonos/{idTel}/delete")
    public String deletePhonesByUserIdForm(
            @PathVariable @NonNull Long idUser,
            @PathVariable @NonNull Integer idTel,
            Model modelo) {

        Optional<Usuario> optUsuario = repoUsuario.findById(idUser);
        Optional<Telefono> oTelefono = repoTelefono.findById(idTel);

        if (optUsuario.isEmpty() ||
                oTelefono.isEmpty()) {
            modelo.addAttribute(
                    "mensaje", "El teléfono o usuario no existen");
            return "error";
        }

        modelo.addAttribute("usuario", optUsuario.get());
        modelo.addAttribute(
                "telefono", oTelefono.get());

        return "usuarios/telefonos/delete";
    }

    @PostMapping("/{idUser}/telefonos/{idTel}/delete")
    public String deletePhonesByUserId(
            @PathVariable @NonNull Long idUser,
            @PathVariable @NonNull Integer idTel, Model modelo) {
        Optional<Usuario> optUsuario = repoUsuario.findById(idUser);
        Optional<Telefono> oTelefono = repoTelefono.findById(idTel);
        if (optUsuario.isEmpty() ||
                oTelefono.isEmpty()) {
            modelo.addAttribute(
                    "mensaje", "El teléfono o usuario no existen");
            return "error";
        }
        if (optUsuario.get().getId() != oTelefono.get().getUsuario().getId()) {
            modelo.addAttribute(
                    "mensaje", "El teléfono no pertenece al usuario");
            return "error";
        }
        repoTelefono.delete(oTelefono.get());
        return "redirect:/usuarios/" + optUsuario.get().getId() + "/telefonos";
    }
}