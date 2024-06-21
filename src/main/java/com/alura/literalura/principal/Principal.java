package com.alura.literalura.principal;



import com.alura.literalura.model.*;
//import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.time.LocalDate;
import java.util.Scanner;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private static ConsumoAPI consumoAPI = new ConsumoAPI();
    private static ConvierteDatos conversor = new ConvierteDatos();
    private static Scanner teclado = new Scanner(System.in);
    //private List<Libro> libros;
    private List<DatosLibros> datosLibros = new ArrayList<>();
    private final LibroRepository repositorio;
    private final AutorRepository autorRepository;

    //se añade clase repositorio desde la appLiteralura
    @Autowired
    public Principal(LibroRepository repository, AutorRepository autorRepository) {
        this.repositorio = repository;
        this.autorRepository = autorRepository;
    }

    //private List<Libro> libro;

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libros por titulo (BDD)
                    2 - Mostrar libros buscados
                    3 - Muestrta autores de los libros consultados
                    4 - Buscar autores vivos en determinado año de libros consultados (BDD)
                    5 - Consultar cantidad de libros por idioma
                    6 - Top 10 libros mas descargados

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

               case 6:
                    Top10LibrosMasDescargados();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }




    ////////////////////////AÑADIR NUEVO LIBRO BDD////////////////////////////////////////
    public Libro convertirADatosEntidad(DatosLibros datosLibros) {
        Libro libro = new Libro();
        libro.setTitulo(datosLibros.titulo());
//        libro.setAutor(datosLibros.autor().stream()
//                .map(DatosAutor::nombre)
//                .collect(Collectors.joining(", ")));
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

//    public Autor convertirADatosAutorEntidad(DatosAutor datosAutor) {
//        Autor autor = new Autor();
//        autor.setNombre(datosAutor.nombre());
//        autor.setFechaDeNacimiento(datosAutor.fechaDeNacimiento());
//        autor.setFechaDeMuerte(datosAutor.fechaDeMuerte());
//        return autor;
//    }

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
            datosLibros.add(libroEncontrado);
            Libro libroEnt = convertirADatosEntidad(libroEncontrado);
            repositorio.save(libroEnt);
            System.out.println("Libro Encontrado");
            System.out.println(libroEnt);
        }else {
            System.out.println("Libro no encontrado");
        }
    }


/////////////////////////MOSTRAR LIBROS BUSCADOS//////////////////////////////////
    private void mostrarLibrosBuscados() {
        if (datosLibros.isEmpty()){
            System.out.println("No hay libros buscados");
        } else {
            System.out.println("Los libros buscados son: ");
            datosLibros.forEach(System.out::println);
        }
    }
///////////////////////MOSTRAR LISTA DE AUTORES CONSULTADOS/////////////////////
    private void mostrarListaAutores() {
        if(datosLibros.isEmpty()){
            System.out.println("No hay libros buscados");
        } else {
            datosLibros.stream().map(DatosLibros::autor).distinct()
                    .forEach(System.out::println);
        }
    }

////////////////BUSCAR AUTORES VIVOS EN CIERTA FECHA////////////////////////
    private void buscarAutoresVivosPorFecha() {
        System.out.println("Ingresa el año para buscar autores vivos:");
        int año = teclado.nextInt();
        teclado.nextLine(); // Consumir la nueva línea


/////////////////////SIN BASE DE DATOS/////////////////////////////////////////
//        List<DatosAutor> autoresVivos = datosLibros.stream()
//                .flatMap(libro -> libro.autor().stream())
//                .filter(autor -> {
//                    int nacimiento = Integer.parseInt(autor.fechaDeNacimiento());
//                    int muerte = autor.fechaDeMuerte().isEmpty() ? Integer.MAX_VALUE : Integer.parseInt(autor.fechaDeMuerte());
//                    return año >= nacimiento && año <= muerte;
//                })
//                .collect(Collectors.toList());
//        if (autoresVivos.isEmpty()){
//            System.out.println("No se encontro ningun autor que viva en el año " + año);
//        } else {
//            System.out.println("Los autores que vivieron en el año " + año + " son:");
//
//            autoresVivos.forEach(System.out::println);
//        }

        //////////////////////////////CON BASE DE DATOS/////////////////////////
        String yearString = String.valueOf(año);
        List<Libro> libros = repositorio.findLibrosPorAutoresVivosEnAno(yearString);

        if (libros.isEmpty()) {
            System.out.println("No se encontró ningún autor que viva en el año " + año);
        } else {
            System.out.println("Libros encontrados con autores vivos en el año " + año + ":");
            libros.forEach(libro -> {

//                String autores = libro.getAutores().stream()
//                        .map(Autor::getNombre)
//                        .collect(Collectors.joining(", "));
                System.out.println("Libro: " + libro.getTitulo() + ", del Autor: "
                        + libro.getAutor() + " (" + libro.getFechaDeNacimiento() + "-"
                        + libro.getFechaDeMuerte() + ")");
                });
        }

//
    }

///////////////ESTADISTICA DE LIBROS POR IDIOMA/////////////////////////////
    private void CantidadDeLibrosPorIdioma() {
        System.out.println("Ingresa el idioma para ver la estadística:(en ingles "+"en"+" y en español "+"es"+")");
        String idioma = teclado.nextLine();
        long cantidad = repositorio.countByIdiomasContaining(idioma);
        System.out.println("Cantidad de libros en " + idioma + ": " + cantidad);
    }

    ////////////////TOP 10 LIBROS MAS DESCARGADOS///////////////////////////////
    private void Top10LibrosMasDescargados() {
        System.out.println("Los 10 libros mas descargados son: ");
        teclado.nextLine();
        var tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ","+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        datosBusqueda.resultados().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);
    }

}









