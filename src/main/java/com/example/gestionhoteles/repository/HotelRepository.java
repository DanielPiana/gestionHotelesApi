package com.example.gestionhoteles.repository;

import com.example.gestionhoteles.model.Habitacion;
import com.example.gestionhoteles.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel,Integer> {//Ese integer es por el id de hotel
//Esta clase sirve para hacer consultas personalizadas
/*Búsqueda de habitaciones de un hotel por tamaño y precio
(rango minimo→máximo). Solo mostrará aquellas habitaciones que
estén marcadas como libres*/

    //LISTA DE HOTELES CON UNA CATEGORIA EN CONCRETO
    @Query("SELECT h FROM Hotel h WHERE h.categoria = :categoria")
    List<Hotel> findByCategoria(String categoria);

    //LISTA DE HOTELES CON UNA LOCALIDAD EN CONCRETO
    @Query("SELECT h FROM Hotel h WHERE h.localidad = :localidad")
    List<Hotel> findByLocalidad(String localidad);


}