package com.pragma.client.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pragma.client.model.Photo;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El nombre del cliente no puede estar vacío")
    @Column(nullable=false)
    private String name;

    @NotNull(message = "El apellido del cliente no puede estar vacío")
    @Column(name = "last_name", nullable=false)
    private String lastName;

    @NotNull(message = "El tipo de documento no puede estar vacío")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="type_identification_card_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @Column(nullable=false)
    private TypeIdentification typeIdentification;

    @NotNull(message = "El número de documento no puede estar vacío")
    @Size( min = 5 , max = 15, message = "El tamaño del número de documento debe ser entre 8 y 15 caracteres")
    @Column(name = "number_identification_card", unique = true, nullable = false)
    private String numberIdentification;

    @Min(value = 1)
    @Max(value = 150)
    private Integer age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="city_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private City city;

    //@NotNull(message = "La foto no puede estar vacía")
    @Column(name = "photo_id")
    private Long photoId;

    @Column(name = "created_at")
    @Temporal(TemporalType.DATE)
    private Date createAt;

    private String state;

    @Transient
    private Photo photo;

    @PrePersist
    public void prePersist() {
        this.createAt = new Date();
    }
}
