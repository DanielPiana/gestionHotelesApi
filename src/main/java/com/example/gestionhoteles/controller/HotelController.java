package com.example.gestionhoteles.controller;


import com.example.gestionhoteles.model.Hotel;
import com.example.gestionhoteles.service.HotelServices;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;


@RestController
@RequestMapping("/api/hotel")
public class HotelController {

    private final HotelServices hotelServices;

    public HotelController(HotelServices hotelServices) {
        this.hotelServices = hotelServices;
    }

    @PostMapping("/save")
    public ResponseEntity<?> createHotel(@RequestBody Hotel hotel) {
        hotelServices.save(hotel);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    /*
    * */
    @GetMapping("/all")
    public List<Hotel> getAllHoteles() {
        try {
            return hotelServices.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error al obtener todos los hoteles ", e);
        }
    }
    @GetMapping("/get/categoria/{categoria}")
    public List<Hotel> getHotelByCategoria(@PathVariable String categoria) {
        try {
            return hotelServices.findByCategoria(categoria);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error al obtener hoteles por categoría", e);
        }
    }

    @GetMapping("/get/localidad/{localidad}")
    public List<Hotel> getHotelByLocalidad(@PathVariable String localidad) {
        try {
            return hotelServices.findByLocalidad(localidad);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error al obtener hoteles por localidad", e);
        }
    }
}
