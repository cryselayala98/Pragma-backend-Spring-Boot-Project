package com.pragma.client.client;

import com.pragma.client.entity.Client;
import com.pragma.client.model.Photo;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class PhotoHystrixFallbackFactory implements PhotoClient{

    @Override
    public ResponseEntity<Photo> getPhoto(String id) {
        Photo photo = Photo.builder()
         .photo(null).build();
         return ResponseEntity.ok(photo);
    }

    @Override
    public ResponseEntity<Photo> addPhoto(MultipartFile photo) throws IOException {
        Photo photoFallback = Photo.builder()
                .photo(null).build();
        return ResponseEntity.ok(photoFallback);
    }

    @Override
    public ResponseEntity<Photo> addPhoto(Photo photo) throws IOException {
        Photo photoFallback = Photo.builder()
                .photo(null).build();
        return ResponseEntity.ok(photoFallback);
    }

    @Override
    public ResponseEntity<Photo> updatePhoto(String id, Photo photo) {
        Photo photoFallback = Photo.builder()
                .photo(null).build();
        return ResponseEntity.ok(photoFallback);
    }

    @Override
    public ResponseEntity<Photo> deletePhoto(String id) {
        Photo photoFallback = Photo.builder()
                .photo(null).build();
        return ResponseEntity.ok(photoFallback);
    }
}
