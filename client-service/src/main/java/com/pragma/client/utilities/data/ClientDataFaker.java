package com.pragma.client.utilities.data;

import com.github.javafaker.Faker;
import com.pragma.client.entity.City;
import com.pragma.client.entity.Client;
import com.pragma.client.entity.TypeIdentification;
import com.pragma.client.model.Photo;

public class ClientDataFaker {

    private static final Faker faker = new Faker();

    public static Long ANY_CLIENT_ID = 123L;
    public static String ANY_CLIENT_NAME = "Pepito";
    public static String ANY_CLIENT_LAST_NAME = "Perez";
    public static TypeIdentification ANY_CLIENT_TYPE_IDENTIFICATION = new TypeIdentification(1L, "Cédula de ciudadanía", "CC");
    public static String ANY_CLIENT_NUMBER_IDENTIFICATION = "123456789";
    public static Integer ANY_CLIENT_AGE = 28;
    public static City ANY_CLIENT_CITY = new City(1L, "Cúcuta");
    public static String ANY_CLIENT_PHOTO_ID = "asdfghjkl";
    public static String ANY_CLIENT_STATE_CREATED = "CREATED";
    public static String ANY_CLIENT_STATE_DELETED = "DELETED";
    public static String ANY_STRING_PHOTO = faker.avatar().image();
    public static Photo ANY_CLIENT_PHOTO = new Photo("asdfghjkl", 1L,"The Photo",  ANY_STRING_PHOTO.getBytes());
    public static Photo ANY_CLIENT_PHOTO_PRE_SAVE = Photo.builder().photo(ANY_STRING_PHOTO.getBytes()).build();
    public static City ANY_CITY_SAVE_CLIENT = City.builder().id(1L).build();
    public static TypeIdentification ANY_TYPE_IDENTIFICATION_SAVE_CLIENT = TypeIdentification.builder().abbreviation("CC").build();
    public static String ANY_TYPE_IDENTIFICATION_ABBREVIATION = "CC";

    public static Client getAnyClient(){

        return Client.builder()
                .id(faker.number().randomNumber())
                .name(faker.name().firstName())
                .lastName(faker.name().lastName())
                .typeIdentification(ANY_CLIENT_TYPE_IDENTIFICATION)
                .numberIdentification(Integer.toString(faker.number().numberBetween(1, 999999999)))
                .age(faker.number().numberBetween(1, 100))
                .city(ANY_CLIENT_CITY)
                .photoId(ANY_CLIENT_PHOTO_ID)
                .state(ANY_CLIENT_STATE_CREATED)
                .build();
    }

    public static Client getPredeterminatedClient(){
        return Client.builder()
                .id(ANY_CLIENT_ID)
                .name(ANY_CLIENT_NAME)
                .lastName(ANY_CLIENT_LAST_NAME)
                .typeIdentification(ANY_CLIENT_TYPE_IDENTIFICATION)
                .numberIdentification(ANY_CLIENT_NUMBER_IDENTIFICATION)
                .age(ANY_CLIENT_AGE)
                .city(ANY_CLIENT_CITY)
                .photoId(ANY_CLIENT_PHOTO_ID)
                .state(ANY_CLIENT_STATE_CREATED)
                .build();
    }

    public static Client getPredeterminatedClientDeleted(){
        return Client.builder()
                .id(ANY_CLIENT_ID)
                .name(ANY_CLIENT_NAME)
                .lastName(ANY_CLIENT_LAST_NAME)
                .typeIdentification(ANY_CLIENT_TYPE_IDENTIFICATION)
                .numberIdentification(ANY_CLIENT_NUMBER_IDENTIFICATION)
                .age(ANY_CLIENT_AGE)
                .city(ANY_CLIENT_CITY)
                .photoId(ANY_CLIENT_PHOTO_ID)
                .state(ANY_CLIENT_STATE_DELETED)
                .build();
    }

    public static Client getPredeterminatedClientPreSave(){
        return Client.builder()
                .name(ANY_CLIENT_NAME)
                .lastName(ANY_CLIENT_LAST_NAME)
                .typeIdentification(ANY_TYPE_IDENTIFICATION_SAVE_CLIENT)
                .numberIdentification(ANY_CLIENT_NUMBER_IDENTIFICATION)
                .age(ANY_CLIENT_AGE)
                .city(ANY_CITY_SAVE_CLIENT)
                .photoId(ANY_CLIENT_PHOTO_ID)
                .state(ANY_CLIENT_STATE_DELETED)
                .photo(ANY_CLIENT_PHOTO_PRE_SAVE)
                .build();
    }


}
