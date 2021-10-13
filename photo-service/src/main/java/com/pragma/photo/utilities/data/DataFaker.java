package com.pragma.photo.utilities.data;

import com.github.javafaker.Faker;
import com.pragma.photo.entity.Photo;

public class DataFaker {

    private static final Faker faker = new Faker();

    public static String ANY_PHOTO_ID = "asdfghjkl";
    public static String ANY_PHOTO_NAME = "The Photo";
    public static Long ANY_PHOTO_NUMBER = 1L;
    public static String ANY_PHOTO = "wertyuiopasdfghjklñzxcvbnm";

    public static Photo getAnyPhoto(){
        return Photo.builder()
                .id(Long.toString(faker.number().randomNumber()))
                .photo(faker.avatar().image().getBytes())
                .photoNumber(faker.number().randomNumber())
                .build();
    }

    public static Photo getAnyPhotoPreSave(){
        return Photo.builder()
                .photo(faker.avatar().image().getBytes())
                .build();
    }

    public static Photo getPredeterminatedPhoto(){
        return Photo.builder()
                .id(ANY_PHOTO_ID)
                .photo(ANY_PHOTO.getBytes())
                .photoNumber(ANY_PHOTO_NUMBER)
                .name(ANY_PHOTO_NAME)
                .build();
    }

    public static Photo getPredeterminatedPhotoPreSave(){
        return Photo.builder()
                .photo(ANY_PHOTO.getBytes())
                .build();
    }


}
