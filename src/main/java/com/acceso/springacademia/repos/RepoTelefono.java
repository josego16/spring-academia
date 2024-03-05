package com.acceso.springacademia.repos;

import com.acceso.springacademia.modelos.Telefono;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoTelefono extends JpaRepository<Telefono, Integer> {
}
