package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "libros")

public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(unique = true)
    private String titulo;

    private String nombre;
    private String idiomas;
    private Double numeroDeDescargas;
    private String fechaDeNacimiento;
    private String fechaDeMuerte;

    private String autor;

//    @ManyToMany(mappedBy = "autores")
//    private List<Libro> libros;

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(String idiomas) {
        this.idiomas = idiomas;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getFechaDeMuerte() {
        return fechaDeMuerte;
    }

    public void setFechaDeMuerte(String fechaDeMuerte) {
        this.fechaDeMuerte = fechaDeMuerte;
    }

    // Método auxiliar para obtener el año a partir de una fecha en formato yyyy-MM-dd
    private int obtenerAnio(String fecha) {
        if (fecha != null && !fecha.isEmpty()) {
            return Integer.parseInt(fecha.substring(0, 4));
        }
        return 0;
    }


    @Override
    public String toString() {
        return
                "DatosLibros" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", fechaNacimientoAutor='" + fechaDeNacimiento + '\'' +
                ", fechaMuerteAutor='" + fechaDeMuerte + '\'' +
                ", idiomas='" + idiomas + '\'' +
                ", numeroDeDescargas='" + numeroDeDescargas + '\'';
    }

}

