package com.pragma.client.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pragma.client.model.Photo;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.Date;

@Data
@Entity
@Builder
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

    @Column(name = "photo_id")
    private String photoId;

    private String state;

    @Column(name = "created_at", updatable=false)
    @CreationTimestamp
    private Timestamp createAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Timestamp updatedAt;

    @Transient
    private Photo photo;
}
