package com.alura.literalura.principal;



import com.alura.literalura.model.Datos;
import com.alura.literalura.model.DatosLibros;
import com.alura.literalura.model.Libro;
import com.alura.literalura.repository.LibroRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import java.util.Scanner;
import java.util.*;


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
                case 3:
                    mostrarListaAutores();
                    break;
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
