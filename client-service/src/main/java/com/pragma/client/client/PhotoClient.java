package com.pragma.client.client;

import com.pragma.client.model.Photo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoClient {

    public ResponseEntity<Photo> getPhoto(@PathVariable("id") String id);

    public ResponseEntity<Photo> addPhoto(@RequestBody MultipartFile photo) throws IOException;

    public ResponseEntity<Photo> addPhoto(@RequestBody Photo photo) throws IOException;

    public ResponseEntity<Photo> updatePhoto(@PathVariable("id") String id, @RequestBody(required = true) Photo photo);

    public ResponseEntity<Photo> deletePhoto(@PathVariable("id") String id);
}
