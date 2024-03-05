package com.acceso.springacademia.repos;

import com.acceso.springacademia.modelos.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoAsignatura extends JpaRepository<Asignatura, Long> {
}