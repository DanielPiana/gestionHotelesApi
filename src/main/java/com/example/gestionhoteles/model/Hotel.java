package com.example.gestionhoteles.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name="hotel")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id_hotel")
    private int idHotel;

    @Column(name="nombre")
    private String nombre;

    @Column(name="descripcion")
    private String descripcion;

    @Column(name="categoria")
    private String categoria;

    @Column(name="piscina")
    private boolean piscina;

    @Column(name="localidad")
    private String localidad;
    //mappedBy se referencia al nombre del atributo objeto Hotel de la relacion ManyToOne de Habitacion
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Habitacion> habitaciones;
}