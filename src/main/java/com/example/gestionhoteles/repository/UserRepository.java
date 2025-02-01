package com.example.gestionhoteles.repository;
import com.example.gestionhoteles.model.Habitacion;
import com.example.gestionhoteles.model.Hotel;
import com.example.gestionhoteles.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository  extends JpaRepository<User,Integer> {
    @Query("SELECT h FROM User h WHERE h.username = :username " +
            "AND h.password = :password")
    Optional<User> findUser(String username, String password);

    @Query("SELECT h FROM User h WHERE h.username = :nombre")
    Optional<User> findUsernameByName(String nombre);
}
