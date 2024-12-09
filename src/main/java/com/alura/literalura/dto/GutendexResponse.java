package com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GutendexResponse (

    @JsonAlias(value = "count")
    int cantDeResultados,

    @JsonAlias("results")
    List<LibroDTO> libros,

    @JsonAlias("download_count")
    int cantDeDescargas
)
{
    @Override
    public String toString() {
        return "GutendexResponse{" +
                "cantDeResultados=" + cantDeResultados +
                "libros=" + libros +
                "cantDeDescargas=" + cantDeDescargas +
                '}';
    }
}
