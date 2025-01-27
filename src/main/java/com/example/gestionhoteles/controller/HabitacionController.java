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
    public ResponseEntity<?> updateOcupada(@PathVariable int idHabitacion) {
        // BUSCAMOS LA HABITACION CON EL ID PROPORCIONADO
        Optional<Habitacion> habitacionOptional = habitacionServices.findById(idHabitacion);

        // SI LA HABITACION EXISTE
        if (habitacionOptional.isPresent()) {
            // LA COGEMOS
            Habitacion habitacion = habitacionOptional.get();

            // CAMBIAMOS EL VALOR DE OCUPADO AL CONTRARIO
            habitacion.setOcupada(!habitacion.isOcupada());

            // LA GUARDAMOS CON EL CAMBIO DE ESTADO
            habitacionServices.save(habitacion);

            return new ResponseEntity<>(habitacion, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Esa habitación no existe", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<?> createHabitacion(@RequestBody Habitacion habitacion) {
        // VALIDAMOS QUE EL HOTEL DE LA HABITACION EXISTA EN LA BASE DE DATOS
        if (habitacion.getHotel() != null) {
            int hotelId = habitacion.getHotel().getIdHotel();
            if (!hotelServices.existsById(hotelId)) { // SI EL HOTEL NO EXISTE, MOSTRAMOS MENSAJE PERSONALIZADO
                return new ResponseEntity<>("Hotel no encontrado", HttpStatus.BAD_REQUEST);
            }
        }
        // SI EL HOTEL EXISTE, GUARDAMOS LA HABITACION
        habitacionServices.save(habitacion);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping("/all/personalizado") //METODO PARA UN PRINT PERSONALIZADO Y VER EL ID DE HOTEL
    public ResponseEntity<?> getAllHabitacionesPersonalizado() {
        // COGEMOS TODAS LAS HABITACIONES
        List<Habitacion> habitaciones = habitacionServices.findAll();

        if (habitaciones.isEmpty()) { // SI LA LISTA ESTÁ VACÍA DEVOLVEMOS UNA RESPUESTA PERSONALIZADA
            return new ResponseEntity<>("Todavía no existen habitaciones", HttpStatus.BAD_REQUEST);
        }

        List<Map<String, Object>> respuesta = new ArrayList<>();

        // RECORREMOS LA LISTA DE HABITACIONES AÑADIENDOLE EL ID DEL HOTEL AL QUE PERTENECEN
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
    public ResponseEntity<?> getBySizePriceAvailable(
            @PathVariable int idHotel,
            @PathVariable double precioMax,
            @PathVariable double precioMin,
            @PathVariable int tamanoMax,
            @PathVariable int tamanoMin) {
        try {
            // COGEMOS TODAS LAS HABITACIONES
            List<Habitacion> habitaciones = habitacionServices.findBySizePriceAvailable(idHotel, tamanoMin, tamanoMax, precioMin, precioMax);

            if (habitaciones.isEmpty()) { // SI LA LISTA ESTÁ VACÍA DEVOLVEMOS UNA RESPUESTA PERSONALIZADA
                return new ResponseEntity<>("No hay habitaciones con esas características", HttpStatus.BAD_REQUEST);
            }
            // SI HAY HOTELES, DEVOLVEMOS LA LISTA
            return new ResponseEntity<>(habitaciones, HttpStatus.OK);
        } catch (Exception e) { // SI HAY ERROR DEVOLVEMOS RESPUESTA PERSONALIZADA
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error al obtener todos los hoteles ", e);
        }
    }
    @GetMapping("/all")
    public ResponseEntity<?> getAllHabitaciones() {
        try {
            // COGEMOS TODAS LAS HABITACIONES
            List<Habitacion> habitaciones = habitacionServices.findAll();

            if (habitaciones.isEmpty()) {// SI LA LISTA ESTÁ VACÍA DEVOLVEMOS UNA RESPUESTA PERSONALIZADA
                return new ResponseEntity<>("Todavía no existen habitaciones", HttpStatus.BAD_REQUEST);
            }
            // SI HAY HOTELES, DEVOLVEMOS LA LISTA
            return new ResponseEntity<>(habitaciones, HttpStatus.OK);

        } catch (Exception e) { // SI HAY ERROR DEVOLVEMOS RESPUESTA PERSONALIZADA
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error al obtener todos los hoteles ", e);
        }
    }
    @DeleteMapping("delete/{idHabitacion}")
    public ResponseEntity<?> deleteHabitacion(@PathVariable int idHabitacion) {
        Optional<Habitacion> habitacion = habitacionServices.findById(idHabitacion);
        if (!habitacion.isPresent()) { // SI EXISTE LA HABITACION, LA BORRAMOS
            return new ResponseEntity<>("No existe una habitación con ese ID", HttpStatus.BAD_REQUEST);
        }
        habitacionServices.deleteById(idHabitacion);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}