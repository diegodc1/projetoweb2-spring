package com.web2.projetoweb2.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String email;
    private String senha;
    private Boolean ativo;

    @ManyToOne
    @JoinColumn(name = "idTipo")
    private TipoPerfil tipoPerfil;

    @ManyToOne
    @JoinColumn(name = "idEndereco")
    private Endereco endereco;

    private LocalDateTime dataCriacao;

}
