package com.alura.literalura.principal;


import com.alura.literalura.model.Datos;
import com.alura.literalura.model.DatosLibros;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import java.util.*;
import java.util.stream.Collectors;

//public class Principal {
//    private static Scanner teclado = new Scanner(System.in);
//    private static final String URL_BASE = "https://gutendex.com/books/";
//    private static ConsumoAPI consumoApi = new ConsumoAPI();
//    private static ConvierteDatos conversor = new ConvierteDatos();
//    private List<DatosLibros> datosLibros = new ArrayList<>();
//
//

//
//
//        private DatosLibros getDatosSerie() {
//            System.out.println("Escribe el nombre de la serie que deseas buscar");
//            var nombreSerie = teclado.nextLine();
//            var json = consumoApi.obtenerDatos(URL_BASE + nombreSerie.replace(" ", "+"));
//            System.out.println(json);
//            DatosLibros datos = conversor.obtenerDatos(json, DatosLibros.class);
//            return datos;
//        }
////        private void buscarEpisodioPorSerie() {
////            mostrarSeriesBuscadas();
////            System.out.println("Escribe el nombre de la serie de la cual quieres ver los Episodios");
////            var nombreSerie = teclado.nextLine();
////
////            Optional<Serie> serie = series.stream()
////                    .filter(s -> s.getTitulo().toLowerCase().contains(nombreSerie.toLowerCase()))
////                    .findFirst();
////
////            if(serie.isPresent()){
////                var serieEncontrada = serie.get();
////                List<DatosTemporadas> temporadas = new ArrayList<>();
////
////                for (int i = 1; i <= serieEncontrada.getTotalTemporadas(); i++) {
////                    var json = consumoApi.obtenerDatos(URL_BASE + serieEncontrada.getTitulo().replace(" ", "+") + "&season=" + i + API_KEY);
////                    DatosTemporadas datosTemporada = conversor.obtenerDatos(json, DatosTemporadas.class);
////                    temporadas.add(datosTemporada);
////                }
////                temporadas.forEach(System.out::println);
////
////                List<Episodio> episodios = temporadas.stream()
////                        .flatMap(d -> d.episodios().stream()
////                                .map(e -> new Episodio(d.numero(), e)))
////                        .collect(Collectors.toList());
////
////                serieEncontrada.setEpisodios(episodios);
////                repositorio.save(serieEncontrada);
////            }
////            //DatosSerie datosSerie = getDatosSerie();
////
////
////        }
////
//        private void buscarLibroWeb() {
//            DatosLibros datos = getDatosSerie();
//            datosLibros.add(datos);
//            System.out.println(datos);
//        }
public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private static ConsumoAPI consumoAPI = new ConsumoAPI();
    private static ConvierteDatos conversor = new ConvierteDatos();
    private static Scanner teclado = new Scanner(System.in);
    private List<DatosLibros> datosLibros = new ArrayList<>();
    //private List<Libro> libro;

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libros por titulo
                    2 - Bu
                    3 - Mo
                    4 - Buscar
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
//                case 3:
//                    mostrarSeriesBuscadas();
//                    break;
//                case 4:
//                    buscarSeriesPorTitulo();
//                    break;
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



    private DatosLibros getDatosLibros(){
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ","+"));
        System.out.println(json);
        DatosLibros datos = conversor.obtenerDatos(json, DatosLibros.class);
        return datos;
    }

    private void buscarLibroWeb() {
//        DatosLibros datos = getDatosLibros();
//        datosLibros.add(datos);
//        System.out.println(datos);
        System.out.println("Ingresa el nombre del libro que desea buscar");
        var tituloLibro = teclado.nextLine();
        var json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ","+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if(libroBuscado.isPresent()){
            System.out.println("Libro Encontrado");
            System.out.println(libroBuscado.get());
        }else {
            System.out.println("Libro no encontrado");
        }
    }

    private void mostrarLibrosBuscados() {
//        List<Libros> series = repositorio.findAll(); //documentación springData Core Concepts
//        findAll trae lo de la lista
//        List<Serie> series = new ArrayList<>();
//        series = datosSeries.stream()
//                .map(d -> new Serie(d))
//                .collect(Collectors.toList());

    }



}





//        private void buscarLibroWeb() {
//            DatosLibros datos = getDatosSerie();
//            datosLibros.add(datos);
//            System.out.println(datos);
//        }


//        var json = consumoAPI.obtenerDatos(URL_BASE);
//        System.out.println(json);
//
//        var datos = conversor.obtenerDatos(json, Datos.class);
//        System.out.println(datos);
//
//        //Top 10 libros mas descargados
//        System.out.println("10 libros mas descargados");
//        datos.resultados().stream()
//                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
//                .limit(10)
//                .map(l -> l.titulo().toUpperCase())
//                .forEach(System.out::println);
//
//        //Busqueda de libros por nombre
//        System.out.println("Ingresa el nombre del libro que desea buscar");
//        var tituloLibro = teclado.nextLine();
//        json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + tituloLibro.replace(" ","+"));
//        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
//        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
//                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
//                .findFirst();
//        if(libroBuscado.isPresent()){
//            System.out.println("Libro Encontrado");
//            System.out.println(libroBuscado.get());
//        }else {
//            System.out.println("Libro no encontrado");
//        }
//
//        //Trabajando con estadisticas
//
//        DoubleSummaryStatistics est = datos.resultados().stream()
//                .filter(d -> d.numeroDeDescargas() > 0 )
//                .collect(Collectors.summarizingDouble(DatosLibros::numeroDeDescargas));
//        System.out.println("Cantidad media de descargas: " +est.getAverage());
//        System.out.println("Cantidad máxima de descargas: " +est.getMax());
//        System.out.println("Cantidad mínima de descargas: " +est.getMin());
//        System.out.println("Cantidad de registros evaluados para calculas las estadisticas : " +est.getCount());

    //}



//}
