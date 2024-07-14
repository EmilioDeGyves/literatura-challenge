package com.alura.literatura.models.libro;

import com.alura.literatura.models.autor.Autor;
import com.alura.literatura.models.autor.AutorRepository;
import jakarta.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")

public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> idiomas;

    private Integer descargas;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores;

    public Libro(LibroDTO datos, AutorRepository autorRepository) {
        this.titulo = datos.titulo();
        this.idiomas = datos.idiomas();
        this.autores = datos.autores().stream()
                .map(dto -> {
                    Optional<Autor> existente = autorRepository.findByNombre(dto.nombre());
                    return existente.orElseGet(() -> new Autor(dto));
                })
                .collect(Collectors.toList());
        this.descargas = datos.descargas();
    }


    public Libro(){};
    // Getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public List<String> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idiomas = idiomas;
    }

    public Integer getDescargas() {
        return descargas;
    }

    public void setDescargas(Integer descargas) {
        this.descargas = descargas;
    }



    @Override
    public String toString() {
        return "-----------LIBRO-----------" + '\n'+
                "titulo: '" + titulo + '\n' +
                "idiomas: " + idiomas + '\n'+
                "descargas: " + descargas+ '\n'+
                autores.stream()
                        .map(autor -> "autor: " + autor.getNombre())
                        .collect(Collectors.joining("\n"))+ '\n'+
                "---------------------------"
                ;
    }
}
