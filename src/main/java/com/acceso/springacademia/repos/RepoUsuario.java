package com.acceso.springacademia.repos;

import com.acceso.springacademia.modelos.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoUsuario extends JpaRepository<Usuario, Long> {
}
