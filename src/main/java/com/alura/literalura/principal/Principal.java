package com.alura.literalura.principal;



import com.alura.literalura.model.Datos;
import com.alura.literalura.model.DatosAutor;
import com.alura.literalura.model.DatosLibros;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.*;
import java.util.stream.Collectors;


public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private static ConsumoAPI consumoAPI = new ConsumoAPI();
    private static ConvierteDatos conversor = new ConvierteDatos();
    private static Scanner teclado = new Scanner(System.in);
    private List<Libro> libros;
    private List<DatosLibros> datosLibros = new ArrayList<>();
    private LibroRepository repositorio;

    //private List<Libro> libro;

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libros por titulo
                    2 - Mostrar libros buscados
                    3 - Muestrta autores de los libros consultados
                    4 - Buscar autores vivos en determinado año de libros consultados
                    5 - Top 
                    6 - Buscar 


                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    mostrarLibrosBuscados();
                    break;
                case 3:
                    mostrarListaAutores();
                    break;
                case 4:
                    buscarAutoresVivosPorFecha();
                    break;
//                case 5:
//                    buscarTop5Series();
//                    break;
//                case 6:
//                    buscarSeriesPorCategoria();
//                    break;
//                case 7:
//                    filtrarSeriesPorTemporadaYEvaluacion();
//                    break;
//                case 8:
//                    buscarEpisodiosPorTitulo(); //estos son los metodos utiliozados
//                    break;
//                case 9:
//                    buscarTop5Episodios();
//                    break;


                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }




    private void buscarLibroWeb() {
        System.out.println("Ingresa el nombre del libro que desea buscar");
        var tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ","+"));
        System.out.println("JSON recibido: " + json);
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if(libroBuscado.isPresent()){
            DatosLibros libroEncontrado = libroBuscado.get();//
            datosLibros.add(libroEncontrado);//
            System.out.println("Libro Encontrado");
            System.out.println(libroEncontrado);

        }else {
            System.out.println("Libro no encontrado");
        }
    }

    private void mostrarLibrosBuscados() {
        if (datosLibros.isEmpty()){
            System.out.println("No hay libros buscados");
        } else {
            datosLibros.forEach(System.out::println);
        }
    }

    private void mostrarListaAutores() {
        if(datosLibros.isEmpty()){
            System.out.println("No hay libros buscados");
        } else {
            datosLibros.stream().map(DatosLibros::autor).distinct()
                    .forEach(System.out::println);
        }
    }


    private void buscarAutoresVivosPorFecha() {
        System.out.println("Ingresa el año para buscar autores vivos:");
        int año = teclado.nextInt();
        teclado.nextLine(); // Consumir la nueva línea

        List<DatosAutor> autoresVivos = datosLibros.stream()
                .flatMap(libro -> libro.autor().stream())
                .filter(autor -> {
                    int nacimiento = Integer.parseInt(autor.fechaDeNacimiento());
                    int muerte = autor.fechaDeMuerte().isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(autor.fechaDeMuerte());
                    return año >= nacimiento && año <= muerte;
                })
                .collect(Collectors.toList());
        if (autoresVivos.isEmpty()){
            System.out.println("No se encontro ningun autor que viva en el año " + año);
        } else {
            System.out.println("Los autores que vivieron en el año " + año + " son:");
            autoresVivos.forEach(System.out::println);
        }


    }



}









