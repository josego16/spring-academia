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

    /**
     * Devuelve la lista de usuarios
     */
    @GetMapping(path = "/")
    public String findAll(Model modelo) {
        List<Usuario> lUsuario = repoUsuario.findAll();
        modelo.addAttribute("usuarios", lUsuario);
        return "usuarios/usuarios";
    }

    /**
     * Devuelve la lista de usuarios
     */
    @GetMapping("")
    public String findAllBis(Model modelo) {
        return findAll(modelo);
    }

    /**
     * Devuelve el formulario para añadir un nuevo usuario
     */
    @GetMapping("/add")
    public String addUsuario(Model modelo) {
        modelo.addAttribute("usuario", new Usuario());
        modelo.addAttribute("telefonos", repoTelefono.findAll());
        modelo.addAttribute("roles", repoRol.findAll());
        return "usuarios/add";
    }

    /**
     * Recoge los datos del formulario anterior para crear un nuevo usuario
     */
    @PostMapping("/add")
    public String addUsuario(@ModelAttribute("usuario") @NonNull Usuario usuario) {
        List<Telefono> telefonos = usuario.getTelefonos();
        if (telefonos != null) {
            for (Telefono telefono : telefonos) {
                if (telefono != null) repoTelefono.save(telefono);
            }
        }
        repoUsuario.save(usuario);

        return "redirect:/usuarios";
    }

    /**
     * Muestra un formulario para confirmar el borrado del usuario
     */
    @GetMapping("/delete/{id}")
    public String deleteUsuarioForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional<Usuario> oUsuario = repoUsuario.findById(id);
        if (oUsuario.isPresent()) modelo.addAttribute("usuario", oUsuario.get());
        else {
            modelo.addAttribute("mensaje", "El usuario consultado no existe.");
            return "error";
        }
        return "usuarios/delete";
    }

    /**
     * Elimina el usuario de la base de datos si es posible
     */
    @PostMapping("/delete/{id}")
    public String deleteUsuario(Model modelo, @PathVariable("id") @NonNull Long id) {
        try {
            repoUsuario.deleteById(id);
        } catch (Exception e) {
            modelo.addAttribute("mensaje", "El usuario no se puede eliminar porque tiene compras ya realizadas.");
            return "error";
        }

        return "redirect:/usuarios";
    }

    /**
     * Muestra un formulario para editar el usuario, para guardar hacemos POST de nuevo a "/add"
     * porque el repositorio de Spring, al hacer repo.save(), va a generar un usario nuevo si no
     * se proporciona ID y actualizará uno existente si el objeto tiene ID.
     */
    @GetMapping("/edit/{id}")
    public String editUsuarioForm(Model modelo, @PathVariable("id") @NonNull Long id) {
        Optional<Usuario> oUsuario = repoUsuario.findById(id);
        if (oUsuario.isPresent()) {
            modelo.addAttribute("usuario", oUsuario.get());
            modelo.addAttribute("roles", repoRol.findAll());
            return "usuarios/edit";
        } else {
            modelo.addAttribute("mensaje", "El usuario consultado no existe.");
            return "error";
        }
    }


    /**
     * Muestra el formulario para añadir un teléfono al usuario dado
     */
    @GetMapping("/{id}/telefonos/add")
    public String usuarioAddPhoneForm(@PathVariable @NonNull Long id, Model modelo) {

        Optional<Usuario> oUsuario = repoUsuario.findById(id);

        if (oUsuario.isEmpty()) {
            modelo.addAttribute("mensaje", "El usuario no existe");
            return "error";
        }

        Telefono telefono = new Telefono();

        modelo.addAttribute("telefono", telefono);
        modelo.addAttribute("usuario", oUsuario.get());

        return "usuarios/telefonos/add";
    }

    /**
     * Añade un teléfono al usuario proporcionado
     */
    @PostMapping("/{id}/telefonos/add")
    public String usuarioAddPhone(@PathVariable @NonNull Long id, @ModelAttribute("telefono") @NonNull Telefono telefono, Model modelo) {

        Optional<Usuario> oUsuario = repoUsuario.findById(id);

        if (oUsuario.isEmpty()) {
            modelo.addAttribute("mensaje", "El usuario no existe");
            return "error";
        }
        telefono.setUsuario(oUsuario.get());

        repoTelefono.save(telefono);

        return "redirect:/usuarios";
    }

    @GetMapping("/{id}/telefonos")
    public String getPhonesByUserId(@PathVariable @NonNull Long id, Model modelo) {

        Optional<Usuario> oUsuario = repoUsuario.findById(id);

        if (oUsuario.isEmpty()) {
            modelo.addAttribute("mensaje", "El usuario no existe");
            return "error";
        }

        modelo.addAttribute("usuarios", repoUsuario.findAll());
        modelo.addAttribute("usuarioActual", oUsuario.get());
        modelo.addAttribute("telefonos", oUsuario.get().getTelefonos());

        modelo.addAttribute("telefono", new Telefono());

        return "usuarios/telefonos/telefonos";
    }


    @GetMapping("/{idUser}/telefonos/{idTel}/edit")
    public String editPhonesByUserIdForm(@PathVariable @NonNull Long idUser, @PathVariable @NonNull Integer idTel, Model modelo) {

        Optional<Usuario> oUsuario = repoUsuario.findById(idUser);
        Optional<Telefono> oTelefono = repoTelefono.findById(idTel);

        if (oUsuario.isEmpty() || oTelefono.isEmpty()) {
            modelo.addAttribute("mensaje", "El teléfono o usuario no existen");
            return "error";
        }

        modelo.addAttribute("usuario", oUsuario.get());
        modelo.addAttribute("telefono", oTelefono.get());
        return "usuarios/telefonos/edit";
    }

}