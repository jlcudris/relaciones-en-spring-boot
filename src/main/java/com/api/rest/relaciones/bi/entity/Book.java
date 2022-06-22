package com.api.rest.relaciones.bi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "libros",uniqueConstraints = {@UniqueConstraint(columnNames = {"nombre"})})
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public Book() {
    }
}
