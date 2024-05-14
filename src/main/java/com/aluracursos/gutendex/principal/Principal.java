package com.aluracursos.gutendex.principal;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.aluracursos.gutendex.model.Datos;
import com.aluracursos.gutendex.model.DatosLibros;
import com.aluracursos.gutendex.service.ConsumoAPI;
import com.aluracursos.gutendex.service.ConvierteDatos;

public class Principal {
    
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor =new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);

    public void muestraElMenu(){
        var json = consumoAPI.obtenerDatos(URL_BASE);
        //System.out.println(json);
        var datos = conversor.obtenerDatos(json, Datos.class);
        System.out.println(datos);

    //Top 10 libros más descargados
    System.out.println("Top 10 libros más descargados: ");
    datos.libros().stream()
            .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
            .limit(10)
            .map(l -> l.titulo().toUpperCase())
            .forEach(System.out::println);

    //Busqueda de libros por nombre
    System.out.println("Ingrese el nombre del libro que desea buscar: ");
    var busquedaLibro = teclado.nextLine();
    json = consumoAPI.obtenerDatos(URL_BASE + "?search=" + busquedaLibro.replace(" ", "+"));
    var resultadoBusqueda = conversor.obtenerDatos(json, Datos.class);
    Optional <DatosLibros> libroBuscado = resultadoBusqueda.libros().stream()
                .filter(l -> l.titulo().toUpperCase().contains(busquedaLibro.toUpperCase()))
                .findFirst();
    if(libroBuscado.isPresent()){
        System.out.println("Resultado de la busqueda: ");
        System.out.println(libroBuscado.get());
    }else {
        System.out.println("No hay resultados para la búsqueda.");
    }

    //Trabajando con estadísticas
    DoubleSummaryStatistics est = datos.libros().stream()
            .filter(d -> d.numeroDeDescargas() > 0)
            .collect(Collectors.summarizingDouble(DatosLibros::numeroDeDescargas));
    System.out.println("Cantidad media de descargas: " + est.getAverage());
    System.out.println("Cantidad máxima de descargas: " + est.getMax());
    System.out.println("Cantidad mínima de descargas: " + est.getMin());
    System.out.println("Cantidad de registros evaluados para calcular las estadísticas: " + est.getCount());

}
}
