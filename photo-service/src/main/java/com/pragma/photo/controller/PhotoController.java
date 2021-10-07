package com.pragma.photo.controller;

import com.pragma.photo.entity.Photo;
import com.pragma.photo.service.PhotoService;
import com.pragma.photo.utilities.ErrorUtils.ErrorMessages;
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
        Photo photo = photoService.getPhoto(id);

        if(photo == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(photo);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Photo> addPhoto(@RequestBody MultipartFile photo) throws IOException {
        Photo photoDB = photoService.addPhoto(photo);
        return ResponseEntity.status(HttpStatus.CREATED).body(photoDB);
    }

    @PostMapping
    public ResponseEntity<Photo> addPhoto(@RequestBody Photo photo) throws IOException {
        Photo photoDB = photoService.addPhoto(photo);
        return ResponseEntity.status(HttpStatus.CREATED).body(photoDB);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Photo> updatePhoto(@PathVariable("id") String id, @RequestBody(required = true) Photo photo) {
        photo.setId(id);
        Photo photoUpdated = photoService.updatePhoto(id, photo);

        if(photoUpdated ==null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(photoUpdated);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Photo> deletePhoto(@PathVariable("id") String id) {
        log.info("Fetching & Deleting photo with id {}", id);

        Photo photoQuery = photoService.getPhoto(id);
        if(photoQuery == null){
            log.error("Unable to delete. Photo with id {} not found.", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Photo.builder()
                    .id(ErrorMessages.photoDoesNotExist(id))
                    .build());
        }
        photoService.deletePhoto(photoQuery);
        return ResponseEntity.ok(photoQuery);
    }
}
