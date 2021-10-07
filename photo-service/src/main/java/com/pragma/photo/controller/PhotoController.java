package com.pragma.photo.controller;

import com.pragma.photo.entity.Photo;
import com.pragma.photo.service.PhotoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping(value = "/photos")
public class PhotoController {

    @Autowired
    PhotoService photoService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Photo> getPhoto(@PathVariable("id") String id) {
        return null;
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Photo> addPhoto(@RequestParam("image") Photo image) throws IOException {
        Photo photoDB = photoService.addPhoto(image);
        return ResponseEntity.status(HttpStatus.CREATED).body(photoDB);
    }

    @PostMapping
    public ResponseEntity<Photo> addPhoto(@RequestParam("image") MultipartFile image) throws IOException {
        Photo photoDB = photoService.addPhoto(image);

        return ResponseEntity.status(HttpStatus.CREATED).body(photoDB);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Photo> updatePhoto(@PathVariable("id") String id, @RequestBody(required = true) Photo photo) {
        return null;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Photo> deletePhoto(@PathVariable("id") String id) {
        return null;
    }
}
