package com.acceso.springacademia.modelos;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Telefono {
    @Id
    private int id;
    private int numero;
    private int codigoPais;
    @ManyToOne
    Usuario usuario;
}