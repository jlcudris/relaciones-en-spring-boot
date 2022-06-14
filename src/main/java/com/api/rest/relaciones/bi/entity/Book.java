package com.api.rest.relaciones.bi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "libros")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //no puede ser nulo
    @NotNull
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "biblioteca_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Library library;
}
