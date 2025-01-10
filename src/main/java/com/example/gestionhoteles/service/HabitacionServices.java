package com.example.gestionhoteles.service;

import com.example.gestionhoteles.model.Habitacion;
import com.example.gestionhoteles.repository.HabitacionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabitacionServices {

    private final HabitacionRepository habitacionRepository;

    public HabitacionServices(HabitacionRepository habitacionRepository) {
        this.habitacionRepository = habitacionRepository;
    }

    public <S extends Habitacion> S save(S entity) {
        return habitacionRepository.save(entity);
    }

    public void deleteById(Integer integer) {
        habitacionRepository.deleteById(integer);
    }

    public List<Habitacion> findAll() {
        return habitacionRepository.findAll();
    }

    public Optional<Habitacion> findById(Integer integer) {
        return habitacionRepository.findById(integer);
    }

    public List<Habitacion> findBySizePriceAvailable(int idHotel, int tamanoMin, int tamanoMax, double precioMin, double precioMax) {
        return habitacionRepository.findBySizePriceAvailable(idHotel,tamanoMin,tamanoMax,precioMin,precioMax);
    }
}
