package com.pragma.photo.service;

import com.pragma.photo.entity.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PhotoService {

    public Photo addPhoto(MultipartFile file) throws IOException;

    public Photo addPhoto(Photo photo);

    public Photo getPhoto(String id);

    public Photo updatePhoto(String id, Photo photo);

    public void deletePhoto(Photo photo);
}
