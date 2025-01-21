package com.example.gestionhoteles.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="Habitacion")
public class Habitacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_habitacion")
    private int idHabitacion;

    @Column(name="tamano")
    private int tamano;

    @Column(name="precio_noche")
    private double precioNoche;

    @Column(name="desayuno")
    private boolean desayuno;

    @Column(name="ocupada")
    private boolean ocupada;

    @ManyToOne
    @JoinColumn (name="id_hotel",referencedColumnName = "id_hotel")
    @JsonBackReference // Para que no sea un bucle infinito
    private Hotel hotel;
}