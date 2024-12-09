package com.alura.literalura.modelos;

import com.alura.literalura.dto.AutorDTO;
import com.alura.literalura.dto.LibroDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Entity
@Getter
@Setter
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @Column
    private String titulo;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinTable(
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Autor> autores = new LinkedList<>();

    @ElementCollection(fetch=FetchType.EAGER)
    @Column(name = "idioma", length = 3)
    @CollectionTable(name = "Libro_idiomas", joinColumns = @JoinColumn(name = "owner_id"))
    private List<String> idiomas = new LinkedList<>();


    public Libro(){}

    public Libro(LibroDTO dto){
        id = dto.id();
        titulo = dto.titulo();
        idiomas = dto.idiomas();

        for(AutorDTO autoresDto : dto.autores()){
            this.autores.add(new Autor(autoresDto));
        }

    }
}
