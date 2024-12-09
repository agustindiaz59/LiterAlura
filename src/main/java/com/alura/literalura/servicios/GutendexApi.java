package com.alura.literalura.servicios;

import com.alura.literalura.dto.GutendexResponse;
import com.alura.literalura.dto.LibroDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

@Service
public class GutendexApi {

    private String base = "https://gutendex.com/books?";



    /**
     * Metodo comun para las solicitudes a la api de gutendex
     * @param urlParams Parametros de URL para la consulta
     * @return GutendexResponse Representacion de la respuesta
     */
    private GutendexResponse realizarSolicitud(String ...urlParams){
        StringBuilder sb = new StringBuilder();
        sb.append(base);
        Arrays.stream(urlParams).forEach(sb::append);

        HttpClient cliente = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(sb.toString()))
                .build();

        try {

            HttpResponse<String> response = cliente.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                // Registrar el módulo para soportar tipos de Java 8 (JSR310)

                objectMapper.registerModule(new JavaTimeModule());

                // Configuración para evitar errores en la serialización de fechas
                objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);


                return objectMapper.readValue(response.body(), GutendexResponse.class);

            }else {
                System.out.println("Error en la solicitud: " + response.statusCode());
                return null;
            }

        } catch (IOException | InterruptedException e) {

            System.out.println("Error trayendo los datos de la api de Gutendex");
            throw new RuntimeException(e);

        }
    }



    /**
     * Trae todos los libros de la api
     * @return lista de DTO's de Libro
     */
    public List<LibroDTO> traerLibros(){
        GutendexResponse response = realizarSolicitud("");

        return response.libros()
                .stream()
                .map(libro ->
                        new LibroDTO(
                                libro.id(),
                                libro.titulo(),
                                libro.autores(),
                                libro.idiomas()
                        )
                ).toList();
    }



    /**
     * Busca coincidencias dado el nombre del autor o del libro y las devuelve
     * @param nombreOAutor Nombre del autor o del libro a buscar
     * @return lista de DTO's de Libro
     */
    public List<LibroDTO> traerLibros(String nombreOAutor){

        GutendexResponse response = realizarSolicitud("search=" + nombreOAutor.replace(" ","%20"));

        return response.libros()
                .stream()
                .map(libro ->
                        new LibroDTO(
                                libro.id(),
                                libro.titulo(),
                                libro.autores(),
                                libro.idiomas()
                        )
                )
                .toList();
    }



    /**
     * Trae un libro por id
     * @param ids id del libro buscado
     * @return DTO de libro buscado
     */
    public LibroDTO traerlibro(int ids){
        GutendexResponse response = realizarSolicitud("ids=" + ids);

        return response.libros().getFirst();
    }


}
