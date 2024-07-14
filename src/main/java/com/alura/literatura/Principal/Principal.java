package com.alura.literatura.Principal;

import com.alura.literatura.models.autor.Autor;
import com.alura.literatura.models.autor.AutorRepository;
import com.alura.literatura.models.libro.Libro;
import com.alura.literatura.models.libro.LibroRepository;
import com.alura.literatura.service.Buscador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
public class Principal {

    private Buscador buscador = new Buscador();

    private LibroRepository libroRepository;
    private AutorRepository autorRepository;

    public Principal(LibroRepository libroRepository, AutorRepository autorRepository){
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    private Scanner user = new Scanner(System.in);

    public void muestraMenu() {
        int opcion = -1;
        while(opcion != 0){
            var menu = """
                    1 - Buscar Libro
                    2 - Listar Libros
                    3 - Listar Autores
                    4 - Buscar autor vivo en cierto año
                    5 - Buscar libros por idiomas
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = user.nextInt();
            user.nextLine();

            switch (opcion){
                case 1:
                    buscaLibro();
                    break;
                case 2:
                    listarLibros();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    autorVivo();
                    break;
                case 5:
                    buscarPorIdioma();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
    }

    public void buscaLibro() {
        System.out.println("Ingresa el título del libro que quieres encontrar:");
        String target = user.nextLine();

        List<Libro> guardados = libroRepository.findByTituloContainsIgnoreCase(target);

        if (!guardados.isEmpty()) {
            guardados.forEach(libro -> {
                System.out.println(libro);
                System.out.println("Idiomas: " + libro.getIdiomas());
            });
            return;
        }

        Libro libro = null;
        int pagina = 1;

        while (libro == null && pagina < 10) {
            libro = buscador.buscarLibro(target, pagina, autorRepository);
            pagina++;
        }

        if (libro != null) {
            libroRepository.save(libro);
            System.out.println(libro);
        } else {
            System.out.println("Libro no encontrado");
        }
    }

    public void listarLibros(){
        libroRepository.findAll().forEach(System.out::println);
    }

    public void listarAutores(){
        autorRepository.findAll().forEach(System.out::println);
    }

    public void autorVivo(){
        System.out.println("Ingresa un año: ");
        int target = user.nextInt();

        List<Autor> autores = autorRepository.findAutoresVivos(target);
        if (!autores.isEmpty()) {
            autores.forEach(System.out::println);
        } else {
            System.out.println("No se encontraron autores vivos en el año especificado.");
        }
    }

    public void buscarPorIdioma(){
        System.out.println("Ingresa el codigo del idioma que desees:" + "\n" +
                "es - español" + "\n" +
                "en - inglés" + "\n" +
                "fr - frances" + "\n" +
                "pt - portugues");
        String target = user.next();
        user.nextLine();

        List<Libro> libros = libroRepository.findByIdioma(target);
        if(!libros.isEmpty()) {
            libros.forEach(System.out::println);
        }
        else{
            System.out.println("Libros con ese idioma no encontrados");
        }
    }
}
