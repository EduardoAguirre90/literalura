package com.alura.literalura.principal;



import com.alura.literalura.model.*;
//import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;


import java.util.Scanner;
import java.util.*;
import java.util.stream.Collectors;


public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private static ConsumoAPI consumoAPI = new ConsumoAPI();
    private static ConvierteDatos conversor = new ConvierteDatos();
    private static Scanner teclado = new Scanner(System.in);
    //private List<Libro> libros;
    private List<DatosLibros> datosLibros = new ArrayList<>();
    private LibroRepository repositorio;
    //private AutorRepository autorRepository;

    //se añade clase repositorio desde la appLiteralura
    public Principal(LibroRepository repository) {
        this.repositorio = repository;
        //this.autorRepository = autorRepository;
    }

    //private List<Libro> libro;

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libros por titulo (BDD)
                    2 - Mostrar libros buscados
                    3 - Muestrta autores de los libros consultados
                    4 - Buscar autores vivos en determinado año de libros consultados
                    5 - Consultar cantidad de libros por idioma

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
                case 5:
                    CantidadDeLibrosPorIdioma();
                    break;
//                case 6:
//                    ListarAutoresVivosEnDeterminadoAño();
//                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }




    public Libro convertirADatosEntidad(DatosLibros datosLibros) {
        Libro libro = new Libro();
        libro.setTitulo(datosLibros.titulo());
        libro.setAutor(datosLibros.autor().stream().map(DatosAutor::nombre).collect(Collectors.toList()).toString());
        libro.setIdiomas(String.join(", ", datosLibros.idiomas()));
        libro.setNumeroDeDescargas(datosLibros.numeroDeDescargas());

        // Extraer fechas de nacimiento y muerte del autor
        if (!datosLibros.autor().isEmpty()) {
            DatosAutor primerAutor = datosLibros.autor().get(0);
            libro.setFechaDeNacimiento(primerAutor.fechaDeNacimiento());
            libro.setFechaDeMuerte(primerAutor.fechaDeMuerte());
        }
        return libro;
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
//            System.out.println("Fecha de nacimiento: " + libroEncontrado.fechaDeNacimiento());
//            System.out.println("Fecha de Muerte: " + libroEncontrado.fechaDeMuerte());
//            System.out.println("Datos Autor: " + libroEncontrado.autor());
            datosLibros.add(libroEncontrado);
            Libro libroEnt = convertirADatosEntidad(libroEncontrado);
            repositorio.save(libroEnt);
            System.out.println("Libro Encontrado");
            System.out.println(libroEnt);
        }else {
            System.out.println("Libro no encontrado");
        }
    }



    private void mostrarLibrosBuscados() {
        if (datosLibros.isEmpty()){
            System.out.println("No hay libros buscados");
        } else {
            System.out.println("Los libros buscados son: ");
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
        //de base de datos


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


    private void CantidadDeLibrosPorIdioma() {
        System.out.println("Ingresa el idioma para ver la estadística:(en ingles "+"en"+" y en español "+"es"+")");
        String idioma = teclado.nextLine();
        long cantidad = repositorio.countByIdiomasContaining(idioma);
        System.out.println("Cantidad de libros en " + idioma + ": " + cantidad);
    }



}









