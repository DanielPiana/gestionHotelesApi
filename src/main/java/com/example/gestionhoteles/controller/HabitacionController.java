package com.example.gestionhoteles.controller;

import com.example.gestionhoteles.model.Habitacion;
import com.example.gestionhoteles.service.HabitacionServices;
import com.example.gestionhoteles.service.HotelServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("/api/habitacion")
public class HabitacionController {
    private final HabitacionServices habitacionServices;

    private final HotelServices hotelServices;

    public HabitacionController(HabitacionServices habitacionServices, HotelServices hotelServices) {
        this.habitacionServices = habitacionServices;
        this.hotelServices = hotelServices;
    }

    //SE PUEDE USAR PutMapping PERO PatchMapping ES MAS ADECUADO PARA ACTUALIZACIONES PARCIALES
    @PatchMapping("/update/ocupada/{idHabitacion}")
    public ResponseEntity<Habitacion> updateOcupada(@PathVariable int idHabitacion) {
        // Buscamos la habitación con el ID proporcionado
        Optional<Habitacion> habitacionOptional = habitacionServices.findById(idHabitacion);

        // Si la habitación existe
        if (habitacionOptional.isPresent()) {
            // La guardamos
            Habitacion habitacion = habitacionOptional.get();

            // Cambiamos el valor de ocupado al contrario
            habitacion.setOcupada(!habitacion.isOcupada());

            // Y lo guardamos
            habitacionServices.save(habitacion);

            return new ResponseEntity<>(habitacion, HttpStatus.OK); // 200 OK con la habitación actualizada
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found si no se encuentra la habitación
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> createHabitacion(@RequestBody Habitacion habitacion) {
        // Validar que el hotel existe en la base de datos
        if (habitacion.getHotel() != null) {
            int hotelId = habitacion.getHotel().getIdHotel();
            if (!hotelServices.existsById(hotelId)) {
                return new ResponseEntity<>("Hotel no encontrado", HttpStatus.BAD_REQUEST);
            }
        }

        // Si el hotel existe, guardamos la habitación
        habitacionServices.save(habitacion);

        return new ResponseEntity<>(HttpStatus.CREATED);  // 201 Created
    }


    @GetMapping("/all/personalizado") //METODO PARA UN PRINT PERSONALIZADO Y VER EL ID DE HOTEL
    public ResponseEntity<List<Map<String, Object>>> getAllHabitacionesPersonalizado() {
        List<Habitacion> habitaciones = habitacionServices.findAll();
        List<Map<String, Object>> respuesta = new ArrayList<>();

        for (Habitacion h : habitaciones) {
            Map<String, Object> datos = new HashMap<>();
            datos.put("idHabitacion", h.getIdHabitacion());
            datos.put("tamano", h.getTamano());
            datos.put("precioNoche", h.getPrecioNoche());
            datos.put("desayuno", h.isDesayuno());
            datos.put("ocupada", h.isOcupada());
            datos.put("idHotel", h.getHotel() != null ? h.getHotel().getIdHotel() : null);
            respuesta.add(datos);
        }

        return new ResponseEntity<>(respuesta, HttpStatus.OK);
    }
    @GetMapping("/get/personalizado/{idHotel}/{tamanoMin}/{tamanoMax}/{precioMin}/{precioMax}")
    public ResponseEntity<List<Habitacion>> getBySizePriceAvailable(
            @PathVariable int idHotel,
            @PathVariable double precioMax,
            @PathVariable double precioMin,
            @PathVariable int tamanoMax,
            @PathVariable int tamanoMin) {
        try {
            List<Habitacion> habitaciones = habitacionServices.findBySizePriceAvailable(idHotel, tamanoMin, tamanoMax, precioMin, precioMax);

            if (habitaciones.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Si no hay habitaciones disponibles
            }

            return new ResponseEntity<>(habitaciones, HttpStatus.OK); // Si hay habitaciones disponibles
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error al obtener todos los hoteles ", e);
        }
    }
    @GetMapping("/all")
    public List<Habitacion> getAllHabitaciones() {
        try {
            return habitacionServices.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error al obtener todos los hoteles ", e);
        }
    }
    @DeleteMapping("delete/{idHabitacion}")
    public ResponseEntity<?> deleteHabitacion(@PathVariable int idHabitacion) {
        habitacionServices.deleteById(idHabitacion);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}