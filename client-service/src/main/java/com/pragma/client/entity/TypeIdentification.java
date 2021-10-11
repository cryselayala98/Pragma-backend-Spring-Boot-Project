package com.pragma.client.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "type_identification_card")
public class TypeIdentification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_identificacion_name", unique=true, nullable = false)
    private String name;

    @Column(unique=true)
    private String abbreviation;

}
