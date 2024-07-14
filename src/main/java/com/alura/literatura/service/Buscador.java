package com.alura.literatura.service;

import com.alura.literatura.models.autor.AutorRepository;
import com.alura.literatura.models.libro.Libro;
import com.alura.literatura.models.libro.LibroDTO;
import com.alura.literatura.models.respuesta.Respuesta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

public class Buscador {
    private ApiService api = new ApiService();
    private ConversorDatos conversor = new ConversorDatos();
    private final String url = "https://gutendex.com/books/?page=";


    public Libro buscarLibro(String libro, int page, AutorRepository repository) {
        var json = api.consumoApi(url + page);
        var datos = conversor.obtenerDatos(json, Respuesta.class);
        List<LibroDTO> books = datos.books();
        Optional<LibroDTO> target = books.stream()
                .filter(b -> b.titulo().toLowerCase().contains(libro.toLowerCase()))
                .findFirst();

        return target.map(libroDTO -> new Libro(libroDTO, repository)).orElse(null);
    }
}
