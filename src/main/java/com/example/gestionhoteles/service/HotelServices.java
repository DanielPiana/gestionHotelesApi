package com.example.gestionhoteles.service;

import com.example.gestionhoteles.model.Hotel;
import com.example.gestionhoteles.repository.HotelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelServices {
    private final HotelRepository hotelRepository;

    public HotelServices(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public <S extends Hotel> S save(S entity) {
        return hotelRepository.save(entity);
    }

    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }

    public List<Hotel> findByCategoria(String categoria) {
        return hotelRepository.findByCategoria(categoria);
    }

    public List<Hotel> findByLocalidad(String localidad) {
        return hotelRepository.findByLocalidad(localidad);
    }

    public boolean existsById(Integer integer) {
        return hotelRepository.existsById(integer);
    }
}