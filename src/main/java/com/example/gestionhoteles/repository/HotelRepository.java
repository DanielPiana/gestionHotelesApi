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
    @Query("SELECT h FROM Habitacion h WHERE " +
            "h.tamano = :tamano AND " +
            "h.precioNoche BETWEEN :precioMin AND :precioMax AND " +
            "h.ocupada = false AND " +
            "h.hotel.idHotel = :idHotel")
    List<Habitacion> buscarHabitaciones(
            @Param("tamano") String tamano,
            @Param("precioMin") double precioMin,
            @Param("precioMax") double precioMax,
            @Param("idHotel") int idHotel
    );
}