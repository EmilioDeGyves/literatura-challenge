package com.alura.literatura.models.autor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("""
        SELECT a FROM Autor a
        WHERE a.nacimiento < :anio AND a.fallecimiento > :anio
       """)
    List<Autor> findAutoresVivos(@Param("anio") int anio);

    Optional<Autor> findByNombre(String nombre);
}
