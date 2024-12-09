package com.alura.literalura.servicios;

import com.alura.literalura.dto.AutorDTO;
import com.alura.literalura.dto.LibroDTO;
import com.alura.literalura.modelos.Libro;
import com.alura.literalura.repositorio.AuthorRepository;
import com.alura.literalura.repositorio.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PostgreSQL {

    @Autowired
    private GutendexApi gutendexApi;

    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AuthorRepository authorRepository;



    public LibroDTO traerLibro(String titulo){
        Optional<List<Libro>> coincidencias = libroRepository.findByTitulo(titulo);

        if(coincidencias.isEmpty()){
            return null;
        }

        //Trae la primera coincidencia
        Optional<Libro> libro = coincidencias.get().stream().findFirst();

        if(libro.isPresent()){
            System.out.println("Traido de la BD");
            List<AutorDTO> autores = libro.get()
                    .getAutores()
                    .stream()
                    .map( autor ->
                        new AutorDTO(
                            autor.getNombre(),
                            autor.getFechaNacimiento(),
                            autor.getFechaFallecimiento())
                    )
                    .toList();

            return new LibroDTO(
                    libro.get().getId(),
                    libro.get().getTitulo(),
                    autores,
                    libro.get().getIdiomas());

        }else {
            //Si no existe en la BD lo busca en la api
            LibroDTO aGuardar = gutendexApi.traerLibros(titulo).getFirst();
            libroRepository.save(new Libro(aGuardar));
            System.out.println("Traido de la api");
            return aGuardar;
        }
    }

    public List<LibroDTO> traerLibros(){

        return libroRepository.findAll()
                .stream()
                .map(libro ->
                        new LibroDTO(
                                libro.getId(),
                                libro.getTitulo(),

                                libro.getAutores().stream().map(
                                        autor -> new AutorDTO(
                                                autor.getNombre(),
                                                autor.getFechaNacimiento(),
                                                autor.getFechaFallecimiento()
                                        )
                                ).toList(),

                                libro.getIdiomas())
                )
                .toList();
    }

    public List<AutorDTO> traerAutores(){

        return authorRepository.findAll().stream()
                .map(autor -> new AutorDTO(
                        autor.getNombre(),
                        autor.getFechaNacimiento(),
                        autor.getFechaFallecimiento()
                        )
                )
                .toList();
    }
    public List<AutorDTO> traerAutores(LocalDate fechaABuscar){
        return authorRepository.traerAutores(fechaABuscar, fechaABuscar).stream()
                .map(autor -> new AutorDTO(
                                autor.getNombre(),
                                autor.getFechaNacimiento(),
                                autor.getFechaFallecimiento()
                        )
                )
                .toList();
    }
}
