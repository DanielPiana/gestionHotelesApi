package com.example.gestionhoteles.repository;

import com.example.gestionhoteles.model.Habitacion;
import com.example.gestionhoteles.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HabitacionRepository extends JpaRepository<Habitacion,Integer> {


    @Query("SELECT h FROM Habitacion h WHERE h.hotel.idHotel = :idHotel " +
            "AND h.tamano BETWEEN :tamanoMin AND :tamanoMax " +
            "AND h.precioNoche BETWEEN :precioMin AND :precioMax " +
            "AND h.ocupada = false")
    List<Habitacion> findBySizePriceAvailable(
            @Param("idHotel") int idHotel,
            @Param("tamanoMin") int tamanoMin,
            @Param("tamanoMax") int tamanoMax,
            @Param("precioMin") double precioMin,
            @Param("precioMax") double precioMax
    );
}