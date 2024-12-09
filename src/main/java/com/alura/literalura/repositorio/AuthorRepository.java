package com.alura.literalura.repositorio;

import com.alura.literalura.modelos.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Autor, Long> {

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento > :nacimiento AND a.fechaFallecimiento < :fallecimiento")
    List<Autor> traerAutores(@Param("nacimiento") LocalDate nacimiento, @Param("fallecimiento") LocalDate fallecimiento);
}
