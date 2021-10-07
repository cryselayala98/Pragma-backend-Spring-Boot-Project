package com.pragma.client.client;

import com.pragma.client.model.Photo;
import org.springframework.http.ResponseEntity;

public interface PhotoClient {

    public ResponseEntity<Photo> getPhoto(Long id);
}
