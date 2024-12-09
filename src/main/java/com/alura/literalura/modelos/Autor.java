package com.alura.literalura.modelos;

import com.alura.literalura.dto.AutorDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "autores")
@Getter
@Setter
public class Autor {

    @Id
    @Column
    private String nombre;
    @Column
    private LocalDate fechaNacimiento;
    @Column
    private LocalDate fechaFallecimiento;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "autores")
    private List<Libro> libros;

    public Autor(){}

    public Autor(AutorDTO dto){
        nombre = dto.nombre();
        fechaNacimiento = dto.fechaNacimiento();
        fechaFallecimiento = dto.fechaFallecimiento();
    }
}
