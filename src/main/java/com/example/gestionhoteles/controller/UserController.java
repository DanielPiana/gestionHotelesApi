package com.example.gestionhoteles.controller;


import com.example.gestionhoteles.model.User;
import com.example.gestionhoteles.service.UserServices;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class UserController {

    private final UserServices userServices;

    public UserController(UserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping("/loginUser") // localhost:9999/api/loginUser
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
                    .body("Acceso no autorizado. Por favor, inicie sesión para continuar.");
        }
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