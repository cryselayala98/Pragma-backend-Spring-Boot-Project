package com.pragma.client.client;

import com.pragma.client.model.Photo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@FeignClient(name="photo-service", fallback = PhotoHystrixFallbackFactory.class)
public interface PhotoClient {

    @GetMapping(value = "/photos/{id}")
    public ResponseEntity<Photo> getPhoto(@PathVariable("id") String id);

    @PostMapping(value = "/photos/add")
    public ResponseEntity<Photo> addPhoto(@RequestBody MultipartFile photo) throws IOException;

    @PostMapping(value= "/photos")
    public ResponseEntity<Photo> addPhoto(@RequestBody Photo photo) throws IOException;

    @PutMapping(value = "/photos/{id}")
    public ResponseEntity<Photo> updatePhoto(@PathVariable("id") String id, @RequestBody(required = true) Photo photo);

    @DeleteMapping(value = "/photos/{id}")
    public ResponseEntity<Photo> deletePhoto(@PathVariable("id") String id);
}
