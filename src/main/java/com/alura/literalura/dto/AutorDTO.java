package com.alura.literalura.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AutorDTO(

        @JsonAlias("name")
        String nombre,

        @JsonAlias("birth_year")
        LocalDate fechaNacimiento,

        @JsonAlias("death_year")
        LocalDate fechaFallecimiento
) {
        @Override
        public String toString() {
                return "---------------------------------\n" +
                        "Autor:\n" +
                        "Nombre:              " + nombre + "\n" +
                        "Fecha de Nacimiento: " + fechaNacimiento + "\n" +
                        "Fecha de Fallecimiento: " + fechaFallecimiento + "\n" +
                        "---------------------------------";
        }

}
