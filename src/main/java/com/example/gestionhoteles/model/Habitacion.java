package com.example.gestionhoteles.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="Habitacion")
public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="idHabitacion")
    private int idHabitacion;

    @Column(name="tamano")
    private int tamano;

    @Column(name="precioNoche")
    private double precioNoche;

    @Column(name="desayuno")
    private boolean desayuno;

    @Column(name="ocupada")
    private boolean ocupada;

    @ManyToOne
    @JoinColumn (name="idHotel",referencedColumnName = "idHotel")
    @JsonBackReference // Para que no sea un bucle infinito
    private Hotel hotel;
}