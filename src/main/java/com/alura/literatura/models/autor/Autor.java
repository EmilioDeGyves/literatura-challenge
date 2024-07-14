package com.alura.literatura.models.autor;

import com.alura.literatura.models.libro.Libro;
import jakarta.persistence.*;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nombre;

    private Integer nacimiento;
    private Integer fallecimiento;

    @ManyToMany(mappedBy = "autores", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(AutorDTO a) {
        this.nombre = a.nombre();
        this.nacimiento = a.nacimiento();
        this.fallecimiento = a.fallecimiento();
    }

    public Autor(){}

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

    public Integer getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(Integer nacimiento) {
        this.nacimiento = nacimiento;
    }

    public Integer getFallecimiento() {
        return fallecimiento;
    }

    public void setFallecimiento(Integer fallecimiento) {
        this.fallecimiento = fallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return "Autor:'" + nombre + '\'' + "\n" +
                "nacimiento:" + nacimiento + "\n"+
                "fallecimiento:" + fallecimiento + "\n"+
                "libros:" + (libros != null ? libros.stream().map(Libro::getTitulo).toList() : "[]")+ "\n";
    }
}
