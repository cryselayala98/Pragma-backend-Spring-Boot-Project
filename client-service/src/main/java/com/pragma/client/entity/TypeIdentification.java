package com.pragma.client.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "type_identification_card")
public class TypeIdentification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_identificacion_name", nullable = false)
    private String name;

}
