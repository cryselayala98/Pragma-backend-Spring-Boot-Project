package com.pragma.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
@AllArgsConstructor
public class Photo {

    private String id;
    private Long photoNumber;
    private String name;
    private byte[] photo;
}
