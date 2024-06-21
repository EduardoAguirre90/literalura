package com.alura.literalura.repository;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.DatosAutor;
import com.alura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {

    long countByIdiomasContaining(String idioma);

    //List<Libro> findByTituloContainingIgnoreCase(String titulo);

    @Query("SELECT l FROM Libro l WHERE " +
            ":year BETWEEN l.fechaDeNacimiento AND COALESCE(l.fechaDeMuerte, '9999')")
    List<Libro> findLibrosPorAutoresVivosEnAno(@Param("year") String year);


}
