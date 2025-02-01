package com.example.gestionhoteles.controller;


import com.example.gestionhoteles.model.Hotel;
import com.example.gestionhoteles.service.HotelServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/hotel")
@Tag(name = "Hoteles", description = "Operaciones relacionadas con hoteles")
public class HotelController {

    private final HotelServices hotelServices;

    public HotelController(HotelServices hotelServices) {
        this.hotelServices = hotelServices;
    }

    @PostMapping("/guardar")
    @Operation(
            summary = "Guardar un hotel en la base de datos",
            description = "Recibe en formato json los datos de un hotel." +
                    "\n\nEjemplo:\n\n" +
                    "```json\n" +
                    "{\n" +
                    "  \"nombre\": \"Hotel Montaña Verde\",\n" +
                    "  \"descripcion\": \"Un refugio en las montañas, ideal para escapadas de fin de semana.\",\n" +
                    "  \"categoria\": \"4 estrellas\",\n" +
                    "  \"piscina\": false,\n" +
                    "  \"localidad\": \"Cerro Azul\",\n" +
                    "  \"habitaciones\": []\n" +
                    "}"
    )
    public ResponseEntity<?> createHotel(@RequestBody Hotel hotel) {
        Optional<Hotel> hotel1 = hotelServices.existsByName(hotel.getNombre());
        if (hotel1.isPresent()) {
            return new ResponseEntity<>("Un hotel con ese nombre ya existe, cámbialo y vuelve a intentarlo", HttpStatus.BAD_REQUEST);
        }
        hotelServices.save(hotel);
        return new ResponseEntity<>("Hotel creado correctametne",HttpStatus.CREATED);
    }

    @GetMapping("/listar")
    @Operation(
            summary = "Visualizar todos los hoteles"
    )
    public ResponseEntity<?> getAllHoteles() {
        try {
            // COGEMOS TODOS LOS HOTELES
            List<Hotel> hoteles = hotelServices.findAll();
            if (hoteles.isEmpty()) { // SI LA LISTA ESTÁ VACÍA DEVOLVEMOS UNA RESPUESTA PERSONALIZADA
                return new ResponseEntity<>("Todavía no existen hoteles", HttpStatus.BAD_REQUEST);
            }
            // SI HAY HOTELES, DEVOLVEMOS LA LISTA
            return new ResponseEntity<>(hotelServices.findAll(),HttpStatus.OK);

        } catch (Exception e) { // SI HAY ERROR DEVOLVEMOS RESPUESTA PERSONALIZADA
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error al obtener todos los hoteles ", e);
        }
    }
    @GetMapping("/buscar/categoria/{categoria}")
    @Operation(
            summary = "Listar hoteles por categoría",
            description = "Recibe la categoría como parámetro de búsqueda"
    )
    public ResponseEntity<?> getHotelByCategoria(@PathVariable String categoria) {
        try {
            // COGEMOS TODOS LOS HOTELES
            List<Hotel> hoteles = hotelServices.findByCategoria(categoria);
            if (hoteles.isEmpty()) { // SI LA LISTA ESTÁ VACÍA DEVOLVEMOS UNA RESPUESTA PERSONALIZADA

                return new ResponseEntity<>("No existen hoteles con esa categoría", HttpStatus.BAD_REQUEST);
            }
            // SI HAY HOTELES, DEVOLVEMOS LA LISTA
            return new ResponseEntity<>(hotelServices.findByCategoria(categoria), HttpStatus.OK);

        } catch (Exception e) { // SI HAY ERROR DEVOLVEMOS RESPUESTA PERSONALIZADA
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error al obtener hoteles por categoría", e);
        }
    }

    @GetMapping("/buscar/localidad/{localidad}")
    @Operation(
            summary = "Listar hoteles por localidad",
            description = "Recibe la localidad como parámetro de búsqueda"
    )
    public ResponseEntity<?> getHotelByLocalidad(@PathVariable String localidad) {
        try {
            // COGEMOS TODOS LOS HOTELES
            List<Hotel> hoteles = hotelServices.findByLocalidad(localidad);

            if (hoteles.isEmpty()) { // SI LA LISTA ESTÁ VACÍA DEVOLVEMOS UNA RESPUESTA PERSONALIZADA
                return new ResponseEntity<>("No existen hoteles en esa localidad", HttpStatus.BAD_REQUEST);
            }

            // SI HAY HOTELES, DEVOLVEMOS LA LISTA
            return new ResponseEntity<>(hotelServices.findByLocalidad(localidad), HttpStatus.OK);

        } catch (Exception e) { // SI HAY ERROR DEVOLVEMOS RESPUESTA PERSONALIZADA
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Error al obtener hoteles por localidad", e);
        }
    }
}
