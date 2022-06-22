package com.api.rest.relaciones.bi.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "biblioteca")
public class Library {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String nombre;

    @OneToMany(mappedBy = "library",cascade = CascadeType.ALL)
    private Set<Book> libros =new HashSet<>();

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

    public Set<Book> getLibros() {
        return libros;
    }

    public void setLibros(Set<Book> libros) {
        this.libros = libros;
        for(Book libro :libros){
            libro.setLibrary(this);
        }
    }
}
