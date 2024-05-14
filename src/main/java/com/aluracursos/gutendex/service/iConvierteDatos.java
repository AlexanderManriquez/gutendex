package com.aluracursos.gutendex.service;
 //El nombre de la clase SIEMPRE debe comenzar con mayúscula
public interface iConvierteDatos {
    
    <T> T obtenerDatos(String json, Class<T> clase);
}
