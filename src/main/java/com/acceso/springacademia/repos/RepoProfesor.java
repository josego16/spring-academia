package com.acceso.springacademia.repos;

import com.acceso.springacademia.modelos.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepoProfesor extends JpaRepository<Profesor,Long> {
}