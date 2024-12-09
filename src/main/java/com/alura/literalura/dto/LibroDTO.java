package com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroDTO (

        @JsonAlias("id")
        Long id,

        @JsonAlias("title")
        String titulo,

        @JsonAlias("authors")
        List<AutorDTO> autores,

        @JsonAlias("languages")
        List<String> idiomas
){
        @Override
        public String toString() {
                return "---------------------------------\n" +
                        "ID:       " + id + "\n" +
                        "Título:   " + titulo + "\n" +
                        "Autores:  " + autores + "\n" +
                        "Idiomas:  " + idiomas + "\n" +
                        "---------------------------------";
        }

}
