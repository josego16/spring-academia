package com.acceso.springacademia.modelos;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Telefono {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int numero;
    private int codigoPais;
    @ManyToOne
    Usuario usuario;
    @ManyToOne
    Alumno alumno;
}