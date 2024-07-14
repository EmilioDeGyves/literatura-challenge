package com.alura.literatura.models.respuesta;

import com.alura.literatura.models.libro.LibroDTO;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record Respuesta(
        @JsonAlias("results") List<LibroDTO> books
) {
}
