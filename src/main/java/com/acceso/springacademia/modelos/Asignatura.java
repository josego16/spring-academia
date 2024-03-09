package com.acceso.springacademia.modelos;

import jakarta.persistence.*;
import jdk.dynalink.linker.LinkerServices;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Asignatura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50)
    private String nombre;
    private int curso;
    private String ciclo;

    @ManyToOne
    Profesor profesor;
    @ManyToOne
    Alumno alumno;
}