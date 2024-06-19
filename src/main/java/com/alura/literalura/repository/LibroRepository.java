package com.alura.literalura.repository;

import com.alura.literalura.model.DatosAutor;
import com.alura.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro,Long> {

    long countByIdiomasContaining(String idioma);

//    @Query("SELECT l FROM Libro l JOIN l.autores a WHERE :año BETWEEN a.fechaDeNacimiento AND COALESCE(a.fechaDeMuerte, :año)")
//    List<Libro> findLibrosConAutoresVivosEnAño(int año);

}
