package com.pragma.photo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "photo")
public class Photo {


    @Id
    private String id;
    private Long photoNumber; //dato identificador de facil memorizacion
    private String name;
    private byte[] photo;

}