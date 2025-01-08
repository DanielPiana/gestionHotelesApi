package com.example.gestionhoteles.repository;

import com.example.gestionhoteles.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel,Integer> {//Ese integer es por el id de hotel
//Esta clase sirve para hacer consultas personalizadas
}
