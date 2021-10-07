package com.pragma.client.client;

import com.pragma.client.entity.Client;
import com.pragma.client.model.Photo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class PhotoHystrixFallbackFactory implements PhotoClient{
    @Override
    public ResponseEntity<Photo> getPhoto(Long id) {
        Photo photo =photo.builder()
                .name("none")
                .lastName("none")
                .city(null)
                .typeIdentification(null)
                .numberIdentification("")
                .age(null)
                .build();
        return ResponseEntity.ok(client);
    }
}
