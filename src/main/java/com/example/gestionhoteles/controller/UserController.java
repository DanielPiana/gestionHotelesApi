package com.example.gestionhoteles.controller;


import com.example.gestionhoteles.model.Habitacion;
import com.example.gestionhoteles.model.User;
import com.example.gestionhoteles.service.UserServices;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
public class UserController {

    private final UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping("/login")
    @Operation(
            summary = "Iniciar sesión",
            description = "Recibe credenciales de usuario y devuelve un token JWT si son correctas."
    )
    public ResponseEntity<?> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        // RECUPERAMOS EL USUARIO EXISTENTE BASÁNDONOS EN EL NOMBRE DE USUARIO Y LA CONTRASEÑA
        Optional<User> optionalUser = userServices.findByUsernameAndPassword(username, password);

        // SI EL USUARIO EXISTE
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // GENERAMOS UN NUEVO TOKEN PARA EL USUARIO
            String token = getJWTToken(username);

            // ASIGNAMOS EL NUEVO TOKEN AL USUARIO
            user.setToken(token);

            // ACTUALIZAMOS EL USUARIO EN LA BASE DE DATOS CON EL NUEVO TOKEN
            userServices.save(user);

            // RETORNAMOS UNA RESPUESTA EXITOSA CON EL USUARIO Y EL NUEVO TOKEN
            return ResponseEntity.ok(user);
        } else {
            // SI EL USUARIO NO EXISTE, RETORNAMOS UNA RESPUESTA DE ERROR 401 (NO AUTORIZADO)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Acceso no autorizado, usuario no encontrado");
        }
    }

    @PostMapping("/crear")
    @Operation(
            summary = "Iniciar sesión de usuario",
            description = "Recibe en formato JSON las credenciales del usuario y devuelve un token.\n\nEjemplo:\n\n" +
                    "```json\n" +
                    "{\n" +
                    "  \"username\": \"dani\",\n" +
                    "  \"password\": \"dani\",\n" +
                    "  \"token\": \"string\"\n" +
                    "}"
    )
    public ResponseEntity<?> crearUsuario(@RequestBody User user) {
        Optional<User> user1 = userServices.existsByName(user.getUsername());
        if (user1.isPresent()) {
            return new ResponseEntity<>("Ese nombre de usuario ya existe, cámbialo y vuelve a intentarlo", HttpStatus.BAD_REQUEST);
        }
        userServices.save(user);
        return new ResponseEntity<>("Usuario creado correctamente",HttpStatus.CREATED);
    }

    @DeleteMapping("borrar/{idUsuario}")
    @Operation(
            summary = "Borrar usuario por ID"
    )
    public ResponseEntity<?> deleteUsuario(@PathVariable int idUsuario) {
        Optional<User> user = userServices.findById(idUsuario);
        if (user.isEmpty()) { // SI EXISTE EL USUARIO, LO BORRAMOS
            return new ResponseEntity<>("No existe un usuario con ese ID", HttpStatus.BAD_REQUEST);
        }
        userServices.deleteById(idUsuario);
        return new ResponseEntity<>("Usuario borrado correctamente",HttpStatus.CREATED);
    }



    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();

        return "Bearer " + token;
    }
}