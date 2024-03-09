package com.acceso.springacademia.modelos;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String nombre;
    private String apellido;
    private boolean enabled;
    @Column(length = 100)
    private String email;
    @Column(length = 25)
    private String username;
    @Column(length = 100)
    private String password;

    @ManyToMany
    private List<Rol> roles;

    @OneToMany
    @JoinColumn(name = "profesor_id")
    List<Asignatura> asignaturas;
}